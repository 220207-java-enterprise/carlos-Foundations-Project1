package com.revature.erm.daos;

import com.revature.erm.models.Users;
import com.revature.erm.models.UserRoles;
import com.revature.erm.util.ConnectionFactory;
import com.revature.erm.util.exceptions.DataSourceException;
import com.revature.erm.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO implements CrudDAO<Users> {


    private final String rootSelect = "SELECT " +
            "eu.user_id, eu.username, eu.email, eu.password, eu.given_name, eu.surname, eu.is_active, eu.role_id, eur.role " +
            "FROM ers_users eu " +
            "JOIN ers_user_roles eur " +
            "ON eu.role_id = eur.role_id ";

    //-------------------------------------find user by username------------------------------------------//

    public Users findUserByUsername(String username) {

        Users user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ?");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new Users();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));//TODO: how to setIs_Active to a boolean value?
                user.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    //---------------------------------find user by email---------------------------------------//

    public Users findUserByEmail(String email) {

        Users user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE email = ?");
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new Users();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));
                user.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return user;

    }

    //------------------------------find user by username AND password---------------------------------------//

    public Users findUserByUsernameAndPassword(String username, String password) {

        Users authUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                authUser = new Users();
                authUser.setUser_id(rs.getString("user_id"));
                authUser.setUsername(rs.getString("username"));
                authUser.setEmail(rs.getString("email"));
                authUser.setPassword(rs.getString("password"));
                authUser.setGiven_name(rs.getString("given_name"));
                authUser.setSurname(rs.getString("surname"));
                authUser.setIs_active(rs.getBoolean("is_active"));
                authUser.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return authUser;
    }

    //----------------------------find user by role------------------------------//

    public Users findUserByRole(String role) {

        Users authUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE role = ?");
            pstmt.setString(1, role);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                authUser = new Users();
                authUser.setUser_id(rs.getString("user_id"));
                authUser.setUsername(rs.getString("username"));
                authUser.setEmail(rs.getString("email"));
                authUser.setPassword(rs.getString("password"));
                authUser.setGiven_name(rs.getString("given_name"));
                authUser.setSurname(rs.getString("surname"));
                authUser.setIs_active(rs.getBoolean("is_active"));
                authUser.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e);
        }

        return authUser;
    }

    //-------------------------------------SAVE newUser--------------------------------------------//

    @Override
    public void save(Users newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newUser.getUser_id());
            pstmt.setString(2, newUser.getUsername());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getPassword());
            pstmt.setString(5, newUser.getGiven_name());
            pstmt.setString(6, newUser.getSurname());
            pstmt.setBoolean(7, newUser.getIs_active());
            pstmt.setString(8, newUser.getRole_id().getRole_id());

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

    //-------------------------------------get user by id--------------------------------------------//

    @Override
    public Users getById(String id) {

        Users user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE user_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new Users();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));
                user.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return user;

    }

    //-------------------------------------getAll users--------------------------------------------//

    @Override
    public List<Users> getAll() {

        List<Users> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));
                user.setRole_id(new UserRoles(rs.getString("role_id"), rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return users;
    }

    //-------------------------------------update users--------------------------------------------//

    @Override
    public void update(Users updatedUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users " +
                    "SET username = ?, " +
                    "email = ?, " +
                    "password = ?, " +
                    "given_name = ?, " +
                    "surname = ? " +
                    "is_active = ? " +
                    "WHERE user_id = ?");
            pstmt.setString(1, updatedUser.getUsername());
            pstmt.setString(2, updatedUser.getEmail());
            pstmt.setString(3, updatedUser.getPassword());
            pstmt.setString(4, updatedUser.getGiven_name());
            pstmt.setString(5, updatedUser.getSurname());
            pstmt.setBoolean(6, updatedUser.getIs_active());
            pstmt.setString(7, updatedUser.getUser_id());

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

    //-------------------------------------delete user by id--------------------------------------------//

    @Override
    public void deleteById(Users idToDelete) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ers_users WHERE user_id = ?");
            pstmt.setString(1, idToDelete.getRole_id().getRole_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
}

