package com.example.java_app_users.infrastructure.rest;

import com.example.java_app_users.application.user.CreateUserUseCase;
import com.example.java_app_users.application.user.GetAllUsersUseCase;
import com.example.java_app_users.application.user.GetUserUseCase;
import com.example.java_app_users.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers= UserController.class)
class UserControllerTest {
    @TestConfiguration
    static class TestConfig {
        @Bean
        CreateUserUseCase createUser() {
            return mock(CreateUserUseCase.class);
        }

        @Bean
        GetUserUseCase getUser() {
            return mock(GetUserUseCase.class);
        }

        @Bean
        GetAllUsersUseCase getAllUsers(){
            return mock(GetAllUsersUseCase.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateUserUseCase createUser;

    @Autowired
    private GetUserUseCase getUser;

    @Autowired
    private GetAllUsersUseCase getAllUsers;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_create_user_and_return_200() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Alice", "alice@example.com", null);

        when(createUser.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Alice",
                        "email": "alice@example.com"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void should_return_404_when_user_not_found() throws Exception {
        UUID id = UUID.randomUUID();
        when(getUser.getUser(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_user_by_id() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new User(id, "Bob", "bob@example.com", createdAt);

        when(getUser.getUser(id)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.email").value("bob@example.com"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void should_get_all_users_sorted() throws Exception {
        User u1 = new User(UUID.randomUUID(), "Zoe", "zoe@example.com", LocalDateTime.now());
        User u2 = new User(UUID.randomUUID(), "Alex", "alex@example.com", LocalDateTime.now());

        when(getAllUsers.getAllUser(any())).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/users?field=name&order=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_return_400_when_invalid_field_param() throws Exception {
        mockMvc.perform(get("/users?field=invalidField&order=asc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void should_return_400_when_invalid_order_param() throws Exception {
        mockMvc.perform(get("/users?field=name&order=upward"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void should_return_400_when_invalid_request_body() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "",
                                "email": "notAnEmail"
                            }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists());
    }
}
