package com.example.java_app_users.application;

import com.example.java_app_users.application.user.impl.GetAllUsersService;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetAllUsersServiceTest {
    private UserRepository userRepository;
    private GetAllUsersService getAllUsersService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getAllUsersService = new GetAllUsersService(userRepository);
    }

    @Test
    void should_get_all_users_successfully() {
        User user1 = new User(UUID.randomUUID(), "Jean", "Jeancaardo@gmail.com", LocalDateTime.now());
        User user2 = new User(UUID.randomUUID(), "Maria", "AMariaGonzalez@gmail.com", LocalDateTime.now());
        List<User>users = List.of(user1, user2);
        when(userRepository.findAll(any())).thenReturn(users);

        List<User> get = getAllUsersService.getAllUser(Sort.by(Sort.Direction.DESC, "createdAt"));

        assertThat(get).isNotEmpty();
        assertThat(get.size()).isEqualTo(2);

        verify(userRepository, times(1)).findAll(any());
    }
}
