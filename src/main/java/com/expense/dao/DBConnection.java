package com.expense.dao;

import java.sql.Connection;
import java.sql.DriverManager;

//public class DBConnection {
//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/expense_tracker",
//                "root",
//                "root"
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}

