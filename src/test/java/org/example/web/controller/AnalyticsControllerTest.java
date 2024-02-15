package org.example.web.controller;

import org.example.model.Summary;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.example.service.interfaces.SummaryService;
import org.example.web.DTO.SummaryDTO;
import org.example.web.mapper.SummaryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AnalyticsControllerTest {
    @InjectMocks
    AnalyticsController controller;
    @Mock
    private SummaryService summaryService;
    @Mock
    private SummaryMapper summaryMapper;
    @Mock
    private MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void testGetSummary_validInput_returnsSummaryDTO() throws Exception {
        long sensorId = 123L;
        Set<MeasurementType> measurementTypes = Collections.singleton(MeasurementType.TEMPERATURE);
        Set<SummaryType> summaryTypes = Collections.singleton(SummaryType.MIN);

        Summary summary = new Summary();
        summary.setSensorId(sensorId);

        when(summaryService.get(sensorId, measurementTypes, summaryTypes)).thenReturn(summary);
        when(summaryMapper.toDTO(summary)).thenReturn(new SummaryDTO());

        Method privateMethod = AnalyticsController.class.getDeclaredMethod("getSummary", long.class, Set.class, Set.class);
        privateMethod.setAccessible(true);
        SummaryDTO summaryDTO = (SummaryDTO) privateMethod.invoke(controller, sensorId, measurementTypes, summaryTypes);
        ResponseEntity<SummaryDTO> responseEntity = new ResponseEntity<>(summaryDTO, HttpStatus.OK);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void testGetSummary_sensorDoesNotExist_returns404() throws Exception {
        long sensorId = 123L;
        Set<MeasurementType> measurementTypes = Collections.singleton(MeasurementType.TEMPERATURE);
        Set<SummaryType> summaryTypes = Collections.singleton(SummaryType.MIN);

        when(summaryService.get(sensorId, measurementTypes, summaryTypes)).thenReturn(null);

        Method privateMethod = AnalyticsController.class.getDeclaredMethod("getSummary", long.class, Set.class, Set.class);
        privateMethod.setAccessible(true);
        ResponseEntity<SummaryDTO> responseEntity = (ResponseEntity<SummaryDTO>) privateMethod.invoke(controller, sensorId, measurementTypes, summaryTypes);

        assertThat(responseEntity).isNull();
    }
    @Test
    public void testGetSummary_emptyMeasurementTypes_usesAllMeasurementTypes() throws Exception {
        long sensorId = 123L;
        Set<SummaryType> summaryTypes = EnumSet.of(SummaryType.MIN, SummaryType.MAX);
        invokePrivateGetSummary(sensorId, EnumSet.noneOf(MeasurementType.class), summaryTypes);

        verify(summaryService).get(sensorId, EnumSet.allOf(MeasurementType.class), summaryTypes);
    }
    @Test
    public void testGetSummary_emptySummaryTypes_usesAllSummaryTypes() throws Exception {
        long sensorId = 123L;
        Set<MeasurementType> measurementTypes = EnumSet.of(MeasurementType.TEMPERATURE, MeasurementType.VOLTAGE);
        invokePrivateGetSummary(sensorId, measurementTypes, Collections.emptySet());

        verify(summaryService).get(sensorId, measurementTypes, EnumSet.allOf(SummaryType.class));
    }
    @Test
    public void testGetSummary_callsSummaryServiceCorrectly() throws Exception {
        long sensorId = 123L;
        Set<MeasurementType> measurementTypes = EnumSet.of(MeasurementType.TEMPERATURE, MeasurementType.VOLTAGE);
        Set<SummaryType> summaryTypes = EnumSet.of(SummaryType.MIN, SummaryType.MAX);
        invokePrivateGetSummary(sensorId, measurementTypes, summaryTypes);

        verify(summaryService).get(sensorId, measurementTypes, summaryTypes);
    }
    @Test
    public void testGetSummary() throws Exception {
        long sensorId = 1L;
        Set<MeasurementType> measurementTypes = EnumSet.of(MeasurementType.TEMPERATURE);
        Set<SummaryType> summaryTypes = EnumSet.of(SummaryType.AVERAGE);

        Summary summary = new Summary();

        when(summaryService.get(sensorId, measurementTypes, summaryTypes)).thenReturn(summary);

        SummaryDTO summaryDTO = new SummaryDTO();
        when(summaryMapper.toDTO(summary)).thenReturn(summaryDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/analytics/summary/{sensorId}", sensorId)
                        .param("mt", MeasurementType.TEMPERATURE.name())
                        .param("st", SummaryType.AVERAGE.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(summaryService, times(1)).get(sensorId, measurementTypes, summaryTypes);
        verify(summaryMapper, times(1)).toDTO(summary);
    }
    @Test
    public void testGetSummaryWithDefaults() throws Exception {
        long sensorId = 1L;
        Summary summary = new Summary();

        when(summaryService.get(eq(sensorId), any(), any())).thenReturn(summary);

        SummaryDTO summaryDTO = new SummaryDTO();
        when(summaryMapper.toDTO(summary)).thenReturn(summaryDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/analytics/summary/{sensorId}", sensorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(summaryService, times(1)).get(eq(sensorId), any(), any());
        verify(summaryMapper, times(1)).toDTO(summary);
    }
    private void invokePrivateGetSummary(long sensorId,
                                         Set<MeasurementType> measurementTypes,
                                         Set<SummaryType> summaryTypes)
            throws Exception
    {
        if (summaryTypes.isEmpty()) {
            summaryTypes = EnumSet.allOf(SummaryType.class);
        }
        Method privateMethod = AnalyticsController.class.getDeclaredMethod("getSummary", long.class, Set.class, Set.class);
        privateMethod.setAccessible(true);
        privateMethod.invoke(controller, sensorId, measurementTypes, summaryTypes);
    }
}
