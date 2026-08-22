package model.dao.impl;

import db.DB;
import db.Excepcions.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                System.out.println("Affected Rows: " + affectedRows);
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
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Affected Rows: " + affectedRows);
            } else {
                throw new DbException("Unexpected Error. Affected Rows : " + affectedRows);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT "
                    + " * FROM department "
                    + "WHERE Id = ?; ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Department dp = this.instantiateDepartment(rs);
                return dp;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
        return null;
    }

    private Department instantiateDepartment(ResultSet set) throws SQLException {
        Department dp = new Department();
        dp.setId(set.getInt("Id"));
        dp.setName(set.getString("Name"));
        return dp;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM department; ");
            rs = ps.executeQuery();
            List<Department> departmentList = new ArrayList<>();
            while (rs.next()){
                Department dep = this.instantiateDepartment(rs);
                departmentList.add(dep);
            }
            return departmentList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}
