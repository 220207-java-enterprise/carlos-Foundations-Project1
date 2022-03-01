//package com.revature.erm;
//
//import com.revature.erm.daos.CrudDAO;
//import com.revature.erm.daos.UsersDAO;
//import com.revature.erm.models.Users;
//import com.revature.erm.util.ConnectionFactory;
//
//
//import java.io.*;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//
//public class ErmDriver {
//    public ErmDriver() throws IOException {
//    }
//
//    public static void main(String[] args) throws SQLException, IOException {
//
//        // get instance of ConnectionFactory - only 1 instance possible because of Singleton Design Pattern
//        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
//
//        Connection conn = null;
//
//        try {
//            conn = connectionFactory.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (conn == null) {
//            System.out.println("Error!");
//        } else {
//            System.out.println("Success!");
//        }
//
//        String welcomeMenu = "Expense Reimbursement System\n" +
//                "Continue as:\n" +
//                "1) Get User By Id\n" +
//                "2) Finance Manager\n" +
//                "3) Admin\n" +
//                "q) Exit application\n" +
//                "> ";
//
//        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//will take all inputs after first space
//
//        String userSelection = consoleReader.readLine();
//
//        if ("1".equals(userSelection)) {
//            System.out.println("Get user by ID test");
//
//            CrudDAO<Users> crud = new UsersDAO(conn);
//            Users user = (Users) crud.getById("1");
//            System.out.println(user);
//        }
//    }
//}
