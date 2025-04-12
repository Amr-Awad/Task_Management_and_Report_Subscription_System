package org.example.task.exception.apiException;

import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

public class SubscriptionNotFoundException extends ApiException {
    public SubscriptionNotFoundException(Long userId) {
        super("No subscription found for user ID: " + userId, HttpStatus.NOT_FOUND.value());
    }
}
