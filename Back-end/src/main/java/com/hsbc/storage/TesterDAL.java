package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.models.BugSeverity;
import com.hsbc.models.Project;
import com.hsbc.models.User;

public interface TesterDAL {

    void raiseBug(int bugId, String bugMessage, BugSeverity bugSeverity, Project project, User assignedDeveloper);

    void inspectBug(int bugId) throws BugNotFoundException;

    void createReport();
}