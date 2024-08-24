package com.hsbc;

import com.hsbc.models.*;
import com.hsbc.storage.DeveloperImpl;
import com.hsbc.views.DeveloperMain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create sample data

        // Create some sample bugs
        List<Bug> bugs = new ArrayList<>();
        Bug bug1 = new Bug(1, "NullPointerException in login module", LocalDateTime.now(), null, BugStatus.PENDING, BugSeverity.CRITICAL, null);
        Bug bug2 = new Bug(2, "UI misalignment in dashboard", LocalDateTime.now(), null, BugStatus.PENDING, BugSeverity.LIGHT, null);
        bugs.add(bug1);
        bugs.add(bug2);

        // Create a sample project
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project(1, "Bug Tracking System", "A system to track bugs", new ArrayList<>());
        projects.add(project1);

        // Assign the project to the bugs
        bug1.setProject(project1);
        bug2.setProject(project1);

        // Step 2: Create a sample user
        User developer = new User(1, "John Doe", "john.doe@example.com", Role.DEVELOPER, projects);

        // Step 3: Initialize the DeveloperImpl with projects and bugs
        DeveloperImpl developerDAL = new DeveloperImpl(projects, bugs);

        // Step 4: Initialize the DeveloperMain with the DeveloperImpl and logged-in user
        DeveloperMain developerMain = new DeveloperMain(developerDAL, developer);

        // Step 5: Display the developer menu
        developerMain.displayMenu();
    }
}
