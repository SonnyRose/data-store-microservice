package org.example.model;

import org.example.model.types.MeasurementType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTest {
    private Data createTestData() {
        Data data = new Data();
        data.setId(null);
        data.setSensorId(1L);
        data.setTimeStamp(LocalDateTime.of(2022, 1, 1, 12, 0));
        data.setMeasurement(25.0);
        data.setMeasurementType(MeasurementType.TEMPERATURE);
        return data;
    }
    @Test
    void testGettersAndSetters() {
        Data data = createTestData();

        assertEquals(1L, data.getSensorId());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), data.getTimeStamp());
        assertEquals(25.0, data.getMeasurement());
        assertEquals(MeasurementType.TEMPERATURE, data.getMeasurementType());
    }
    @Test
    void testSensorIdInteraction() {
        Data data = createTestData();
        MeasurementType measurementTypeMock = Mockito.mock(MeasurementType.class);
        data.setMeasurementType(measurementTypeMock);

        assertEquals(measurementTypeMock, data.getMeasurementType());
    }
    @Test
    void testEqualsAndHashCode() {
        Data data1 = createTestData();
        Data data2 = createTestData();

        assertEquals(data1, data1);
        assertEquals(data1.hashCode(), data1.hashCode());

        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }
    @Test
    void testToString() {
        Data data = createTestData();

        String toStringResult = data.toString();

        String expectedString = String.format(
                Locale.ENGLISH,
                "Data(id=%d, sensorId=%d, timeStamp=%s, measurement=%.1f, measurementType=%s)",
                data.getId(), data.getSensorId(), data.getTimeStamp(), data.getMeasurement(), data.getMeasurementType());

        assertEquals(expectedString, toStringResult);
    }
}
