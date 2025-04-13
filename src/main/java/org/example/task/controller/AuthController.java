package org.example.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task.exception.ErrorResponse;
import org.example.task.model.dto.auth.JwtResponse;
import org.example.task.model.dto.auth.SignInRequest;
import org.example.task.model.dto.auth.SignUpRequest;
import org.example.task.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and authentication APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "Register new user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "409", description = "Email already exists",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignUpRequest request) {
        JwtResponse jwt = authService.register(request);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signin")
    @Operation(summary = "Authenticate user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest request) {
        JwtResponse jwt = authService.login(request);
        return ResponseEntity.ok(jwt);
    }
}