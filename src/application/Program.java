package application;


import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.util.List;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(2,null));

        for (Seller sl : sellerList){
            System.out.println(sl);
        }
    }
}
