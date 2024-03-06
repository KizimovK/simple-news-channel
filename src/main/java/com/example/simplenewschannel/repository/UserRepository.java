package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    @Query("SELECT id FROM users")
    List<Long> findAllId();
}
