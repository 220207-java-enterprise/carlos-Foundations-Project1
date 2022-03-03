package com.revature.erm.dtos.requests;

import com.revature.erm.models.Reimbursements;
import com.revature.erm.models.ReimbursementTypes;

public class NewReimbursementRequest {

    //TODO: what variables to user here?
    private int amount;
    private int submitted;
    private int resolved;
    private String description;
    private byte receipt;
    private String payment_id;
    private UserRoles role_id;
}
