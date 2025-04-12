package org.example.task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request, Authentication auth) {
        return ResponseEntity.ok(taskService.createTask(request, auth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @Valid @RequestBody TaskRequest request,
                                                   Authentication auth) {
        return ResponseEntity.ok(taskService.updateTask(id, request, auth));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteTask(@PathVariable Long id, Authentication auth) {
        taskService.deleteTask(id, auth);
        return ResponseEntity.ok(Map.of("message", "Task deleted safely. Will be removed in 24h if not restored."));
    }

    @DeleteMapping
    public ResponseEntity<?> softDeleteTasksInRange(@RequestParam LocalDate from,
                                                    @RequestParam LocalDate to,
                                                    Authentication auth) {
        int count = taskService.deleteTasksByDateRange(from, to, auth);
        return ResponseEntity.ok(Map.of("message", count + " tasks safely deleted. Will be cleaned in 24h."));
    }

    @PostMapping("/restore")
    public ResponseEntity<TaskResponse> restoreLastDeletedTask(Authentication auth) {
        return ResponseEntity.ok((TaskResponse) taskService.restoreLastDeleted(auth));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> filterTasks(@RequestParam(required = false) LocalDate from,
                                                          @RequestParam(required = false) LocalDate to,
                                                          @RequestParam(required = false) TaskStatus status,
                                                          Authentication auth) {
        TaskFilterRequest request = new TaskFilterRequest(from, to, status);
        return ResponseEntity.ok(taskService.filterTasks(request, auth));
    }
}

