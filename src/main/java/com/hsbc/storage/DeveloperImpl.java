package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.exceptions.ProjectNotFoundException;
import com.hsbc.exceptions.UserIsNotADeveloper;
import com.hsbc.helpers.MySQLHelper;
import com.hsbc.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeveloperImpl implements DeveloperDAL {
    private ResourceBundle resourceBundle;
    int projectId; //as developer only works on one project

    public DeveloperImpl() {
        resourceBundle = ResourceBundle.getBundle("db");
    }

    //this is to get bugs based on the project ids this can be used to display
    @Override
    public List<Bug> getAllBugs(User user) {
        String query = resourceBundle.getString("getAllBugsByProject"); // Getting query from properties file
        List<Bug> bugs = new ArrayList<>();
        if(user.getRole() == Role.DEVELOPER){
            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserId());

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    // Mapping result set to Bug object
                    Bug bug = new Bug();
                    bug.setBugId(resultSet.getInt("bugId"));
                    bug.setBugMessage(resultSet.getString("bugMessage"));
                    bug.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
                    bug.setUpdatedAt(resultSet.getTimestamp("updatedAt").toLocalDateTime());
                    bug.setBugStatus(BugStatus.valueOf(resultSet.getString("bugStatus")));
                    bug.setBugSeverity(BugSeverity.valueOf(resultSet.getString("bugSeverity")));
                    bug.setProjectId(resultSet.getInt("projectId"));

                    // Adding Bug to the list
                    bugs.add(bug);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException("Error fetching bugs for project ID: " + projectId, e);
            }
            //ask if this needs to be an array or not
            if(bugs.size() == 0)
                System.out.println("No bugs found for project ID: " + projectId);

            //returns bugs array which gives bugs related to the given project id
            return bugs;
        }else{
            //thorwing an error if the user is not a developer
            throw new UserIsNotADeveloper("The given User is not a developer");
        }


    }



    //this is update bugs status depending on the  what is happening
    @Override
    public void resolveBug(User user, int bugId, BugStatus newStatus) {
        if(user.getRole() == Role.DEVELOPER){
            String query = resourceBundle.getString("updateBugStatus"); // Getting query from properties file
            int rows = 0;  // To check if the bug status was successfully updated
            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection()) {
                // Adding values to prepared statement to update the bug's status
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newStatus.name());
                preparedStatement.setInt(2, bugId);

                // Execute update query
                rows = preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }

            if (rows > 0) {
                System.out.println("Bug status updated successfully.");
            } else {
                throw new BugNotFoundException("Bug with ID " + bugId + " not found.");
            }
        }else{
            //thorwing an error if the user is not a developer
            throw new UserIsNotADeveloper("The given User is not a developer");
        }

    }




    //not chaging anything
    @Override
    public void createReport(int projectId) throws ProjectNotFoundException {
//        // Ensure the project exists
//        Project project = projects.stream()
//                .filter(p -> p.getProjectId() == projectId)
//                .findFirst()
//                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));
//
//        // Generate a report of all bugs related to this project
//        System.out.println("Bug Report for Project: " + project.getProjectName());
//        boolean bugsFound = false;
//        for (Bug bug : bugList) {
//            if (bug.getProjectId() == projectId) {
//                bugsFound = true;
//                System.out.println("Bug ID: " + bug.getBugId());
//                System.out.println("Bug Message: " + bug.getBugMessage());
//                System.out.println("Bug Status: " + bug.getBugStatus());
//                System.out.println("Created At: " + bug.getCreatedAt());
//                System.out.println("Updated At: " + bug.getUpdatedAt());
//                System.out.println("Severity: " + bug.getBugSeverity());
//                System.out.println("------------------------------------");
//            }
//        }
//
//        if (!bugsFound) {
//            System.out.println("No bugs found for the project with ID: " + projectId);
//        }
    }
}
