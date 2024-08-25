package com.hsbc.views;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.exceptions.ProjectNotFoundException;
import com.hsbc.models.BugStatus;
import com.hsbc.models.Project;
import com.hsbc.models.Role;
import com.hsbc.models.User;
import com.hsbc.storage.*;

import java.util.Scanner;

public class DeveloperMain {

//   private DeveloperImpl developerDAL;
//   private User loggedInUser;
//
//   public DeveloperMain(DeveloperImpl developerDAL, User loggedInUser) {
//       this.developerDAL = developerDAL;
//       this.loggedInUser = loggedInUser;
//   }





    public static void main(String[] args) {
        UserDAL userDAL = null;
        DeveloperDAL developerDAL = null;
        ManagerDAL managerDAL = null;
        TesterDAL testerDAL = null;
        try{
            userDAL = new UserImpli();
            developerDAL = new DeveloperImpl();
            managerDAL = new ManagerImpl();

            //guru implemetn kar do
            testerDAL = new TesterImpl();

            //should create user
            userDAL.signUpUser(new User("MANAGER@gmail.comm", "password12345", Role.MANAGER));
            userDAL.signUpUser(new User("DEVELOPER@gmail.comm", "password12345", Role.DEVELOPER));
            userDAL.signUpUser(new User("TESTER@gmail.comm", "password12345", Role.TESTER));


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        //making og in values
        User manager = null;
        User developer = null;
        User tester = null;


        //loging user
        try {
            manager = userDAL.loginUser("MANAGER@gmail.comm", "password12345");
            developer = userDAL.loginUser("DEVELOPER@gmail.comm", "password12345");
            tester = userDAL.loginUser("TESTER@gmail.comm", "password12345");
        }catch (RuntimeException  e) {
            System.out.println(e.getMessage());
        }


        //adding manger functions
        try{
            //adding new project
            managerDAL.addNewProject(manager, new Project("bugs detection", "this project finds  bugs"));
            //addding developer
            managerDAL.addDeveloper(manager, 1, developer);
            //adding tester
            managerDAL.addTester(manager, 1, tester);



            //all not assigned bugs in the given project
            System.out.println(managerDAL.getNotAssignedBugs(manager, 1));

            //assign bug to manager
            managerDAL.AssignBugsToDeveloper(manager, 2, developer);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //get all fucntions
        System.out.println(managerDAL.getAllDevelopers(manager));
        //System.out.println(managerDAL.getAllTesters(manager));

        //developer dal functions
        try{
            //this works
            System.out.println(developerDAL.getAllBugs(developer));
            developerDAL.resolveBug(developer, 2, BugStatus.IN_PROGRESS);
            System.out.println(developerDAL.getAllBugs(developer));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }















































//
//    public void displayMenu() {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("\nDeveloper Dashboard");
//            System.out.println("1. View Assigned Projects");
//            System.out.println("2. View Bugs in a Project");
//            System.out.println("3. Mark Bug as Resolved");
//            System.out.println("4. Logout");
//            System.out.print("Enter your choice: ");
//            choice = scanner.nextInt();
//            scanner.nextLine();  // Consume newline
//
//            switch (choice) {
//                case 1:
//                    viewAssignedProjects();
//                    break;
//                case 2:
//                    viewBugsInProject(scanner);
//                    break;
//                case 3:
//                    markBugAsResolved(scanner);
//                    break;
//                case 4:
//                    System.out.println("Logging out...");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 4);
//
//        scanner.close();
//    }
//
//    private void viewAssignedProjects() {
//        // Assuming the logged-in user has a list of assigned projects.
//        if (loggedInUser.getProjects().isEmpty()) {
//            System.out.println("No projects assigned to you.");
//        } else {
//            System.out.println("Assigned Projects:");
//            loggedInUser.getProjects().forEach(project -> {
//                System.out.println("Project ID: " + project.getProjectId() + ", Project Name: " + project.getProjectName());
//            });
//        }
//    }
//
//    private void viewBugsInProject(Scanner scanner) {
//        System.out.print("Enter Project ID to view bugs: ");
//        int projectId = scanner.nextInt();
//        scanner.nextLine();  // Consume newline
//
//        try {
//            developerDAL.createReport(projectId);
//        } catch (ProjectNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void markBugAsResolved(Scanner scanner) {
//        System.out.print("Enter Bug ID to mark as resolved: ");
//        int bugId = scanner.nextInt();
//        scanner.nextLine();  // Consume newline
//
//        System.out.print("Enter Project ID: ");
//        int projectId = scanner.nextInt();
//        scanner.nextLine();  // Consume newline
//
//        try {
//            developerDAL.resolveBug(bugId, projectId);
//        } catch (BugNotFoundException | ProjectNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
