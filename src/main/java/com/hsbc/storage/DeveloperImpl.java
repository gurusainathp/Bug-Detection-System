package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.exceptions.ProjectNotFoundException;
import com.hsbc.helpers.MySQLHelper;
import com.hsbc.models.Bug;
import com.hsbc.models.BugStatus;
import com.hsbc.models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class DeveloperImpl implements DeveloperDAL {

    private List<Project> projects; // Storage for projects
    private List<Bug> bugList; // Storage for bugs
    private ResourceBundle resourceBundle;

    public DeveloperImpl(List<Project> projects, List<Bug> bugList) {
        this.projects = projects;
        this.bugList = bugList;
        resourceBundle = ResourceBundle.getBundle("db");
    }


    private Bug findBugById(int bugId) throws BugNotFoundException {
        try (Connection connection = MySQLHelper.getConnection()) {
            String query = resourceBundle.getString("getBugs");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bugList.stream()
                .filter(bug -> bug.getBugId() == bugId)
                .findFirst()
                .orElseThrow(() -> new BugNotFoundException("Bug with ID " + bugId + " not found."));
    }

    @Override
    public void resolveBug(int bugId, int projectId) throws BugNotFoundException, ProjectNotFoundException {
        // Ensure the project exists
        Project project = projects.stream()
                .filter(p -> p.getProjectId() == projectId)
                .findFirst()
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));

        // Find the bug by ID
        Bug bug = findBugById(bugId);

        // Update the bug status to resolved
        if (bug.getBugStatus() != BugStatus.RESOLVED) {
            bug.setBugStatus(BugStatus.RESOLVED);
            bug.setUpdatedAt(LocalDateTime.now());
            System.out.println("Bug with ID: " + bugId + " has been resolved.");
        } else {
            System.out.println("Bug with ID: " + bugId + " is already resolved.");
        }
    }

    @Override
    public void createReport(int projectId) throws ProjectNotFoundException {
        // Ensure the project exists
        Project project = projects.stream()
                .filter(p -> p.getProjectId() == projectId)
                .findFirst()
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));

        // Generate a report of all bugs related to this project
        System.out.println("Bug Report for Project: " + project.getProjectName());
        boolean bugsFound = false;
        for (Bug bug : bugList) {
            if (bug.getProject().getProjectId() == projectId) {
                bugsFound = true;
                System.out.println("Bug ID: " + bug.getBugId());
                System.out.println("Bug Message: " + bug.getBugMessage());
                System.out.println("Bug Status: " + bug.getBugStatus());
                System.out.println("Created At: " + bug.getCreatedAt());
                System.out.println("Updated At: " + bug.getUpdatedAt());
                System.out.println("Severity: " + bug.getBugSeverity());
                System.out.println("------------------------------------");
            }
        }

        if (!bugsFound) {
            System.out.println("No bugs found for the project with ID: " + projectId);
        }
    }
}
