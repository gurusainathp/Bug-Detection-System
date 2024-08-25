package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.models.BugSeverity;
import com.hsbc.models.Project;

public interface TesterDAL {

    void raiseBug(int bugId, String bugMessage, BugSeverity bugSeverity, int ProjectId);

    void inspectBug(int bugId) throws BugNotFoundException;

    void createReport();
}
