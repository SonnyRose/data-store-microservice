package org.example.web.DTO;

import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SummaryDTOTest {
    @Test
    public void testEmptySummaryDTO() {
        SummaryDTO summaryDTO = new SummaryDTO();

        summaryDTO.setValues(new HashMap<>());

        assertEquals(0, summaryDTO.getSensorId());
        assertTrue(summaryDTO.getValues().isEmpty());
    }
    @Test
    public void testAddSummaryEntry() {
        SummaryEntry entry = new SummaryEntry();
        entry.setType(SummaryType.MIN);
        entry.setValue(25.0);
        entry.setCounter(10L);

        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setSensorId(12345L);

        Map<MeasurementType, List<SummaryEntry>> values = new HashMap<>();
        summaryDTO.setValues(values);

        List<SummaryEntry> tempList = new ArrayList<>();
        tempList.add(entry);
        values.put(MeasurementType.TEMPERATURE, tempList);

        assertEquals(1, summaryDTO.getValues().size());
        assertTrue(summaryDTO.getValues().containsKey(MeasurementType.TEMPERATURE));
        assertEquals(1, summaryDTO.getValues().get(MeasurementType.TEMPERATURE).size());
        assertEquals(entry, summaryDTO.getValues().get(MeasurementType.TEMPERATURE).get(0));
    }
    @Test
    public void testToString() {
        long sensorId = 12345L;
        SummaryEntry entry = new SummaryEntry();
        entry.setType(SummaryType.MIN);
        entry.setValue(25.0);
        entry.setCounter(10L);

        List<SummaryEntry> entries = new ArrayList<>();
        entries.add(entry);

        Map<MeasurementType, List<SummaryEntry>> values = new HashMap<>();
        values.put(MeasurementType.TEMPERATURE, entries);

        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setSensorId(sensorId);
        summaryDTO.setValues(values);

        String expectedString =
                "SummaryDTO(sensorId=12345, values={TEMPERATURE=[SummaryEntry(type=MIN, value=25.0, counter=10)]})";

        assertEquals(expectedString, summaryDTO.toString());
    }
}
