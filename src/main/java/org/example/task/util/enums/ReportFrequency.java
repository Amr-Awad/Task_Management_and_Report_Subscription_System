package org.example.task.util.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Report frequency options")
public enum ReportFrequency {
    @Schema(description = "Daily reports") DAILY("daily"),
    @Schema(description = "Weekly reports") WEEKLY("weekly"),
    @Schema(description = "Monthly reports") MONTHLY("monthly");

    private final String frequency;

    ReportFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }
}