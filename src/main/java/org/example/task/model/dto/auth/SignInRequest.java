package org.example.task.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "User login credentials")
public class SignInRequest {

    @Email
    @NotBlank
    @Schema(description = "User's email address", example = "user@example.com", required = true)
    private String email;

    @NotBlank
    @Schema(description = "User's password", example = "SecurePassword123!", required = true)
    private String password;
}