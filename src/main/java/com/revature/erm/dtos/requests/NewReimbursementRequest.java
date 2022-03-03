package com.revature.erm.dtos.requests;

import com.revature.erm.daos.UsersDAO;
import com.revature.erm.util.Bytea;
import com.revature.erm.models.*;//want to implement all models

import java.sql.Timestamp;
import java.util.UUID;


public class NewReimbursementRequest {

    //TODO: what variables to user here?
    private double amount;
    private String description;
    private Bytea receipt;
    private String author_id;
    private String type;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(double amount, String description, Bytea receipt, String author_id, String type) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bytea getReceipt() {
        return receipt;
    }

    public void setReceipt(Bytea receipt) {
        this.receipt = receipt;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reimbursements extractReimbursement() {

    }
}
