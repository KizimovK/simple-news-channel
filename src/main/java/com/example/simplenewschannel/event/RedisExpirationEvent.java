package com.example.simplenewschannel.event;

import com.example.simplenewschannel.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handleRedisExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event) {
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();

        if (expiredRefreshToken == null) {
            throw new RuntimeException("Refresh token is null in handleRedisExpiredEvent function!");
        }

        log.info("Refresh token with key = {} has expired! Refresh token is: {} ", expiredRefreshToken.getId(),
                expiredRefreshToken.getToken());
    }
}
