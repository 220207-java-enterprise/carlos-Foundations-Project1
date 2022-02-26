package com.revature.erm;

import com.revature.erm.models.AppUser;
import com.revature.erm.util.ConnectionFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class ErmDriver {
    public static void main(String[] args) throws SQLException {

        String welcomeMenu = "Expense Reimbursement System\n" +
                            "Continue as:\n" +
                            "1) Employee\n" +
                            "2) Finance Manager\n" +
                            "3) Admin\n" +
                            "q) Exit application\n" +
                            "> ";

        System.out.print(welcomeMenu);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//will take all inputs after first space

        try {
            String userSelection = consoleReader.readLine();
            System.out.println(userSelection);//TODO: don't need this, could delete later to have console clean

            switch (userSelection) {
                case "1":
                    System.out.println("You have entered as an Employee");
                    //UI Logic
                    System.out.println("Please provide credentials to login:");

                    System.out.println("Username: ");
                    String username = consoleReader.readLine();

                    System.out.println("Password: ");
                    String password = consoleReader.readLine();

                    //Business/Validation logic

                    //Persistence logic

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
                    //if(valid userName and password found in database with Admin id) then show Admin screen
                    //TODO: ADD new user
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

                    AppUser newUser = new AppUser(firstName, lastName, email, username, password);
                    System.out.printf("Registration info provided: %s\n", newUser);
                    //TODO: Validate that the provided username and email are not already taken
                    if (!isUserValid(newUser)) {
                        throw new RuntimeException("Bad registration details given");//this will halt the app for now
                    }





                    //TODO: Add user to SQL database(persistence logic)
                    newUser.setId(UUID.randomUUID().toString());//creates random UUID converted to String
                    String fileString = newUser.toFileString() + "\n";
                    System.out.println(fileString);

                    File userDataFile = new File("data/users.txt");
                    FileWriter dataWriter = new FileWriter(userDataFile, true);//'true' appends data(instead of overriding file each time)
                    dataWriter.write(fileString);
                    dataWriter.close();

                    //TODO: UPDATE target user
                    System.out.println("[option] Update target user\n");

                    System.out.println("Please provide an existing user's credentials to update\n");
                    System.out.print("Username: ");
                    System.out.println("Password: ");
                    //if (valid userName and password found in database with Employee id OR Finance manager id)
                    //then


                    break;

                case "q":
                    System.out.println("You have quit the application.");
                    return;

                default:
                    System.out.println("You have made an incorrect selection! >:(");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Validation for user. Includes first name, last name, email, username, and password
    private static boolean isUserValid(AppUser appUser) {
        //First and last names can not have empty strings
        if (appUser.getFirstName().trim().equals("") || appUser.getLastName().trim().equals("")) {
            System.out.println("Bad first or last name. No empty values allowed.");
            return false;
        }
        //Usernames must be 3-8 characters in length and only contain alphanumeric values
        if (!appUser.getUsername().matches("^[a-zA-Z0-9]{3,8}")) {
            System.out.println("Username must be 3-8 characters.");
            return false;
        }
        //Password must have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        if (!appUser.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println("Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
            return false;
        }
        //Simple email validation
        if (!appUser.getEmail().matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$")) {
            System.out.println("Invalid email input.");
            return false;
        }
        return true;
    }
}
