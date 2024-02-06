package org.example.config.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class KeyHelperTest {
    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(KeyHelper.class, "prefix", null);
    }
    @Test
    public void getKey_withDefaultPrefix_shouldReturnDefaultPrefixAndKey(){
        String key = "testKey";
        String expectedKey = "app:testKey";
        String actualKey = KeyHelper.getKey(key);
        assertEquals(actualKey, expectedKey);
    }
    @Test
    public void getKey_withCustomPrefix_shouldReturnCustomPrefixAndKey(){
        String customPrefix = "custom";
        String key = "testKey";
        String expectedKey = "custom:testKey";

        KeyHelper.setPrefix(customPrefix);

        String actualKey = KeyHelper.getKey(key);

        assertEquals(expectedKey, actualKey);
    }
    @Test
    public void getPrefix_withNoCustomPrefix_shouldReturnDefaultPrefix(){
        String expectedPrefix = "app";
        String actualPrefix = KeyHelper.getPrefix();
        assertEquals(expectedPrefix, actualPrefix);
    }
    @Test
    public void getPrefix_withCustomPrefix_shouldReturnCustomPrefix(){
        String customPrefix = "custom";
        KeyHelper.setPrefix(customPrefix);

        String actualPrefix = KeyHelper.getPrefix();

        assertEquals(customPrefix, actualPrefix);
    }
}
