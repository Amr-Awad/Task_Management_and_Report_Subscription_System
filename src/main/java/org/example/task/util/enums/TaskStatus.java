package org.example.task.util.enums;

public enum TaskStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    OVERDUE("overdue");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
