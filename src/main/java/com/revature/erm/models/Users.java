package com.revature.erm.models;

import java.util.Objects;

public class Users {
    private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private Boolean is_active;
    private UserRoles role_id;

    public Users() {
        super();
    }

    public Users(String username, String email, String password, String given_name, String surname, Boolean is_active, UserRoles role_id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public UserRoles getRole_id() {
        return role_id;
    }

    public void setRole_id(UserRoles role_id) {
        this.role_id = role_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(user_id, users.user_id)
                && Objects.equals(username, users.username)
                && Objects.equals(email, users.email)
                && Objects.equals(password, users.password)
                && Objects.equals(given_name, users.given_name)
                && Objects.equals(surname, users.surname)
                && Objects.equals(is_active, users.is_active)
                && Objects.equals(role_id, users.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, email, password, given_name, surname, is_active, role_id);
    }

    @Override
    public String toString() {
        return "users{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active=" + is_active +
                ", role_id='" + role_id + '\'' +
                '}';
    }
}
