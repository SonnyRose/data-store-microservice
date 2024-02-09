package org.example.service;

import com.google.gson.JsonObject;
import org.example.model.Data;
import org.example.model.types.MeasurementType;
import org.example.service.implementations.DebeziumEventConsumerImpl;
import org.example.service.interfaces.SummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DebeziumEventConsumerImplTest {
    @InjectMocks
    private DebeziumEventConsumerImpl debeziumEventConsumer;
    @Mock
    private SummaryService summaryService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testParseJson_validJson_parsesCorrectly() {
        String json = "{\"payload\":{\"id\":1,\"sensor_id\":123,\"measurement\":25.5,\"timeStamp\":1643331035000,\"type\":\"TEMPERATURE\"}}";

        JsonObject jsonObject = debeziumEventConsumer.parseJson(json);

        assertTrue(jsonObject.has("payload"));
        assertEquals(1, jsonObject.getAsJsonObject("payload").get("id").getAsLong());
        assertEquals(123, jsonObject.getAsJsonObject("payload").get("sensor_id").getAsLong());
        assertEquals(25.5, jsonObject.getAsJsonObject("payload").get("measurement").getAsDouble());
        assertEquals(1643331035000L, jsonObject.getAsJsonObject("payload").get("timeStamp").getAsLong());
        assertEquals("TEMPERATURE", jsonObject.getAsJsonObject("payload").get("type").getAsString());
    }
    @Test
    public void testParseJson_invalidJson_throwsException() {
        String invalidJson = "{\"payload\":}";
        assertThrows(Exception.class, () -> debeziumEventConsumer.parseJson(invalidJson));
    }
    @Test
    public void testCreateDataFromPayload_validPayload_createsCorrectData() {
        String payload = "{\"id\":1,\"sensor_id\":123,\"measurement\":25.5,\"timeStamp\":1643331035000,\"type\":\"TEMPERATURE\"}";
        JsonObject jsonObject = debeziumEventConsumer.parseJson(payload);

        Data data = debeziumEventConsumer.createDataFromPayload(jsonObject);

        assertNotNull(data);
        assertEquals(1, data.getId());
        assertEquals(123, data.getSensorId());
        assertEquals(25.5, data.getMeasurement());
        assertEquals(MeasurementType.TEMPERATURE, data.getMeasurementType());
    }
    @Test
    public void testHandle_validMessage_callsSummaryServiceHandle() {
        String message = "{\"payload\":{\"id\":1,\"sensor_id\":123,\"measurement\":25.5,\"timeStamp\":1643331035000,\"type\":\"TEMPERATURE\"}}";

        debeziumEventConsumer.handle(message);

        verify(summaryService, times(1)).handle((Data) any(Data.class));
    }
    @Test
    public void testHandle_exception_logsAndContinues() {
        String invalidMessage = "{\"payload\":}";

        debeziumEventConsumer.handle(invalidMessage);
    }
}
