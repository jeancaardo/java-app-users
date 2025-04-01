package com.example.java_app_users.infrastructure.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User not found with id: " + id);
    }
}
