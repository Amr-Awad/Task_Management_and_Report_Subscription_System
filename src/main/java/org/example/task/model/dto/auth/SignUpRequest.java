package org.example.task.model.dto.auth;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * SignUpRequest is a data transfer object (DTO) that represents the information required for a user to sign up.
 * It includes fields for username, email, and password, along with validation annotations to ensure that
 * the input meets certain criteria.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class SignUpRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

