package com.example.java_app_users.persistance.user;

import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.infrastructure.persistance.user.JpaUserRepository;
import com.example.java_app_users.infrastructure.persistance.user.UserEntity;
import com.example.java_app_users.infrastructure.persistance.user.UserRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserRepositoryAdapter.class) // Importamos el adapter manualmente
class UserRepositoryAdapterTest {

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    void should_save_user_correctly() {
        User user = new User(null, "Alice", "alice@example.com", LocalDateTime.now());

        User saved = userRepositoryAdapter.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(jpaUserRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void should_find_user_by_id() {
        UserEntity entity = new UserEntity();
        entity.setName("Bob");
        entity.setEmail("bob@example.com");
        entity.setCreatedAt(LocalDateTime.now());

        UserEntity savedEntity = jpaUserRepository.save(entity);

        Optional<User> result = userRepositoryAdapter.findById(savedEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Bob");
    }

    @Test
    void should_return_empty_when_user_not_found() {
        Optional<User> result = userRepositoryAdapter.findById(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void should_return_all_users_sorted() {
        LocalDateTime now = LocalDateTime.now();

        UserEntity u1 = new UserEntity();
        u1.setName("Charlie");
        u1.setEmail("charlie@example.com");
        u1.setCreatedAt(now.minusDays(1));

        UserEntity u2 = new UserEntity();
        u2.setName("Anna");
        u2.setEmail("anna@example.com");
        u2.setCreatedAt(now);

        jpaUserRepository.saveAll(List.of(u1, u2));

        List<User> users = userRepositoryAdapter.findAll(Sort.by(Sort.Direction.ASC, "name"));

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getName()).isEqualTo("Anna");
        assertThat(users.get(1).getName()).isEqualTo("Charlie");
    }
}