package org.example.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task.exception.ErrorResponse;
import org.example.task.model.dto.task.TaskFilterRequest;
import org.example.task.model.dto.task.TaskRequest;
import org.example.task.model.dto.task.TaskResponse;
import org.example.task.service.TaskService;
import org.example.task.util.enums.TaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management APIs")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "Create task",
            description = "Create a new task with title, description, and dates",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task created successfully",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication auth) {
        return ResponseEntity.ok(taskService.createTask(request, auth));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Task not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<TaskResponse> getTaskById(
            @Parameter(description = "ID of task to retrieve", required = true)
            @PathVariable Long id,
            Authentication auth) {
        return ResponseEntity.ok(taskService.getTaskByIdForUser(id, auth));
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all tasks",
            description = "Retrieve all tasks for the authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tasks",
                            content = @Content(schema = @Schema(implementation = TaskResponse[].class)))
            }
    )
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication auth) {
        return ResponseEntity.ok(taskService.getAllTasksForUser(auth));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update task",
            description = "Update an existing task",
            parameters = {
                    @Parameter(name = "id", description = "Task ID", required = true, in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            }
    )
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication auth) {
        return ResponseEntity.ok(taskService.updateTask(id, request, auth));
    }

    @PatchMapping("/{id}/complete")
    @Operation(
            summary = "Mark task as completed",
            description = "Update task status to COMPLETED",
            parameters = {
                    @Parameter(name = "id", description = "Task ID", required = true, in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task marked as completed",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            }
    )
    public ResponseEntity<TaskResponse> completeTask(
            @PathVariable Long id,
            Authentication auth) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id, auth));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete task",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task marked for deletion"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Task not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<Map<String, String>> softDeleteTask(
            @Parameter(description = "ID of task to delete", required = true)
            @PathVariable Long id,
            Authentication auth) {
        taskService.deleteTask(id, auth);
        return ResponseEntity.ok(Map.of("message", "Task deleted safely. Will be removed in 24h if not restored."));
    }

    @DeleteMapping
    @Operation(
            summary = "Soft delete tasks in date range",
            description = "Mark all tasks within date range for deletion",
            parameters = {
                    @Parameter(name = "from", description = "Start date (YYYY-MM-DD)", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "to", description = "End date (YYYY-MM-DD)", required = true, in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks marked for deletion",
                            content = @Content(schema = @Schema(example = "{\"message\": \"X tasks safely deleted. Will be cleaned in 24h.\"}"))),
                    @ApiResponse(responseCode = "400", description = "Invalid date range")
            }
    )
    public ResponseEntity<Map<String, String>> softDeleteTasksInRange(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to,
            Authentication auth) {
        int count = taskService.deleteTasksByDateRange(from, to, auth);
        return ResponseEntity.ok(Map.of("message", count + " tasks safely deleted. Will be cleaned in 24h."));
    }

    @PostMapping("/restore")
    @Operation(
            summary = "Restore last deleted task(s)",
            description = "Restore the most recently soft-deleted task(s)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restored task(s)",
                            content = @Content(schema = @Schema(implementation = TaskResponse[].class))),
                    @ApiResponse(responseCode = "404", description = "No tasks to restore")
            }
    )
    public ResponseEntity<List<TaskResponse>> restoreLastDeletedTask(Authentication auth) {
        return ResponseEntity.ok(taskService.restoreLastDeleted(auth));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter tasks",
            description = "Filter tasks by date range and/or status",
            parameters = {
                    @Parameter(name = "from", description = "Start date (YYYY-MM-DD)", in = ParameterIn.QUERY),
                    @Parameter(name = "to", description = "End date (YYYY-MM-DD)", in = ParameterIn.QUERY),
                    @Parameter(name = "status", description = "Task status", in = ParameterIn.QUERY,
                            schema = @Schema(implementation = TaskStatus.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered tasks",
                            content = @Content(schema = @Schema(implementation = TaskResponse[].class)))
            }
    )
    public ResponseEntity<List<TaskResponse>> filterTasks(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) TaskStatus status,
            Authentication auth) {
        TaskFilterRequest request = new TaskFilterRequest(from, to, status);
        return ResponseEntity.ok(taskService.filterTasks(request, auth));
    }
}