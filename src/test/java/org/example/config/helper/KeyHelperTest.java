package org.example.config.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class KeyHelper {
    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(KeyHelper.class, "prefix", null);
    }
    @Test
    public void getKey_withDefaultPrefix_shouldReturnDefaultPrefixKey(){
        String key = "testKey";
        String expectedKey = "app:testKey";
        String actualKey = KeyHelper.
    }
}
