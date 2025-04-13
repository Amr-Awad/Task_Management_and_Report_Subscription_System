package org.example.task.model.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "User registration details")
public class SignUpRequest {

    @NotBlank
    @Schema(description = "User's display name", example = "John Doe", required = true, minLength = 3, maxLength = 50)
    private String username;

    @Email
    @NotBlank
    @Schema(description = "User's email address", example = "john@example.com", required = true)
    private String email;

    @NotBlank
    @Schema(description = "User's password", example = "SecurePassword123!", required = true, minLength = 6)
    private String password;
}