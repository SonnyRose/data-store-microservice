package org.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Getter
@Setter
public class RedisConfig {
    @Value("${REDIS_HOST}")
    private String host;
    @Value("${REDIS_PORT}")
    private int port;
    @Bean
    public JedisPool jedisPool() {
        if (host == null || port <= 0) {
            throw new IllegalArgumentException("Invalid host or port");
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setJmxEnabled(false);
        try {
            return new JedisPool(jedisPoolConfig, host, port);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JedisPool", e);
        }
    }
}