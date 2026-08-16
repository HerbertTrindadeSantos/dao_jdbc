package application;

import db.DB;

import java.sql.Connection;

public class Program {
    public static void main(String[] args) {
        
        Connection conn = null;
        DB.getConnection();
        DB.closeConnection();
    }
}
