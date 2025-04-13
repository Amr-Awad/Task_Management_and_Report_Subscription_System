package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown when subscription is not found")
public class SubscriptionNotFoundException extends ApiException {
    public SubscriptionNotFoundException(Long userId) {
        super("No subscription found for user ID: " + userId, HttpStatus.NOT_FOUND.value());
    }
}