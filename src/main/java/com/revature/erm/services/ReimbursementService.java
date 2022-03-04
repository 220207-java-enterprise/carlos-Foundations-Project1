package com.revature.erm.services;

import com.revature.erm.daos.ReimbursementDAO;
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.dtos.requests.NewReimbursementRequest;
import com.revature.erm.dtos.requests.NewUserRequest;
import com.revature.erm.dtos.requests.UpdateReimbursementRequest;
import com.revature.erm.dtos.responses.ResourceCreationResponse;
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

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {

        this.reimbursementDAO = reimbursementDAO;
    }

    public ResourceCreationResponse saveNewReimbursement(NewReimbursementRequest newReimbursementRequest) throws IOException {

        Reimbursements newReimbursement = newReimbursementRequest.extractReimbursement();

        // TODO validate that the data provided in the new reimburesment request is valid

        reimbursementDAO.save(newReimbursement);

        return new ResourceCreationResponse(newReimbursement.getReimb_id());
    }

    public ResourceCreationResponse updateReimbursement(UpdateReimbursementRequest updateReimbursementRequest) throws IOException {

        Reimbursements reimbursementToBeUpdated = reimbursementDAO.getById(updateReimbursementRequest.getReimb_id());
        ReimbursementStatuses reimbursementStatus = new ReimbursementStatuses(updateReimbursementRequest.getStatus(), updateReimbursementRequest.getStatus());

        reimbursementToBeUpdated.setStatus(reimbursementStatus);//set reimbursement to new status

        reimbursementDAO.update(reimbursementToBeUpdated);

        return new ResourceCreationResponse(reimbursementToBeUpdated.getReimb_id());
    }
}
