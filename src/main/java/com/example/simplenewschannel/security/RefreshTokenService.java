package com.example.simplenewschannel.security;

import com.example.simplenewschannel.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> findByRefreshToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken checkRefreshToken(RefreshToken token);
    boolean isHasRefreshTokenByUserId(Long userId);
    void deleteByUserId(Long userId);
}
