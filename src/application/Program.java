package application;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {

        Department dp = new Department(1,"Books");

        Seller seller = new Seller(21,"Feliepete","felipete@gmail.com", LocalDate.parse("1999-09-12"),1500.00,dp);
        System.out.println(dp);
        System.out.println(seller);
    }
}
