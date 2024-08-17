package com.hsbc.storage;

import com.hsbc.models.BugStatus;
import com.hsbc.storage.UserDAL;
import com.hsbc.models.Bug;
import com.hsbc.models.Project;
import com.hsbc.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeveloperImpl implements UserDAL {

    // Mock database storage
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Project> projects = new HashMap<>();
    private Map<Integer, Bug> bugs = new HashMap<>();

    @Override
    public void addUser(User user) {
        // Add user to the mock database
        users.put(user.getUserId(), user);
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public Project getProject(int projectId) {
        return projects.get(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return List.of();
    }

    @Override
    public List<Project> getProjectsByUser(int userId) {
        // Retrieve projects assigned to the developer
        User user = users.get(userId);
        return user != null ? user.getProjects() : new ArrayList<>();
    }

    @Override
    public Bug getBug(int bugId) {
        return bugs.get(bugId);
    }

    @Override
    public List<Bug> getBugsByProject(int projectId) {
        // Retrieve bugs associated with a project
        List<Bug> projectBugs = new ArrayList<>();
        for (Bug bug : bugs.values()) {
            if (bug.getProject().getProjectId() == projectId) {
                projectBugs.add(bug);
            }
        }
        return projectBugs;
    }

    // Developer-specific operation
    public void markBugAsResolved(int bugId) {
        // Mark a bug as resolved in the mock database
        Bug bug = bugs.get(bugId);
        if (bug != null) {
            bug.setBugStatus(BugStatus.RESOLVED);
            bug.setUpdatedAt(LocalDateTime.now());
        }
    }
}
