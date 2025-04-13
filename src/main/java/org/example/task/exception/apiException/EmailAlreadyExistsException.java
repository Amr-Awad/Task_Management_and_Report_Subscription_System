package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown when email is already in use")
public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException(String email) {
        super("Email already in use: " + email, HttpStatus.CONFLICT.value());
    }
}