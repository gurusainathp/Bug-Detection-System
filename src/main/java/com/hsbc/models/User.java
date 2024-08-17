package com.hsbc.models;

import java.util.List;

public class User {
    private int userId;
    private String userName;
    private String email;
    private Role role;
    private List<Project> projects;

    public User() {
    }

    public User(int userId, String userName, String email, Role role, List<Project> projects) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.projects = projects;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", projects=" + projects +
                '}';
    }
}
