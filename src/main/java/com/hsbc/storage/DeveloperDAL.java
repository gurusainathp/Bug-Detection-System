package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.exceptions.ProjectNotFoundException;
import com.hsbc.models.Bug;
import com.hsbc.models.BugStatus;
import com.hsbc.models.User;

import java.util.List;

public interface DeveloperDAL {

    //void getAllbugsByProjectId();
    void resolveBug(User user, int bugId, BugStatus newStatus);
    //this is to get bugs based on the project ids this can be used to display bugs depending on the id
    List<Bug> getAllBugs(User user);
    void createReport(int projectId) throws ProjectNotFoundException;
}
