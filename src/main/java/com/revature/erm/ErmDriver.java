package com.revature.erm;

import com.revature.erm.util.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ErmDriver {
    public static void main(String[] args) throws SQLException {

        String welcomeMenu = "Expense Reimbursement System\n" +
                            "Please make a selection below:\n" +
                            "1) Login\n" +
                            "2) Register\n" +
                            "3) Exit\n" +
                            "> ";

        System.out.println(welcomeMenu);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    }
}
