package application;


import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;


public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        sellerDao.insert(new Seller("Thiago Silva","thiagoSilva@gmail.com", LocalDate.parse("2005-12-20"),4000.00,new Department(1)));

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(2,null));

        for (Seller sl : sellerList){
            System.out.println(sl);
        }
        System.out.println();


        List<Seller> sellerList1 = sellerDao.findAll();

        for (Seller sl1 : sellerList1){
            System.out.println(sl1);
        }
    }
}
