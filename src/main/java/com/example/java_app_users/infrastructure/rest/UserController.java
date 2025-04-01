package com.example.java_app_users.infrastructure.rest;

import com.example.java_app_users.application.user.CreateUserUseCase;
import com.example.java_app_users.application.user.GetAllUsersUseCase;
import com.example.java_app_users.application.user.GetUserUseCase;
import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.infrastructure.exception.InvalidSortParamException;
import com.example.java_app_users.infrastructure.exception.UserNotFoundException;
import com.example.java_app_users.infrastructure.rest.dto.CreateUserRequest;
import com.example.java_app_users.infrastructure.rest.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Set<String> ALLOWED_FIELDS = Set.of("name", "email", "createdAt");
    private static final Set<String> ALLOWED_ORDERS = Set.of("asc", "desc");

    private final CreateUserUseCase createUser;
    private final GetUserUseCase getUser;
    private final GetAllUsersUseCase getAllUsers;

    public UserController(CreateUserUseCase createUser,
                          GetUserUseCase getUser,
                          GetAllUsersUseCase getAllUsers) {
        this.createUser = createUser;
        this.getUser = getUser;
        this.getAllUsers = getAllUsers;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = createUser.createUser(new User(null, request.name(), request.email(), null));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return getUser.getUser(UUID.fromString(id))
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(name="field", defaultValue = "createdAt") String field,
                                                          @RequestParam(name="order", defaultValue = "desc") String order){

        if (!ALLOWED_FIELDS.contains(field)) {
            throw new InvalidSortParamException(field, true);
        }

        if (!ALLOWED_ORDERS.contains(order)) {
            throw new InvalidSortParamException(order, false);
        }

        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort sort = Sort.by(direction, field);

        return ResponseEntity.ok(getAllUsers.getAllUser(sort)
                .stream()
                .map(this::toDto)
                .toList());
    }

    private UserResponse toDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }
}