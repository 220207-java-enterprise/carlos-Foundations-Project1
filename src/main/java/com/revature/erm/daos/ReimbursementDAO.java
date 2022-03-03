package com.revature.erm.daos;

import com.revature.erm.models.*;
import com.revature.erm.util.ConnectionFactory;
import com.revature.erm.util.exceptions.DataSourceException;
import com.revature.erm.util.exceptions.ResourcePersistenceException;
import com.revature.erm.util.Bytea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursements>{

    private final String rootSelect = "SELECT " +
            "er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.receipt, er.payment_id, er.author_id, er.resolver_id, er.status_id, er.type_id, ert.status, ers.type " +
            "FROM ers_reimbursements er " +
            "JOIN ers_reimbursements_types ert " +
            "ON er.status_id = ers.status_id " +
            "JOIN ers_reimbursements_statuses ers " + //only joined ers_reimbursements with ers_reimbursements_types thus far
            "ON er.type_id = ert.type_id ";//TODO: do we have to join both ers_reimbursements_types AND ers_reimbursements_status???

    //-------------------------------save new reimbursement request---------------------------------//
    @Override
    public void save(Reimbursements newReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, NULL, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newReimbursement.getReimb_id());
            pstmt.setDouble(2, newReimbursement.getAmount());
            pstmt.setTimestamp(3, newReimbursement.getSubmitted());
            pstmt.setTimestamp(4, newReimbursement.getResolved());
            pstmt.setString(5, newReimbursement.getDescription());
            pstmt.setString(6, newReimbursement.getPayment_id());
            pstmt.setString(7, newReimbursement.getAuthor_id());
            pstmt.setString(8, newReimbursement.getResolver_id());
            pstmt.setString(9, newReimbursement.getStatus().getStatus_id());
            pstmt.setString(10, newReimbursement.getType().getType_id());

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

    //---------------------------view reimbursement by reimb_id----------------------------//
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
                reimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));//TODO: make sure this is correct syntax(should be?)
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor_id(rs.getString("author_id"));
                reimbursement.setResolver_id(rs.getString("resolver_id"));
                reimbursement.setStatus(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursement.setType(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));

            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursement;

    }

    //---------------------------view all reimbursement requests(FINANCE MANAGER ONLY)----------------------------//

    @Override
    public List<Reimbursements> getAll() {

        List<Reimbursements> reimbursements = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            if (rs.next()) {//TODO: perhaps the if loop should be a 'while'
                Reimbursements reimbursement = new Reimbursements();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));//TODO: make sure this is correct syntax(should be?)
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor_id(rs.getString("author_id"));
                reimbursement.setResolver_id(rs.getString("resolver_id"));
                reimbursement.setStatus(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursement.setType(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));
                reimbursements.add(reimbursement);
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursements;
    }

    //----------------------------update reimbursement request(FINANCE MANAGER ONLY)---------------------//
    @Override
    public void update(Reimbursements updatedReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
                    "SET amount = ?, " +
                    "resolved = ?, " +
                    "description = ?, " +
                    "receipt = ?, " +
                    "payment_id = ? " +
                    "status_id = ? " +
                    "WHERE reimb_id = ?");
            pstmt.setDouble(1, updatedReimbursement.getAmount());
            pstmt.setTimestamp(2, updatedReimbursement.getResolved());
            pstmt.setString(3, updatedReimbursement.getDescription());
            pstmt.setBinaryStream(4, updatedReimbursement.getReceipt().getBinaryStream());
            pstmt.setString(5, updatedReimbursement.getPayment_id());
            pstmt.setString(6, updatedReimbursement.getStatus().getStatus_id());
            pstmt.setString(7, updatedReimbursement.getType().getType_id());

            // TODO allow role to be updated as well

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

    }

    //-----------------------------delete reimbursement status and type by respective id--------------------//
    //-----------------------------delete reimbursement status and type by respective id--------------------//
    @Override
    public void deleteById(Reimbursements deleteReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmtStatus = conn.prepareStatement("DELETE FROM ers_reimbursement_statuses WHERE status_id = ?");
            pstmtStatus.setString(1, deleteReimbursement.getStatus().getStatus_id());

            int rowsInsertedStatus = pstmtStatus.executeUpdate();
            if (rowsInsertedStatus != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

            conn.setAutoCommit(false);
            PreparedStatement pstmtType = conn.prepareStatement("DELETE FROM ers_reimbursement_types WHERE type_id = ?");
            pstmtType.setString(1, deleteReimbursement.getType().getType_id());

            int rowsInsertedType = pstmtType.executeUpdate();
            if (rowsInsertedType != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

}
