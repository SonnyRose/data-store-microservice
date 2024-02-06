
package org.example.config;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.*;

public class RedisConfigTest {
    @Test
    public void testJedisPoolCreationWithValidConfig() {
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost("localhost");
        redisConfig.setPort(6379);

        JedisPool jedisPool = redisConfig.jedisPool();

        // Perform assertions
        assertEquals("localhost", redisConfig.getHost());
        assertEquals(6379, redisConfig.getPort());
    }
    @Test
    public void testInvalidConfig() {
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost("invalid_host");
        redisConfig.setPort(-1);

        assertThrows(IllegalArgumentException.class, redisConfig::jedisPool);
    }
    @Test
    public void testSingletonBehavior() {
        RedisConfig redisConfig = new RedisConfig();

        JedisPool jedisPool1 = redisConfig.jedisPool();
        JedisPool jedisPool2 = redisConfig.jedisPool();

        assertSame(jedisPool1, jedisPool2);
    }
}
