package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
