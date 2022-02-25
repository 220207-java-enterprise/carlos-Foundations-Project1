package com.revature.erm.models;

import java.util.Objects;

public class AppUser {

    //No Business Logic found here, only POJO. Simple encapsulation of some domain object's states

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    //Generate constructors
    public AppUser(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    //Generate getters n setters(these are what we use to access our private instance members
    //Reason we do this is to avoid giving direct access towards our String variables
    //This is normal for Java

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toFileString() {
        return new StringBuilder(id).append(":")//StringBuidler cant append to null String(id)
                .append(firstName).append(":")
                .append(lastName).append(":")
                .append(email).append(":")
                .append(username).append(":")
                .append(password).toString();
    }

    //Generate equals and hashCode method
    //This overrides the equals and hashCode methods. Why?
    //hashCode gives us unique value that represents


    @Override//overrides equals method
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(email, appUser.email) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password);
    }
    //hashCode gives us sudo-unique(not truly random) int value that represents the objects(id,firstName,...)
    @Override//overrides hashCode method; we use this to compare data quickly through hashSet
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password);
    }

    //Generate toString method
    @Override//Overrides to String method(when we print user to console it will print out like this, rather than hashValue)
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
