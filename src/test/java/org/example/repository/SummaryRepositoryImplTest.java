package org.example.repository;

import org.example.config.schema.RedisSchema;
import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.example.repository.implementations.SummaryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class SummaryRepositoryImplTest {
    @InjectMocks
    private SummaryRepositoryImpl summaryRepository;
    @Mock
    private JedisPool jedisPool;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(this);
        Jedis jedis = new Jedis();
        when(jedisPool.getResource()).thenReturn(jedis);
        jedis.flushAll();
    }
    @Test
    public void testFindBySensorId_sensorExists_returnsSummary() {
        long sensorId = 1L;
        SummaryType summaryType = SummaryType.MIN;
        MeasurementType measurementType = MeasurementType.TEMPERATURE;
        SummaryEntry entry = createSummaryEntry(summaryType, 25.0, 1L);
        saveSummaryEntryToRedis(sensorId, measurementType, entry);

        Jedis jedis = new Jedis();
        when(jedisPool.getResource()).thenReturn(jedis);

        Optional<Summary> optionalSummary = summaryRepository.findBySensorId(sensorId, Set.of(measurementType), Set.of(summaryType));

        assertTrue(optionalSummary.isPresent());
        Summary summary = optionalSummary.get();
        assertEquals(sensorId, summary.getSensorId());
        assertTrue(summary.getValues().containsKey(measurementType));
        SummaryEntry retrievedEntry = summary.getValues().get(measurementType).get(0);
        assertEquals(entry, retrievedEntry);
    }
    @Test
    public void testFindBySensorId_sensorDoesNotExist_returnsEmptyOptional() {
        long sensorId = 1L;

        Optional<Summary> optionalSummary = summaryRepository.findBySensorId(sensorId, Set.of(), Set.of());

        assertTrue(optionalSummary.isEmpty());
    }
    @Test
    public void testHandle_newSensor_addsSensorToKnownSensors() {
        Data data = new Data();
        data.setSensorId(1L);

        summaryRepository.handle(data);

        try (Jedis jedis = jedisPool.getResource()) {
            assertTrue(jedis.sismember(RedisSchema.sensorKeys(), String.valueOf(data.getSensorId())));
        }
    }
    private SummaryEntry createSummaryEntry(SummaryType type, Double value, Long counter) {
        SummaryEntry entry = new SummaryEntry();
        entry.setType(type);
        entry.setValue(value);
        entry.setCounter(counter);
        return entry;
    }
    private void saveSummaryEntryToRedis(long sensorId, MeasurementType measurementType, SummaryEntry entry) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(RedisSchema.summaryKey(sensorId, measurementType),
                    entry.getType().name().toLowerCase(), String.valueOf(entry.getValue()));
            jedis.hset(RedisSchema.summaryKey(sensorId, measurementType),
                    "counter", String.valueOf(entry.getCounter()));
        }
    }
}
