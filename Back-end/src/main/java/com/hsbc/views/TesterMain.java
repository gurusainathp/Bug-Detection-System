package com.hsbc.views;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.models.BugSeverity;
import com.hsbc.models.Project;
import com.hsbc.models.Role;
import com.hsbc.models.User;
import com.hsbc.storage.TesterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TesterMain {
    public static void main(String[] args) {
        TesterImpl tester = new TesterImpl();
        Scanner scanner = new Scanner(System.in);
        boolean bugRaised = false;

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Raise Bug");
            System.out.println("2. Inspect Bug");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter Bug ID:");
                    int bugId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    System.out.println("Enter Bug Message:");
                    String bugMessage = scanner.nextLine();

                    System.out.println("Enter Bug Severity (CRITICAL, MODERATE, LIGHT):");
                    String severityInput = scanner.nextLine();
                    BugSeverity bugSeverity = BugSeverity.valueOf(severityInput.toUpperCase());

                    System.out.println("Enter Project ID:");
                    int projectId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    System.out.println("Enter Project Name:");
                    String projectName = scanner.nextLine();

                    System.out.println("Enter Project Description:");
                    String projectDescription = scanner.nextLine();

                    // Collect users associated with the project
                    List<User> users = new ArrayList<>();
                    System.out.println("Enter the number of users associated with the project:");
                    int numUsers = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    User assignedDeveloper = null; // Variable to store the assigned developer

                    for (int i = 0; i < numUsers; i++) {
                        System.out.println("Enter User ID for User " + (i + 1) + ":");
                        int userId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        System.out.println("Enter User Name for User " + (i + 1) + ":");
                        String userName = scanner.nextLine();

                        System.out.println("Enter User Email for User " + (i + 1) + ":");
                        String email = scanner.nextLine();

                        System.out.println("Enter User Role (e.g., DEVELOPER, TESTER, MANAGER):");
                        String roleInput = scanner.nextLine();
                        Role role = Role.valueOf(roleInput.toUpperCase());

                        // Assuming each user can have their own associated projects, collect these projects
                        List<Project> userProjects = new ArrayList<>();
                        System.out.println("Enter the number of projects for User " + (i + 1) + ":");
                        int numUserProjects = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        for (int j = 0; j < numUserProjects; j++) {
                            System.out.println("Enter Project ID for Project " + (j + 1) + " associated with this user:");
                            int userProjectId = scanner.nextInt();
                            scanner.nextLine();  // Consume newline

                            System.out.println("Enter Project Name for Project " + (j + 1) + ":");
                            String userProjectName = scanner.nextLine();

                            System.out.println("Enter Project Description for Project " + (j + 1) + ":");
                            String userProjectDescription = scanner.nextLine();

                            userProjects.add(new Project(userProjectId, userProjectName, userProjectDescription, null));  // Assuming null for now, as we're focusing on User construction
                        }

                        // Create the User object with the new constructor
                        User user = new User(userId, userName, email, role, userProjects);
                        users.add(user);

                        // Assign the developer for bug fixing
                        if (role == Role.DEVELOPER && assignedDeveloper == null) {
                            assignedDeveloper = user;
                        }
                    }

                    // Ensure at least one developer is present
                    if (assignedDeveloper == null) {
                        System.out.println("Error: No developer assigned to this project. Bug cannot be raised.");
                        break;
                    }

                    // Create the Project object with the new constructor
                    Project project = new Project(projectId, projectName, projectDescription, users);

                    // Raise the bug and assign it to the developer
                    tester.raiseBug(bugId, bugMessage, bugSeverity, project, assignedDeveloper);
                    bugRaised = true;
                    break;

                case 2:
                    if (!bugRaised) {
                        System.out.println("Error: No bugs have been raised yet. Please raise a bug first.");
                    } else {
                        System.out.println("Enter Bug ID to inspect:");
                        int inspectBugId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        try {
                            tester.inspectBug(inspectBugId);
                        } catch (BugNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 3:
                    if (!bugRaised) {
                        System.out.println("Error: No bugs have been raised yet. Please raise a bug first.");
                    } else {
                        tester.createReport();
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
