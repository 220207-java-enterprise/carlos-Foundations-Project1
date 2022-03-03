package com.revature.erm.daos;

import com.revature.erm.models.Reimbursements;
import com.revature.erm.models.UserRoles;
import com.revature.erm.models.Users;
import com.revature.erm.util.ConnectionFactory;
import com.revature.erm.util.exceptions.DataSourceException;
import com.revature.erm.util.exceptions.ResourcePersistenceException;
import com.revature.erm.util.Bytea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursements>{

    private final String rootSelect = "SELECT " +
            "er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.receipt, er.payment_id, er.author_id, er.resolver_id, er.status_id, er.type_id, ert.type, ers.status " +
            "FROM ers_reimbursements er " +
            "JOIN ers_reimbursements_types ert " +
            "ON er.type_id = ert.type_id " +
            "JOIN ers_reimbursements_statuses ers " + //only joined ers_reimbursements with ers_reimbursements_types thus far
            "ON er.status_id = ers.status_id ";//TODO: do we have to join both ers_reimbursements_types AND ers_reimbursements_status???

    //save
        //create new reimbursement request
    @Override
    public void save(Reimbursements newReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newReimbursement.getReimb_id());
            pstmt.setDouble(2, newReimbursement.getAmount());
            pstmt.setTimestamp(3, newReimbursement.getSubmitted());
            pstmt.setTimestamp(4, newReimbursement.getResolved());
            pstmt.setString(5, newReimbursement.getDescription());
            pstmt.setBinaryStream(6, newReimbursement.getReceipt().getBinaryStream());
            pstmt.setString(7, newReimbursement.getPayment_id());
            pstmt.setString(8, newReimbursement.getAuthor_id());
            pstmt.setString(9, newReimbursement.getResolver_id());
            pstmt.setString(10, newReimbursement.getStatus().getStatus_id());
            pstmt.setString(11, newReimbursement.getType().getType_id());

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

    //---------------------------get Reimbursement by reimb_id----------------------------//
    @Override
    public Reimbursements getById(String id) {

        Reimbursements reimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE reimb_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reimbursement = new Reimbursements();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytea("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return user;

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
