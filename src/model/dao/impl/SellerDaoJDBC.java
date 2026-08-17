package model.dao.impl;

import db.Excepcions.DbException;
import model.dao.SellerDao;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteByld(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet set = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, "
                            + "department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");
            st.setInt(1, id);
            if (set.next()){
                
            }

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
