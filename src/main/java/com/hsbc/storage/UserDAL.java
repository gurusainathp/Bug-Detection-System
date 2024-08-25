package com.hsbc.storage;

import com.hsbc.exceptions.InvalidCredentialsException;
import com.hsbc.models.User;

public interface UserDAL {
    // User operations
    //sign up user is bascially adding the given user to the database
    void signUpUser(User user);

    //login in user using the giving credentails
    User loginUser(String email, String password)  throws InvalidCredentialsException; //returns user which is used in other dals

    //get all projects



}