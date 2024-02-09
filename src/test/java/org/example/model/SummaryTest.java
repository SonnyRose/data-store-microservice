package org.example.model;

import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryTest {
    private Summary summary;
    @BeforeEach
    public void setUp() {
        summary = new Summary();
    }
    @Test
    public void shouldAddEntryToEmptyValuesMap() {
        SummaryEntry entry = createSummaryEntry(SummaryType.MIN, 25.0, 1L);
        summary.addValues(MeasurementType.TEMPERATURE, entry);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertTrue(values.containsKey(MeasurementType.TEMPERATURE));
        assertEquals(1, values.get(MeasurementType.TEMPERATURE).size());
        assertEquals(entry, values.get(MeasurementType.TEMPERATURE).get(0));
    }

    @Test
    public void shouldAddEntryToExistingValuesMap() {
        SummaryEntry entry1 = createSummaryEntry(SummaryType.MIN, 25.0, 1L);
        SummaryEntry entry2 = createSummaryEntry(SummaryType.AVERAGE, 26.0, 2L);
        summary.addValues(MeasurementType.TEMPERATURE, entry1);
        summary.addValues(MeasurementType.TEMPERATURE, entry2);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertTrue(values.containsKey(MeasurementType.TEMPERATURE));
        assertEquals(2, values.get(MeasurementType.TEMPERATURE).size());
        assertTrue(values.get(MeasurementType.TEMPERATURE).contains(entry1));
        assertTrue(values.get(MeasurementType.TEMPERATURE).contains(entry2));
    }

    @Test
    public void shouldAddEntryToValuesMapForDifferentType() {
        SummaryEntry entry = createSummaryEntry(SummaryType.MIN, 25.0, 1L);
        summary.addValues(MeasurementType.TEMPERATURE, entry);

        SummaryEntry entryForDifferentType = createSummaryEntry(SummaryType.SUM, 30.0, 1L);
        summary.addValues(MeasurementType.VOLTAGE, entryForDifferentType);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertTrue(values.containsKey(MeasurementType.TEMPERATURE));
        assertTrue(values.containsKey(MeasurementType.VOLTAGE));
        assertEquals(1, values.get(MeasurementType.TEMPERATURE).size());
        assertEquals(1, values.get(MeasurementType.VOLTAGE).size());
        assertTrue(values.get(MeasurementType.TEMPERATURE).contains(entry));
        assertTrue(values.get(MeasurementType.VOLTAGE).contains(entryForDifferentType));
    }
    @Test
    public void shouldHandleNullEntry() {
        summary.addValues(MeasurementType.TEMPERATURE, null);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertFalse(values.containsKey(MeasurementType.TEMPERATURE));
    }
    @Test
    public void shouldHandleNullMeasurementType() {
        SummaryEntry entry = createSummaryEntry(null, 25.0, 1L);
        summary.addValues(null, entry);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertTrue(values.isEmpty());
    }
    @Test
    public void shouldHandleNullEntryAndMeasurementType() {
        summary.addValues(null, null);

        Map<MeasurementType, List<SummaryEntry>> values = summary.getValues();
        assertTrue(values.isEmpty());
    }
    private SummaryEntry createSummaryEntry(SummaryType type, Double value, Long counter) {
        SummaryEntry entry = new SummaryEntry();
        entry.setType(type);
        entry.setValue(value);
        entry.setCounter(counter);
        return entry;
    }
}
