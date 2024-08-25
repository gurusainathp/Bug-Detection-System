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
        Bug bug1 = new Bug(1, "NullPointerException in login module", LocalDateTime.now(), null, BugStatus.PENDING, BugSeverity.CRITICAL, 12);
        Bug bug2 = new Bug(2, "UI misalignment in dashboard", LocalDateTime.now(), null, BugStatus.PENDING, BugSeverity.LIGHT, 12);
        bugs.add(bug1);
        bugs.add(bug2);

    }
}
