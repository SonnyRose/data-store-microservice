package org.example.service.implementations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Data;
import org.example.model.types.MeasurementType;
import org.example.service.interfaces.CDCEventConsumer;
import org.example.service.interfaces.SummaryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.example.constants.MessageConstants.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class DebeziumEventConsumerImpl implements CDCEventConsumer {
    private final SummaryService summaryService;
    @Override
    @KafkaListener(topics = "data")
    public void handle(String message) {
        try {
            JsonObject payload = parseJson(message);
            Data data = createDataFromPayload(payload);
            summaryService.handle(data);
        }catch (Exception exception) {
            log.error("Exception occurred during data handling", exception);
        }
    }
    public Data createDataFromPayload(JsonObject payload){
        Data data = new Data();
        data.setId(payload.get(ID_KEY).getAsLong());
        data.setSensorId(payload.get(SENSOR_ID_KEY).getAsLong());
        data.setMeasurement(payload.get(MEASUREMENT_KEY).getAsDouble());
        data.setTimeStamp(convertEpochMillisToDateTime(payload.get(TIMESTAMP_KEY).getAsLong()));
        data.setMeasurementType(MeasurementType.valueOf(payload.get(MEASUREMENT_TYPE_KEY).getAsString()));
        return data;
    }
    public JsonObject parseJson(String json){
        return JsonParser.parseString(json).getAsJsonObject().get("payload").getAsJsonObject();
    }
    private LocalDateTime convertEpochMillisToDateTime(long epochMillis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis /1000),
                TimeZone.getDefault().toZoneId());
    }
}
