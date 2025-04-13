package org.example.task.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Schema(description = "Authentication response containing JWT token and user details")
public class JwtResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "User's username", example = "john_doe")
    private String username;

    @Schema(description = "User's email address", example = "john@example.com")
    private String email;

    public JwtResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }
}