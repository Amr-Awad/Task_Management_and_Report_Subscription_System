package org.example.task.model.dto.task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.task.util.enums.TaskStatus;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "Task details response")
public class TaskResponse {

    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Complete project documentation")
    private String title;

    @Schema(description = "Task description", example = "Write API documentation")
    private String description;

    @Schema(description = "Start date", example = "2025-04-01")
    private LocalDate startDate;

    @Schema(description = "Due date", example = "2025-04-30")
    private LocalDate dueDate;

    @Schema(description = "Completion date", example = "2025-04-25")
    private LocalDate completionDate;

    @Schema(description = "Task status", implementation = TaskStatus.class)
    private TaskStatus status;
}