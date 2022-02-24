package com.revature.erm;

import com.revature.erm.util.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ErmDriver {
    public static void main(String[] args) throws SQLException {

        String welcomeMenu = "Expense Reimbursement System\n" +
                            "Please make a selection below:\n" +
                            "1) Employee\n" +
                            "2) Finance Manager\n" +
                            "3) Admin\n" +
                            "> ";

        System.out.print(welcomeMenu);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//will take all inputs after first space

        try {
            String userSelection = consoleReader.readLine();
            System.out.println(userSelection);//TODO: don't need this, could delete later to have console clean

            switch (userSelection) {
                case "1":
                    System.out.println("You have entered as an Employee");
                    System.out.println("Login: ");

                    break;

                case "2":
                    System.out.println("You have entered as a Finance Manager");
                    System.out.println("Login: ");
                    break;

                case "3":
                    System.out.println("You have entered as an Admin");
                    System.out.print("Username: ");
                    String userName = consoleReader.readLine();
                    System.out.print("Password: ");
                    String passWord = consoleReader.readLine();
                    //TODO: Once entered successfully as admin, allow to ADD new user, UPDATE target user?, DELETE target user

                    //TODO: REGISTER new user
                    System.out.print("[option] Add new user\n");

                    System.out.print("First name: ");
                    String firstName = consoleReader.readLine();

                    System.out.print("Last name: ");
                    String lastName = consoleReader.readLine();

                    System.out.print("Email: ");
                    String email = consoleReader.readLine();

                    System.out.print("Username: ");
                    String username = consoleReader.readLine();

                    System.out.print("Password: ");
                    String password = consoleReader.readLine();

                    System.out.printf("Registration info provided: %s %s %s %s %s", firstName, lastName, email, username, password);
                    break;

                default:
                    System.out.println("You have made an incorrect selection! >:(");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
