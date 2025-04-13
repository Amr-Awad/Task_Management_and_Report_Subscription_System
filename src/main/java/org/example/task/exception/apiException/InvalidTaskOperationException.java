package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown for invalid task operations")
public class InvalidTaskOperationException extends ApiException {
    public InvalidTaskOperationException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}