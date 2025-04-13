package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown when task is not found")
public class TaskNotFoundException extends ApiException {
    public TaskNotFoundException(Long id) {
        super("Task not found with ID: " + id, HttpStatus.NOT_FOUND.value());
    }
}