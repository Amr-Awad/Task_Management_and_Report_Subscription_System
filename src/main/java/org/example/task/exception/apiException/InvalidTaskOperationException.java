package org.example.task.exception.apiException;


import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidTaskOperationException extends ApiException {
    public InvalidTaskOperationException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}