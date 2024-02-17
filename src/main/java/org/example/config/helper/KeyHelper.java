package org.example.config.helper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class KeyHelper {
    private final static String defaultPrefix = "app";
    @Setter
    private static String prefix = null;
    public static String getKey(String key){
        return getPrefix() + ":" + key;
    }
    public static String getPrefix(){
        return Objects.requireNonNullElse(prefix, defaultPrefix);
    }

}
