package com.example.simplenewschannel.repository;

import com.example.simplenewschannel.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Long userId);
    boolean existsByUserId(Long userId);

}
