package model.dao.impl;

import db.Excepcions.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO"
                    + " department (Name) "
                    + "VALUES (?)"
            );
            ps.setString(1, obj.getName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Rows affected: " + affectedRows);
            } else {
                throw new DbException("Unexpect Error. No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department obj, int id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE department "
                    + "SET "
                    + "Name = ? "
                    + "WHERE Id = ?; "
            );

            ps.setString(1, obj.getName());
            ps.setInt(2, id);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Affected Rows: " + affectedRows);
            } else {
                throw new DbException("Unexpected error: " + affectedRows);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteByld(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }
}
