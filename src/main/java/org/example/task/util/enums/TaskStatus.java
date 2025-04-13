package org.example.task.util.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task status options")
public enum TaskStatus {
    @Schema(description = "Task is pending") PENDING("pending"),
    @Schema(description = "Task is completed") COMPLETED("completed"),
    @Schema(description = "Task is overdue") OVERDUE("overdue");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}