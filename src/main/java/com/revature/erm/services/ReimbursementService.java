package com.revature.erm.services;

import com.revature.erm.daos.ReimbursementDAO;
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.dtos.requests.NewReimbursementRequest;
import com.revature.erm.dtos.requests.NewUserRequest;
import com.revature.erm.models.Reimbursements;
import com.revature.erm.models.UserRoles;
import com.revature.erm.models.Users;
import com.revature.erm.util.exceptions.InvalidRequestException;
import com.revature.erm.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.UUID;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO; // a dependency of ReimbursementService

    public Reimbursements register(NewReimbursementRequest newReimbursementRequest) throws IOException {

        Reimbursements newReimbursement = newReimbursementRequest.extractReimbursement();

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

        newUser.setUser_id(UUID.randomUUID().toString());//sets random UUID to new employee..TODO do I have to specify an ID when sending a request?
        newUser.setRole_id(new UserRoles("7c3521f5-ff75-4e8a-9913-01d15ee4dc99", "EMPLOYEE")); // All newly registered users start as BASIC_USER
        newUser.setIs_active(false);
        reimbursementDAO.save(newUser);

        return newUser;
    }
}
