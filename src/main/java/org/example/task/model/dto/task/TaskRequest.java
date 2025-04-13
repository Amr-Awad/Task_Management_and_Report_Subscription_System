package org.example.task.model.dto.task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.task.util.enums.TaskStatus;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "Task creation/update request")
public class TaskRequest {

    @Schema(description = "Task title", example = "Complete project documentation", minLength = 3, maxLength = 100)
    private String title;

    @Schema(description = "Detailed task description", example = "Write API documentation for all endpoints")
    private String description;

    @Schema(description = "Task start date (YYYY-MM-DD)", example = "2025-04-01", required = true)
    private LocalDate startDate;

    @Schema(description = "Task due date (YYYY-MM-DD)", example = "2025-04-30", required = true)
    private LocalDate dueDate;

    @Schema(description = "Task completion date (YYYY-MM-DD)", example = "2025-04-25")
    private LocalDate completionDate;

    @Schema(description = "Task status", implementation = TaskStatus.class)
    private TaskStatus status;
}