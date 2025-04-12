package org.example.task.model.dto.task;
import lombok.*;
import org.example.task.util.enums.TaskStatus;
import java.time.LocalDate;

/**
 * TaskResponse is a DTO class that represents the response for a task.
 * It contains fields for the task's ID, title, description, start date, due date, completion date, and status.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completionDate;
    private TaskStatus status;
}
