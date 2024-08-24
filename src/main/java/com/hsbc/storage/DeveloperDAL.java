package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.exceptions.ProjectNotFoundException;

public interface DeveloperDAL {
    void resolveBug(int bugId, int projectId) throws BugNotFoundException, ProjectNotFoundException;
    void createReport(int projectId) throws ProjectNotFoundException;
}
