package com.example.java_app_users.application.user;

import com.example.java_app_users.domain.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAllUser(Sort order);
}
