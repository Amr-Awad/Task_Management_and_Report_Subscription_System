package org.example.task.model.dto.task;

import lombok.*;
import org.example.task.util.enums.TaskStatus;

import java.time.LocalDate;

/**
 * TaskFilterRequest is a DTO class that represents the filter criteria for tasks.
 * It contains fields for the date range and task status.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class TaskFilterRequest {

    private LocalDate fromDate;
    private LocalDate toDate;
    private TaskStatus status;
}
