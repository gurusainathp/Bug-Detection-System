package com.hsbc.storage;

import com.hsbc.models.Bug;
import com.hsbc.models.Project;
import com.hsbc.models.User;

import java.util.List;

public interface UserDAL {
    // User operations
    void addUser(User user);
    User getUser(int userId);

    // Project operations
    Project getProject(int projectId);
    List<Project> getAllProjects();
    List<Project> getProjectsByUser(int userId);

    // Bug operations
    Bug getBug(int bugId);
    List<Bug> getBugsByProject(int projectId);
    void markBugAsResolved(int bugId);
}
