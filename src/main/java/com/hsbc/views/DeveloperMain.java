package com.hsbc.views;

import com.hsbc.storage.UserDAL;
import com.hsbc.models.Bug;
import com.hsbc.models.Project;
import com.hsbc.models.User;

import java.util.List;
import java.util.Scanner;

public class DeveloperMain {

    private UserDAL developerDAL;
    private User loggedInUser;

    public DeveloperMain(UserDAL developerDAL, User loggedInUser) {
        this.developerDAL = developerDAL;
        this.loggedInUser = loggedInUser;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nDeveloper Dashboard");
            System.out.println("1. View Assigned Projects");
            System.out.println("2. View Bugs in a Project");
            System.out.println("3. Mark Bug as Resolved");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAssignedProjects();
                    break;
                case 2:
                    viewBugsInProject(scanner);
                    break;
                case 3:
                    markBugAsResolved(scanner);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private void viewAssignedProjects() {
        List<Project> projects = developerDAL.getProjectsByUser(loggedInUser.getUserId());

        if (projects.isEmpty()) {
            System.out.println("No projects assigned to you.");
        } else {
            System.out.println("Assigned Projects:");
            for (Project project : projects) {
                System.out.println("Project ID: " + project.getProjectId() + ", Project Name: " + project.getProjectName());
            }
        }
    }

    private void viewBugsInProject(Scanner scanner) {
        System.out.print("Enter Project ID to view bugs: ");
        int projectId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<Bug> bugs = developerDAL.getBugsByProject(projectId);

        if (bugs.isEmpty()) {
            System.out.println("No bugs found for this project.");
        } else {
            System.out.println("Bugs in Project:");
            for (Bug bug : bugs) {
                System.out.println("Bug ID: " + bug.getBugId() + ", Message: " + bug.getBugMessage() + ", Status: " + bug.getBugStatus());
            }
        }
    }

    private void markBugAsResolved(Scanner scanner) {
        System.out.print("Enter Bug ID to mark as resolved: ");
        int bugId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        developerDAL.markBugAsResolved(bugId);
        System.out.println("Bug ID " + bugId + " marked as resolved.");
    }
}
