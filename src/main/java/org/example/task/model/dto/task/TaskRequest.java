package org.example.task.model.dto.task;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.task.util.enums.TaskStatus;

import java.time.LocalDate;

/**
 * TaskRequest is a DTO class that represents a request to create or update a task.
 * It contains fields for the task's title, description, start date, due date, and completion date.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class TaskRequest {

    @NotBlank
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completionDate;
    private TaskStatus status;

}

