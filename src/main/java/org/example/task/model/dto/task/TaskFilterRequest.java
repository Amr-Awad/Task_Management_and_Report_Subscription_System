package org.example.task.model.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.task.util.enums.TaskStatus;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "Task filter criteria")
public class TaskFilterRequest {

    @Schema(description = "Filter tasks from this date (inclusive)", example = "2025-01-01")
    private LocalDate fromDate;

    @Schema(description = "Filter tasks to this date (inclusive)", example = "2025-12-31")
    private LocalDate toDate;

    @Schema(description = "Filter by task status", implementation = TaskStatus.class)
    private TaskStatus status;
}