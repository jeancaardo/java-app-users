package com.example.java_app_users.application.user;

import com.example.java_app_users.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {
    Optional<User> getUser(UUID userId);
}
