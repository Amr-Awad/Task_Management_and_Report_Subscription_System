package org.example.task.service;

import lombok.RequiredArgsConstructor;
import org.example.task.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    // This service can be used to manage user-related operations
    // such as updating user information, changing passwords, etc.
    // Currently, it only have function to add task to user

    private final UserRepository userRepository;
}
