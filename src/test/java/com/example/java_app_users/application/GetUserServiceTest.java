package com.example.java_app_users.application;

import com.example.java_app_users.application.user.impl.GetAllUsersService;
import com.example.java_app_users.application.user.impl.GetUserService;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetUserServiceTest {
    private UserRepository userRepository;
    private GetUserService getUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getUserService = new GetUserService(userRepository);
    }

    @Test
    void should_get_user_successfully() {
        UUID id = UUID.randomUUID();
        User user1 = new User(id, "Jean", "Jeancaardo@gmail.com", LocalDateTime.now());

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));

        Optional<User> get = getUserService.getUser(id);

        assertThat(get).isNotEmpty();
        assertThat(get.get()).isEqualTo(user1);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void should_return_empty_when_user_not_found() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = getUserService.getUser(id);

        assertThat(result).isEmpty();

        verify(userRepository, times(1)).findById(id);
    }
}
