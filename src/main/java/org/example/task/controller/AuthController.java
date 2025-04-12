package org.example.task.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.task.model.dto.auth.JwtResponse;
import org.example.task.model.dto.auth.SignInRequest;
import org.example.task.model.dto.auth.SignUpRequest;
import org.example.task.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest request) {
        JwtResponse jwt = authService.register(request);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest request) {
        JwtResponse jwt = authService.login(request);
        return ResponseEntity.ok(jwt);
    }
}
