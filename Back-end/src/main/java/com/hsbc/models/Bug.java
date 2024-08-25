package com.hsbc.models;

import java.time.LocalDateTime;

public class Bug {
    private int bugId;
    private String bugMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BugStatus bugStatus;
    private BugSeverity bugSeverity;
    private int projectId;
    private int userId;  //foregin key to developer to which the bug has been assigned

    public Bug() {}

    public Bug(int bugId, String bugMessage, LocalDateTime createdAt, LocalDateTime updatedAt, BugStatus bugStatus, BugSeverity bugSeverity, int projectId) {
        this.bugId = bugId;
        this.bugMessage = bugMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bugStatus = bugStatus;
        this.bugSeverity = bugSeverity;
        this.projectId = projectId;
    }

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public String getBugMessage() {
        return bugMessage;
    }

    public void setBugMessage(String bugMessage) {
        this.bugMessage = bugMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BugStatus getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(BugStatus bugStatus) {
        this.bugStatus = bugStatus;
    }

    public BugSeverity getBugSeverity() {
        return bugSeverity;
    }

    public void setBugSeverity(BugSeverity bugSeverity) {
        this.bugSeverity = bugSeverity;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getDeveloperId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "bugId=" + bugId +
                ", bugMessage='" + bugMessage + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", bugStatus=" + bugStatus +
                ", bugSeverity=" + bugSeverity +
                ", project=" + projectId +
                '}';
    }
}
