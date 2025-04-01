package com.example.java_app_users.domain.port;

import com.example.java_app_users.domain.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    List<User> findAll(Sort order);
}
