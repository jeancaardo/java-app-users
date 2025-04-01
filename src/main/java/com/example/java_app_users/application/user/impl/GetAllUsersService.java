package com.example.java_app_users.application.user.impl;

import com.example.java_app_users.application.user.GetAllUsersUseCase;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersService implements GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser(Sort order) {
        return this.userRepository.findAll(order);
    }
}
