package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.helpers.MySQLHelper;
import com.hsbc.models.Bug;
import com.hsbc.models.BugSeverity;
import com.hsbc.models.BugStatus;
import com.hsbc.models.Project;
import com.hsbc.models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TesterImpl implements TesterDAL {
    private List<Bug> bugList = new ArrayList<Bug>();
    private ResourceBundle resourceBundle;
    private PreparedStatement preparedStatement;

    public TesterImpl() {
        resourceBundle = ResourceBundle.getBundle("db");
    }


public class TesterImpl implements TesterDAL {
    private List<Bug> bugList = new ArrayList<>();


    @Override
    public void raiseBug(int bugId, String bugMessage, BugSeverity bugSeverity, Project project, User assignedDeveloper) {
        Bug bug = new Bug(bugId, bugMessage, LocalDateTime.now(), null, BugStatus.PENDING, bugSeverity, project);
        // Optionally, you can store the developer information in the Bug class, or log it as needed
        bugList.add(bug);
        System.out.println("Bug raised: " + bug);

        System.out.println("Assigned Developer: " + assignedDeveloper.getUserName() + " (Email: " + assignedDeveloper.getEmail() + ")");


        String query = resourceBundle.getString("addBug");
        try (Connection connection = MySQLHelper.getConnection()) {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bug.getBugMessage());
            preparedStatement.setString(2, LocalDateTime.now().toString());
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, BugStatus.PENDING.name());
            preparedStatement.setString(5, bug.getBugSeverity().toString());
            preparedStatement.setInt(6, bug.getProject().getProjectId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Bug findBugById(int bugId) throws BugNotFoundException {
        return bugList.stream()
                .filter(bug -> bug.getBugId() == bugId)
                .findFirst()
                .orElseThrow(() -> new BugNotFoundException("Bug with ID " + bugId + " not found."));
    }

    @Override
    public void inspectBug(int bugId) throws BugNotFoundException {
        Bug bug = findBugById(bugId);

        System.out.println("Inspecting Bug: " + bug);

        // Inspection logic
        if (bug.getBugSeverity() == BugSeverity.CRITICAL) {
            System.out.println("This is a critical bug. Immediate action is required.");
            // Possibly escalate or update the status
            bug.setBugStatus(BugStatus.IN_PROGRESS);
        } else if (bug.getBugSeverity() == BugSeverity.MODERATE) {
            System.out.println("This is a moderate bug. Schedule it for fixing soon.");
            // Update the status if needed
            bug.setBugStatus(BugStatus.IN_PROGRESS);
        } else if (bug.getBugSeverity() == BugSeverity.LIGHT) {
            System.out.println("This is a light bug. It can be fixed later.");
            // No immediate action, but update timestamp
            bug.setUpdatedAt(LocalDateTime.now());
        }

        // Update the timestamp to indicate that the bug has been inspected
        bug.setUpdatedAt(LocalDateTime.now());

        System.out.println("Bug after inspection: " + bug);

    }

    @Override
    public void createReport() {
        System.out.println("Creating bug report...");

        // Specify the file path
        String filePath = "C:\\Users\\91702\\OneDrive\\Desktop\\bug-detection-system\\File\\bug_report.csv";

        // Try-with-resources for handling file operations
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Writing CSV headers
            writer.write("Bug ID,Bug Message,Bug Severity,Bug Status,Project Name,Created At,Updated At");
            writer.newLine();

            // Writing bug details to the CSV file
            for (Bug bug : bugList) {
                writer.write(bug.getBugId() + "," +
                        bug.getBugMessage() + "," +
                        bug.getBugSeverity() + "," +
                        bug.getBugStatus() + "," +
                        bug.getProject().getProjectName() + "," +
                        bug.getCreatedAt() + "," +
                        (bug.getUpdatedAt() != null ? bug.getUpdatedAt() : "N/A"));
                writer.newLine();
            }

            System.out.println("Bug report created successfully at " + filePath);

        } catch (IOException e) {
            System.err.println("Error writing bug report: " + e.getMessage());
        }

        // Print bug details in the console as before
        for (Bug bug : bugList) {
            System.out.println(bug);
        }
    }
}
