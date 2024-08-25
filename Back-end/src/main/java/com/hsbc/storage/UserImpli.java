package com.hsbc.storage;

import com.hsbc.exceptions.InvalidCredentialsException;
import com.hsbc.exceptions.UserAlreadyExist;
import com.hsbc.models.Role;
import com.hsbc.models.User;


//import these classes if there's an error
import java.util.ResourceBundle;
import java.sql.*;
import com.hsbc.helpers.MySQLHelper;

//these are general functions to all people
public class UserImpli implements UserDAL{
    //  private static Connection connection;
    private static ResourceBundle resourceBundle;
    private PreparedStatement preparedStatement;

    //constructor getting resoruce bundle instance
    public UserImpli()  {
        //connection= MySQLHelper.getConnection();
        resourceBundle=ResourceBundle.getBundle("db");
    }



    //adding user sign up is directly adding to use data base
    @Override
    public void signUpUser(User user) {
        String query=resourceBundle.getString("addUser");    //getting query from property
        int rows = 0;  //rows to throw execption if user already exist

        //self close try catch block
        try(Connection connection=MySQLHelper.getConnection()) {
            //adding values to prepared statement to send to the table
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            //execute query
            rows= preparedStatement.executeUpdate();

        }catch (SQLIntegrityConstraintViolationException e){
            throw new UserAlreadyExist("This email already exists");
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e); //need to customn exeptions
        }

        if(rows>0)
            System.out.println("User added successfully");



    }


    //login is retreving object from user and returning the user which is used in all other dals
    @Override
    public User loginUser(String email, String password) throws InvalidCredentialsException {
        User user = null;
        String query = resourceBundle.getString("loginUser");  // getting query from property

        try (Connection connection = MySQLHelper.getConnection()) {
            // preparing the statement to execute the query
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            // executing the query and getting the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // checking if the user exists
            if (resultSet.next()) {
                // retrieving user details from the result set
                int userId = resultSet.getInt("userId");
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("password");
                String role = resultSet.getString("role");

                // creating a User object and returning it
                user = new User(userId, userEmail, userPassword, Role.valueOf(role));
                System.out.println("User logged in");
            } else {
                // if no user found, throw custom exception
                throw new InvalidCredentialsException("Invalid email or password.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e); // handle SQL or ClassNotFound exceptions
        }

        return user;
    }




    //adds signed up people into the database



}