package com.example.java_app_users.infrastructure.persistance.user;

import com.example.java_app_users.domain.model.User;
import com.example.java_app_users.domain.port.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaRepo;

    public UserRepositoryAdapter(JpaUserRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setCreatedAt(user.getCreatedAt());
        UserEntity saved = jpaRepo.save(entity);
        user.setId(saved.getId());
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll(Sort order) {
        return jpaRepo.findAll(order).stream().map(this::toDomain).toList();
    }

    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setCreatedAt(entity.getCreatedAt());
        return user;
    }
}
