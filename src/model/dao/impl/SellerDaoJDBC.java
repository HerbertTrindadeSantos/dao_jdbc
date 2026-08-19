package model.dao.impl;

import db.DB;
import db.Excepcions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO "
                    +"seller "
                    +"(Name,Email,BirthDate,BaseSalary,DepartmentId) "
                    +"VALUES "
                    +"(?,?,?,?,?);"
            );
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,Date.valueOf(obj.getBirthDate()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteByld(Integer id) {

    }

    private Department isntantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sl = new Seller();
        sl.setId(rs.getInt("Id"));
        sl.setName(rs.getString("Name"));
        sl.setEmail(rs.getString("Email"));
        sl.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        sl.setBaseSalary(rs.getDouble("BaseSalary"));
        sl.setDepartment(dep);
        return sl;
    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*, "
                            + "department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Department dp = this.isntantiateDepartment(rs);
                Seller sl = this.instantiateSeller(rs, dp);
                return sl;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT "
                    + "seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id;");

            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer,Department> departmentMap = new HashMap<>();

            while (rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = isntantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"),dep);
                }
                Seller sll = instantiateSeller(rs,dep);
                sellerList.add(sll);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("SELECT "
                    + "seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name;");
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            List<Seller> sellerList = new ArrayList<>();

            Map<Integer, Department> mapSeller = new HashMap<>();
            while (rs.next()) {

                Department dep = mapSeller.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = isntantiateDepartment(rs);
                    mapSeller.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sell = instantiateSeller(rs, dep);
                sellerList.add(sell);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}
