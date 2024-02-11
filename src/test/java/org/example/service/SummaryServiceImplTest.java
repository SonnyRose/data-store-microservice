package org.example.service;

import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.exception.SensorNotFoundException;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.example.repository.SummaryRepository;
import org.example.service.implementations.SummaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SummaryServiceImplTest {
    @InjectMocks
    private SummaryServiceImpl summaryService;
    @Mock
    private SummaryRepository summaryRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGet_ValidData_ReturnsSummary() {
        long sensorId = 1;
        Set<MeasurementType> measurementTypes = Set.of(MeasurementType.TEMPERATURE);
        Set<SummaryType> summaryTypes = Set.of(SummaryType.MIN);
        Summary expectedSummary = new Summary();
        when(summaryRepository.findBySensorId(sensorId, measurementTypes, summaryTypes))
                .thenReturn(Optional.of(expectedSummary));
        Summary actualSummary = summaryService.get(sensorId, measurementTypes, summaryTypes);
        assertEquals(expectedSummary, actualSummary);
    }

    @Test
    public void testGet_InvalidData_ThrowsSensorNotFoundException() {
        long sensorId = 1;
        Set<MeasurementType> measurementTypes = Set.of(MeasurementType.TEMPERATURE);
        Set<SummaryType> summaryTypes = Set.of(SummaryType.MIN);
        when(summaryRepository.findBySensorId(sensorId, measurementTypes, summaryTypes))
                .thenReturn(Optional.empty());
        assertThrows(SensorNotFoundException.class,
                () -> summaryService.get(sensorId, measurementTypes, summaryTypes));
    }
    @Test
    public void testHandle_ValidData_CallsRepositoryHandle() {
        Data data = new Data();
        summaryService.handle(data);
        verify(summaryRepository, times(1)).handle(eq(data));
    }
}
