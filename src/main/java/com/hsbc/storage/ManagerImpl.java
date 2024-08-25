package com.hsbc.storage;

import com.hsbc.exceptions.*;
import com.hsbc.helpers.MySQLHelper;
import com.hsbc.models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerImpl implements ManagerDAL{

    //  private static Connection connection;
    private static ResourceBundle resourceBundle;
    private PreparedStatement preparedStatement;

    //constructor getting resoruce bundle instance
    public ManagerImpl()  {
        //connection= MySQLHelper.getConnection();
        resourceBundle=ResourceBundle.getBundle("db");
    }


    //adding to the user project table private because you cannoy access outside the class
    private void addToUserProjectTable(int userId, int projectId) {
        //System.out.println(userId + "  " + projectId);
        String query = resourceBundle.getString("addUserProject"); // Getting query from properties file

        // Self-closing try-with-resources block
        try (Connection connection = MySQLHelper.getConnection()) {
            // Prepare the SQL statement for inserting into User_Project
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, userId);

            // Execute the insert query
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("User added to project successfully.");
            } else {
                System.out.println("Failed to add user to project.");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            throw new RuntimeException("this user already assigened to project");
        }catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e); // Consider using a custom exception here
        }
    }


    //counting number of projets of the given user id
    public int countProjectsForUser(int userId) {
        int projectCount = 0;
        String query = resourceBundle.getString("countProjectsForUser");  // getting query from property

        // Using try-with-resources to ensure resources are closed properly
        try (Connection connection = MySQLHelper.getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()) {
                 projectCount = resultSet.getInt(1);
             }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle SQL or ClassNotFound exceptions with a custom message
            throw new RuntimeException("Error counting projects for user", e);
        }

        return projectCount;
    }


    //adding a new project
    @Override
    public void addNewProject(User user, Project project) {
        if(user.getRole() == Role.MANAGER){ //giving manager permisons
            String query = resourceBundle.getString("addProject"); // Getting query from properties file
            int generatedProjectId = -1;  // To hold the auto-generated projectId

            if(countProjectsForUser(user.getUserId()) >= 4)
                throw new MaxProjectLimit("Manager cannot be in more than " + 4 + " projects.");

            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection()) {
                // Prepare the SQL statement with the auto-generated key flag
                PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, project.getProjectName());
                preparedStatement.setString(2, project.getProjectDescription());

                // Execute the insert query
                int rows = preparedStatement.executeUpdate();

                // Retrieve the auto-generated project ID
                if (rows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedProjectId = generatedKeys.getInt(1);// Get the generated projectId
                            //addg values to the UserProject table
                            addToUserProjectTable(user.getUserId(), generatedProjectId); //adding values to many to many table
                        }
                    }
                } else {
                    throw new RuntimeException("Failed to add the project.");
                }
            }
            catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }

            System.out.println("Project added successfully with ID: " + generatedProjectId); //added to database

        }else{
            throw new UserNotManager("This user does not have the role MANAGER."); //add custom error
        }

    }


    @Override
    public List<Bug> getNotAssignedBugs(User user, int projectId) {

        // SQL query to get bugs with no assigned user for the given projectId
        String query = resourceBundle.getString("getNotAssignedBugs"); // Getting query from properties file
        List<Bug> bugs = new ArrayList<>();
        if(user.getRole() == Role.MANAGER){
            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Set the projectId in the prepared statement
                preparedStatement.setInt(1, projectId);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Process the results
                    while (resultSet.next()) {
                        int bugId = resultSet.getInt("bugId");
                        String bugMessage = resultSet.getString("bugMessage");
                        LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                        LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                        BugStatus bugStatus = BugStatus.valueOf(resultSet.getString("bugStatus"));
                        BugSeverity bugSeverity = BugSeverity.valueOf(resultSet.getString("bugSeverity"));

                        Bug bug = new Bug(bugId, bugMessage, createdAt, updatedAt, bugStatus, bugSeverity, projectId);
                        bugs.add(bug);
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }

            return bugs;
        }else{
            throw new UserNotManager("This user does not have the role MANAGER.");
        }

    }

    @Override
    public void AssignBugsToDeveloper(User user, int bugId, User devloper) {
        if (user.getRole() == Role.MANAGER) { // Check if the user has MANAGER permissions
            String query = resourceBundle.getString("assignBugToDeveloper"); // Getting query from properties file
            if(devloper.getRole() != Role.DEVELOPER){
                 throw new UserIsNotADeveloper("given user id is not of a developer");
            }
            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Set the bugId and developerId in the prepared statement
                preparedStatement.setInt(1, devloper.getUserId());
                preparedStatement.setInt(2, bugId);

                // Execute the update query
                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                    System.out.println("Bug with ID " + bugId + " assigned to developer with ID " + devloper.getUserId() + " successfully.");
                } else {
                    throw new BugNotFoundException("Bug with ID " + bugId + " not found.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }
        } else {
            throw new UserNotManager("This user does not have the role MANAGER."); // Custom exception for role check
        }
    }



    @Override
    public void addDeveloper(User user, int projectId, User developer) {
        if (user.getRole() == Role.MANAGER) {// Checking if the user has MANAGER permissions

            //checking if the user is a developer or not
            if(developer.getRole() == Role.DEVELOPER){

                //checking how many projects does developer has
                if(countProjectsForUser(developer.getUserId()) >= 1)
                    throw new MaxProjectLimit("developer cannot be in more than " + 1 + " projects.");

                addToUserProjectTable( developer.getUserId(), projectId);
                System.out.println("Develoepr added to the project");
            }
            else{
                throw new UserIsNotADeveloper("This user does not have the role developer.");
            }

        } else {
            throw new UserNotManager("This user does not have the role MANAGER."); // Custom exception for role check
        }
    }




    @Override
    public void addTester(User user, int projectId, User tester) {
        if (user.getRole() == Role.MANAGER) {// Checking if the user has MANAGER permissions

            //checking if the user is a developer or not
            if(tester.getRole() == Role.TESTER){

                //checking how many projects does developer has
                if(countProjectsForUser(tester.getUserId()) >= 2)
                    throw new MaxProjectLimit("tester cannot be in more than " + 2 + " projects.");

                addToUserProjectTable( tester.getUserId(), projectId);
                System.out.println("Tester added to the project");
            }
            else{
                throw new UserIsNotTester("This user does not have the role Tester.");
            }

        } else {
            throw new UserNotManager("This user does not have the role MANAGER."); // Custom exception for role check
        }
    }


    @Override
    public List<User> getAllTesters(User user) {
        if (user.getRole() == Role.MANAGER) { // Check if the user has MANAGER permissions
            String query = resourceBundle.getString("getAllTesters"); // Getting query from properties file
            List<User> testers = new ArrayList<>();

            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process the results
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String email = resultSet.getString("email");
                    Role role = Role.valueOf(resultSet.getString("role"));

                    // Create User object and add it to the list
                    User tester = new User(userId, email, "", role); // Assuming no name field and empty password
                    testers.add(tester);
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }

            return testers;
        } else {
            throw new UserNotManager("This user does not have the role MANAGER."); // Custom exception for role check
        }
    }


    @Override
    public List<User> getAllDevelopers(User user) {
        if (user.getRole() == Role.MANAGER) { // Check if the user has MANAGER permissions
            String query = resourceBundle.getString("getAllDevelopers"); // Getting query from properties file
            List<User> developers = new ArrayList<>();

            // Self-closing try-with-resources block
            try (Connection connection = MySQLHelper.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process the results
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String email = resultSet.getString("email");
                    Role role = Role.valueOf(resultSet.getString("role"));

                    // Create User object and add it to the list
                    User developer = new User(userId, email, "", role); // Assuming no name field and empty password
                    developers.add(developer);
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e); // Consider using a custom exception here
            }

            return developers;
        } else {
            throw new UserNotManager("This user does not have the role MANAGER."); // Custom exception for role check
        }
    }

}
