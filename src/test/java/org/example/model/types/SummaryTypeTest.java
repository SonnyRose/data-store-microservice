package org.example.model.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryTypeTest {
    private static final String MIN = "MIN";
    private static final String MAX = "MAX";
    private static final String AVERAGE = "AVERAGE";
    private static final String SUM = "SUM";

    @ParameterizedTest
    @EnumSource(SummaryType.class)
    public void shouldReturnCorrectStringValues(SummaryType summaryType) {
        assertEquals(summaryType.name(), summaryType.toString());
    }
    @Test
    public void shouldHaveFourDefinedSummaryTypes() {
        assertEquals(4,
                SummaryType.values().length,
                "Enum should have four values.");
    }
    @Test
    public void shouldContainExpectedEnumValuesInOrder() {
        assertArrayEquals(
                new SummaryType[]{
                        SummaryType.MIN,
                        SummaryType.MAX,
                        SummaryType.AVERAGE,
                        SummaryType.SUM},
                SummaryType.values(),
                "Enum values should match expectations.");
    }
    @ParameterizedTest
    @ValueSource(strings = {MIN, MAX, AVERAGE, SUM})
    public void shouldRetrieveEnumValueByName(String validName) {
        assertEquals(
                SummaryType.valueOf(validName),
                SummaryType.valueOf(validName),
                "Retrieving valid names should return the correct enum value.");
    }
    @ParameterizedTest
    @ValueSource(strings = {"INVALID"})
    public void shouldThrowExceptionForInvalidName(String invalidName) {
        assertThrows(
                IllegalArgumentException.class,
                () -> SummaryType.valueOf(invalidName),
                "Invalid name should result in an IllegalArgumentException.");
    }
}
