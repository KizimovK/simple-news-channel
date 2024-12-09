package com.example.simplenewschannel.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisStartCleanerEvent {
    private final StringRedisTemplate redisTemplate;

    @EventListener(ContextRefreshedEvent.class)
    public void redisCleanerOnStartup() {
        log.info("Clearing Redis on start application");
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.flushAll();
            return null;
        });
    }
}
