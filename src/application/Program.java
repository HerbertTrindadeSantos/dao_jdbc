package application;


import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        sellerDao.deleteByld(5);

        List<Seller> sellers = sellerDao.findAll();

        for (Seller sl : sellers){
            System.out.println(sl);
        }

    }
}
