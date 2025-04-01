package com.example.java_app_users.application;

import com.example.java_app_users.application.user.impl.CreateUserService;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUserServiceTest {

    private UserRepository userRepository;
    private CreateUserService createUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        createUserService = new CreateUserService(userRepository);
    }

    @Test
    void should_create_user_successfully() {
        String name = "Alice";
        String email = "alice@example.com";

        when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(), name, email, LocalDateTime.now()));

        User created = createUserService.createUser(new User(null, name, email, null));

        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getEmail()).isEqualTo(email);
        assertThat(created.getId()).isInstanceOf(UUID.class);

        verify(userRepository, times(1)).save(any());
    }
}
