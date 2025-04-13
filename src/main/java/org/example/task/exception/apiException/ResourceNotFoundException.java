package org.example.task.exception.apiException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.task.exception.ApiException;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception thrown when a resource is not found")
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id, HttpStatus.NOT_FOUND.value());
    }
}