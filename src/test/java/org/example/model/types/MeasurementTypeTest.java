package org.example.model.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementTypeTest {
    private static final String INVALID = "INVALID";
    private static final String VOLTAGE = "VOLTAGE";
    private static final String POWER = "POWER";
    private static final String TEMPERATURE = "TEMPERATURE";

    @ParameterizedTest
    @EnumSource(MeasurementType.class)
    void shouldReturnCorrectStringValues(MeasurementType measurementType) {
        assertEquals(measurementType.name(), measurementType.toString());
    }
    @Test
    void shouldHaveThreeDefinedMeasurementTypes() {
        assertEquals(3,
                MeasurementType.values().length,
                "Enum should have three values.");
    }
    @Test
    void shouldContainExpectedEnumValuesInOrder() {
        assertArrayEquals(
                new MeasurementType[]{
                        MeasurementType.TEMPERATURE,
                        MeasurementType.VOLTAGE,
                        MeasurementType.POWER},
                MeasurementType.values(),
                "Enum values should match expectations.");
    }
    @ParameterizedTest
    @ValueSource(strings = {VOLTAGE, TEMPERATURE, POWER})
    void shouldRetrieveEnumValueByName(String validName) {
        assertEquals(
                MeasurementType.valueOf(validName),
                MeasurementType.valueOf(validName),
                "Retrieving valid names should return the correct enum value.");
    }
    @ParameterizedTest
    @ValueSource(strings = {INVALID})
    void shouldThrowExceptionForInvalidName(String invalidName) {
        assertThrows(
                IllegalArgumentException.class,
                () -> MeasurementType.valueOf(invalidName),
                "Invalid name should result in an IllegalArgumentException.");
    }
}
