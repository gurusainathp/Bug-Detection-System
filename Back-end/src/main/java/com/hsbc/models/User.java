package com.hsbc.models;

import java.util.List;

public class User {
    private int userId; //user di is increment type
    private String email; //email willl be unique
    private String password; //taken by user
    private Role role; //3 roles

    public User(int userId, String email, String password, Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //custom contructor as we only need 3 values as userId is specified automatically
    public User(String email, String password, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
