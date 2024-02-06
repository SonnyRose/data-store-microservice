package org.example.config.schema;

import org.example.config.helper.KeyHelper;
import org.example.model.types.MeasurementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisSchemaTest {
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void sensorKeys_shouldReturnCorrectKey() {
        assertRedisSchemaKey("expectedKey", "sensors");
    }
    @Test
    void summaryKey_shouldReturnCorrectKey() {
        long sensorId = 1L;
        MeasurementType measurementType = MeasurementType.TEMPERATURE;
        String keyPrefix = "sensors:" + sensorId + ":" + measurementType.name().toLowerCase();
        assertRedisSchemaKey("expectedKey", keyPrefix);
    }
    private void assertRedisSchemaKey(String expectedKey, String keyToGenerate) {
        try (MockedStatic<KeyHelper> mockedKeyHelper = Mockito.mockStatic(KeyHelper.class)) {
            mockedKeyHelper.when(() -> KeyHelper.getKey(keyToGenerate)).thenReturn(expectedKey);

            // Assert against the corresponding method in RedisSchema
            if (keyToGenerate.equals("sensors")) {
                assertEquals(expectedKey, RedisSchema.sensorKeys());
            } else {
                // Extract sensorId and measurementType from keyToGenerate for summaryKey
                String[] parts = keyToGenerate.split(":");
                long sensorId = Long.parseLong(parts[1]);
                MeasurementType measurementType = MeasurementType.valueOf(parts[2].toUpperCase());

                assertEquals(expectedKey, RedisSchema.summaryKey(sensorId, measurementType));
            }
        }
    }

}
