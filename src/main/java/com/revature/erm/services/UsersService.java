package com.revature.erm.services;

import com.revature.erm.dtos.requests.LoginRequest;
import com.revature.erm.dtos.requests.NewUserRequest;
import com.revature.erm.dtos.responses.AppUserResponse;
import com.revature.erm.models.Users;
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.models.UserRoles;
import com.revature.erm.util.exceptions.AuthenticationException;
import com.revature.erm.util.exceptions.InvalidRequestException;
import com.revature.erm.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsersService {

    private UsersDAO userDAO; // a dependency of UserService

    // Constructor injection
    public UsersService(UsersDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<AppUserResponse> getAllUsers() {

        // Pre-Java 8 mapping logic (without Streams)
//        List<AppUser> users = userDAO.getAll();
//        List<AppUserResponse> userResponses = new ArrayList<>();
//        for (AppUser user : users) {
//            userResponses.add(new AppUserResponse(user));
//        }
//        return userResponses;

        // Java 8+ mapping logic (with Streams)
        return userDAO.getAll()
                .stream()
                .map(AppUserResponse::new) // intermediate operation
                .collect(Collectors.toList()); // terminal operation
    }

    public Users register(NewUserRequest newUserRequest) throws IOException {

        Users newUser = newUserRequest.extractUser();

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Bad registration details given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "username ";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }

        // TODO encrypt provided password before storing in the database

        newUser.setUser_id(UUID.randomUUID().toString());
        newUser.setRole_id(new UserRoles("7c3521f5-ff75-4e8a-9913-01d15ee4dc99", "EMPLOYEE")); // All newly registered users start as BASIC_USER
        userDAO.save(newUser);

        return newUser;
    }

    public Users login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!isUsernameValid(username) || !isPasswordValid(password)) {
            throw new InvalidRequestException("Invalid credentials provided!");
        }

        // TODO encrypt provided password (assumes password encryption is in place) to see if it matches what is in the DB

        Users authUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUser == null) {
            throw new AuthenticationException();
        }

        return authUser;

    }

    private boolean isUserValid(Users user) {

        // First name and last name are not just empty strings or filled with whitespace
        if (user.getGiven_name().trim().equals("") || user.getSurname().trim().equals("")) {
            return false;
        }

        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(user.getUsername())) {
            return false;
        }

        // Passwords require a minimum eight characters, at least one uppercase letter, one lowercase
        // letter, one number and one special character
        if (!isPasswordValid(user.getPassword())) {
            return false;
        }

        // Basic email validation
        return isEmailValid(user.getEmail());

    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean isUsernameAvailable(String username) {
        return userDAO.findUserByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userDAO.findUserByEmail(email) == null;
    }

}

