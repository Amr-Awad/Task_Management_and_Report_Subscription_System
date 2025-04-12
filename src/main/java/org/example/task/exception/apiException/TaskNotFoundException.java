package org.example.task.exception.apiException;


import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends ApiException {
    public TaskNotFoundException(Long id) {
        super("Task not found with ID: " + id, HttpStatus.NOT_FOUND.value());
    }
}
