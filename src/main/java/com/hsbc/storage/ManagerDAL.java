package com.hsbc.storage;

import com.hsbc.models.Bug;
import com.hsbc.models.Project;
import com.hsbc.models.User;

import java.util.List;

public interface ManagerDAL {

    void addNewProject(User user, Project project);
//    void getAllProjects(); this can be a user function

    List<Bug> getNotAssignedBugs(User user, int projectId); //finds the bugs which have not been assigned to the developer
    void AssignBugsToDeveloper(User user, int bugId, User devloper);

    void addDeveloper(User user, int projectId, User developer); //adding developer to the table
    List<User> getAllDevelopers(User user);

    void addTester(User user, int projectId, User tester);
    List<User> getAllTesters(User user);


}
