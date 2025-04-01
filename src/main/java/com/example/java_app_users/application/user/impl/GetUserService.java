package com.example.java_app_users.application.user.impl;

import com.example.java_app_users.application.user.GetUserUseCase;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserService implements GetUserUseCase {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(UUID userId) {
        return this.userRepository.findById(userId);
    }
}
