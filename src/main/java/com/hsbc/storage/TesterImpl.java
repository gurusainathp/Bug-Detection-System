package com.hsbc.storage;

import com.hsbc.exceptions.BugNotFoundException;
import com.hsbc.models.Bug;
import com.hsbc.models.BugSeverity;
import com.hsbc.models.BugStatus;
import com.hsbc.models.Project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TesterImpl implements TesterDAL{
    private List<Bug> bugList = new ArrayList<>();
    @Override
    public void raiseBug(int bugId, String bugMessage, BugSeverity bugSeverity, Project project) {
        Bug bug = new Bug(bugId, bugMessage, LocalDateTime.now(), null, BugStatus.PENDING, bugSeverity, project);
        bugList.add(bug);
        System.out.println("Bug raised: " + bug);


    }
    private Bug findBugById(int bugId) throws BugNotFoundException {
        return bugList.stream()
                .filter(bug -> bug.getBugId() == bugId)
                .findFirst()
                .orElseThrow(() -> new BugNotFoundException("Bug with ID " + bugId + " not found."));
    }

    @Override
    public void inspectBug(int bugId) throws BugNotFoundException {
        Bug bug = findBugById(bugId);

        System.out.println("Inspecting Bug: " + bug);

        // Inspection logic
        if (bug.getBugSeverity() == BugSeverity.CRITICAL) {
            System.out.println("This is a critical bug. Immediate action is required.");
            // Possibly escalate or update the status
            bug.setBugStatus(BugStatus.IN_PROGRESS);
        } else if (bug.getBugSeverity() == BugSeverity.MODERATE) {
            System.out.println("This is a moderate bug. Schedule it for fixing soon.");
            // Update the status if needed
            bug.setBugStatus(BugStatus.IN_PROGRESS);
        } else if (bug.getBugSeverity() == BugSeverity.LIGHT) {
            System.out.println("This is a light bug. It can be fixed later.");
            // No immediate action, but update timestamp
            bug.setUpdatedAt(LocalDateTime.now());
        }

        // Update the timestamp to indicate that the bug has been inspected
        bug.setUpdatedAt(LocalDateTime.now());

        System.out.println("Bug after inspection: " + bug);

    }


    @Override
    public void createReport() {
        System.out.println("Creating bug report...");
        for (Bug bug : bugList) {
            System.out.println(bug);
        }
        System.out.println("Bug report created.");


    }
}
