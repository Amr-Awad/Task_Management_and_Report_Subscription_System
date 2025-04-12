package org.example.task.exception.apiException;

import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException(String email) {
        super("Email already in use: " + email, HttpStatus.CONFLICT.value());
    }
}
