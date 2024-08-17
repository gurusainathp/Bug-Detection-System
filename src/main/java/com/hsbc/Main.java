package com.hsbc;

import com.hsbc.models.Role;
import com.hsbc.models.User;
import com.hsbc.storage.DeveloperDAL;
import com.hsbc.storage.DeveloperImpl;
import com.hsbc.storage.UserDAL;
import com.hsbc.views.DeveloperMain;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        UserDAL developerDAL = new DeveloperImpl();

        // Mock login (replace with actual login mechanism)
        User developer = new User(1, "John Doe", "john.doe@example.com", Role.DEVELOPER, new ArrayList<>());

        DeveloperMain developermain = new DeveloperMain(developerDAL, developer);
        developermain.displayMenu();
    }
}
