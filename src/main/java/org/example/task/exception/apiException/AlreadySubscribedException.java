package org.example.task.exception.apiException;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

public class AlreadySubscribedException extends ApiException {
    public AlreadySubscribedException(String email) {
        super("User with email " + email + " is already subscribed.", HttpStatus.CONFLICT.value());
    }
}

