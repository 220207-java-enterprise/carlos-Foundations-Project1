package com.revature.erm.dtos.requests;

import com.revature.erm.models.Reimbursements;

import java.util.Objects;

public class UpdateReimbursementRequest {

    private String reimb_id;
    private String resolver_id;
    private String status;

    public UpdateReimbursementRequest() {
        super();
    }

    public UpdateReimbursementRequest(String reimb_id, String resolver_id, String status) {
        this.reimb_id = reimb_id;
        this.resolver_id = resolver_id;
        this.status = status;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateReimbursementRequest that = (UpdateReimbursementRequest) o;
        return Objects.equals(reimb_id, that.reimb_id) && Objects.equals(resolver_id, that.resolver_id) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, resolver_id, status);
    }

    @Override
    public String toString() {
        return "UpdateReimbursementRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
