package org.example.task.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standard error response format")
public class ErrorResponse {
    @Schema(description = "Error message", example = "Task not found with ID: 123")
    private final String message;

    @Schema(description = "HTTP status code", example = "404")
    private final int status;

    @Schema(description = "Timestamp when error occurred", example = "2025-04-15T14:30:45.123456")
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }
    public int getStatus() {
        return status;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}