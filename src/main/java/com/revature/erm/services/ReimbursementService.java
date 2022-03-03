package com.revature.erm.services;

import com.revature.erm.daos.ReimbursementDAO;
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.dtos.requests.NewReimbursementRequest;
import com.revature.erm.dtos.requests.NewUserRequest;
import com.revature.erm.models.ReimbursementStatuses;
import com.revature.erm.models.Reimbursements;
import com.revature.erm.models.UserRoles;
import com.revature.erm.models.Users;
import com.revature.erm.util.exceptions.InvalidRequestException;
import com.revature.erm.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.UUID;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO; // a dependency of ReimbursementService

    public Reimbursements saveNewReimbursement(NewReimbursementRequest newReimbursementRequest) throws IOException {

        Reimbursements newReimbursement = newReimbursementRequest.extractReimbursement();

        reimbursementDAO.save(newReimbursement);

        return newReimbursement;
    }
}
