package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown when user is already subscribed")
public class AlreadySubscribedException extends ApiException {
    public AlreadySubscribedException(String email) {
        super("User with email " + email + " is already subscribed.", HttpStatus.CONFLICT.value());
    }
}