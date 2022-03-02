package com.revature.erm.daos;

import com.revature.erm.models.Reimbursements;
import com.revature.erm.models.Users;
import com.revature.erm.util.ConnectionFactory;
import com.revature.erm.util.exceptions.DataSourceException;
import com.revature.erm.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursements>{

    //save
        //create new reimbursement request
    @Override
    public void save(Reimbursements newReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newReimbursement.getReimb_id());
            pstmt.setInt(2, newReimbursement.getAmount());
            pstmt.setInt(3, newReimbursement.getSubmitted());
            pstmt.setInt(4, newReimbursement.getResolved());
            pstmt.setString(5, newReimbursement.getDescription());
            pstmt.setByte(6, newReimbursement.getReceipt());
            pstmt.setString(7, newReimbursement.getPayment_id());
            pstmt.setString(8, newReimbursement.getAuthor_id());
            pstmt.setString(9, newReimbursement.getResolver_id());
            pstmt.setString(10, newReimbursement.getStatus_id());
            pstmt.setString(11, newReimbursement.getType_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Reimbursements getById(String id) {
        return null;
    }

    @Override
    public List<Reimbursements> getAll() {
        return null;
    }

    @Override
    public void update(Reimbursements updatedObj) {

    }

    @Override
    public void deleteById(String id) {

    }
    //getById
        //view my reimbursements
            //view reimbursement details
    //update
        //update reimbursement

    //financeManager

}
