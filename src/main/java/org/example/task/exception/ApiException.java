package org.example.task.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Base class for all custom API exceptions")
public abstract class ApiException extends RuntimeException {
    @Schema(description = "HTTP status code", example = "400")
    private final int status;

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}