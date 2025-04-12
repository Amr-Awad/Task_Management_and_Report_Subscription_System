package org.example.task.model.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;


/**
 * DTO for sign-in request.
 * Contains email and password fields.
 */
@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class SignInRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}


