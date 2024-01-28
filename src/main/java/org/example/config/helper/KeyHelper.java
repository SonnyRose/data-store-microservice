package org.example.config.helper;

import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class KeyHelper {
    private final static String defaultPrefix = "app";
    private static String prefix = null;
    public static void setPrefix(String ketPrefix){
        prefix = ketPrefix;
    }
    public static String getKey(String key){
        return getPrefix() + ":" + key;
    }
    public static String getPrefix(){
        return Objects.requireNonNullElse(prefix, defaultPrefix);
    }

}
