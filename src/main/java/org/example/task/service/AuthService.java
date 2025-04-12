package org.example.task.service;

import lombok.RequiredArgsConstructor;
import org.example.task.exception.apiException.EmailAlreadyExistsException;
import org.example.task.exception.apiException.InvalidCredentialsException;
import org.example.task.model.dto.auth.JwtResponse;
import org.example.task.model.dto.auth.SignInRequest;
import org.example.task.model.dto.auth.SignUpRequest;
import org.example.task.model.entity.User;
import org.example.task.repository.UserRepository;
import org.example.task.security.JwtUtils;
import org.example.task.security.UserDetailsImpl;
import org.example.task.util.enums.RoleType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public JwtResponse register(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.ROLE_USER);

        userRepository.save(user);

        // 🔑 Automatically generate token for the new user
        String token = jwtUtils.generateJwtToken(UserDetailsImpl.build(user));

        return new JwtResponse(token, user.getName(), user.getEmail());
    }

    public JwtResponse login(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtils.generateJwtToken(UserDetailsImpl.build(user));

        return new JwtResponse(token, user.getName(), user.getEmail());
    }
}
