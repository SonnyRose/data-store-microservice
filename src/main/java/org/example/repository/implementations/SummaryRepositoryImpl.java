package org.example.repository.implementations;

import lombok.RequiredArgsConstructor;
import org.example.config.schema.RedisSchema;
import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.entry.SummaryEntry;
import org.example.model.enums.MeasurementType;
import org.example.model.enums.SummaryType;
import org.example.repository.SummaryRepository;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class SummaryRepositoryImpl implements SummaryRepository  {
    private final JedisPool jedisPool;
    @Override
    public Optional<Summary> findBySensorId(long sensorId,
                                            Set<MeasurementType> measurementTypes,
                                            Set<SummaryType> summaryTypes) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (!sensorExists(sensorId, jedis)) {
                return Optional.empty();
            }
            Set<MeasurementType> effectiveMeasurementTypes = measurementTypes.isEmpty()
                    ? Set.of(MeasurementType.values())
                    : measurementTypes;

            Set<SummaryType> effectiveSummaryTypes = summaryTypes.isEmpty()
                    ? Set.of(SummaryType.values())
                    : summaryTypes;
            return getSummary(sensorId, effectiveMeasurementTypes, effectiveSummaryTypes, jedis);
        }
    }

    @Override
    public void handle(Data data) {
        try (Jedis jedis = jedisPool.getResource()){
            if (!jedis.sismember(RedisSchema.sensorKeys(),
                    String.valueOf(data.getSensorId()))){
                jedis.sadd(RedisSchema.sensorKeys(), String.valueOf(data.getSensorId()));
            }
            updateMinValue(data, jedis);
            updateMaxValue(data, jedis);
            updateSumAndAverageValue(data, jedis);
        }
        // метод, який зберігає та оновлює дані в Redis для обчислення статистик датчика
    }

    private Optional<Summary> getSummary(long sensorId,
                                         Set<MeasurementType> measurementTypes,
                                         Set<SummaryType> summaryTypes, Jedis jedis){
        Summary summary = new Summary();
        summary.setSensorId(sensorId);
        for (MeasurementType measurementType : measurementTypes){
            for (SummaryType summaryType : summaryTypes){
                SummaryEntry entry = new SummaryEntry();
                entry.setType(summaryType);
                String value = jedis.hget(RedisSchema
                                .summaryKey(sensorId, measurementType)
                        , summaryType.name().toLowerCase());
                if (value != null){
                    entry.setValue(Double.parseDouble(value));
                }
                String counter = jedis.hget(RedisSchema.summaryKey(sensorId, measurementType),
                        "counter");
                if (counter != null){
                    entry.setCounter(Long.parseLong(counter));
                }
                summary.addValues(measurementType, entry);
            }
        }
        return Optional.of(summary);
    }
    private boolean sensorExists(long sensorId, Jedis jedis) {
        return jedis.sismember(RedisSchema.sensorKeys(), String.valueOf(sensorId));
    }
    private void updateMinValue(Data data, Jedis jedis){
        String key = RedisSchema.summaryKey(data.getSensorId(), data.getMeasurementType());
        // створення ключа Redis для зберігання статистики для конкретного датчика та типу вимірювання
        String value = jedis.hget(key, SummaryType.MIN.name().toLowerCase());
        // отримання поточного мінімального значення з Redis за допомогою hget.
        // Мінімальне значення зберігається для кожного типу вимірювання окремо
        if (value == null || data.getMeasurement() < Double.parseDouble(value)){
            // Порівнюється поточне значення із новим значенням, яке прийшло з датчика data.getMeasurement()
            // Якщо поточне значення відсутнє або нове значення менше, тоді виконується Redis
            jedis.hset(key, SummaryType.MIN.name().toLowerCase(),
                    String.valueOf(data.getMeasurement()));
            // Оновлення значення за необхідності:
        }
    }
    private void updateMaxValue(Data data, Jedis jedis){
        String key = RedisSchema.summaryKey(data.getSensorId(), data.getMeasurementType());
        String value = jedis.hget(key, SummaryType.MAX.name().toLowerCase());
        if (value == null || data.getMeasurement() > Double.parseDouble(value)){
            jedis.hset(key, SummaryType.MAX.name().toLowerCase(),
                    String.valueOf(data.getMeasurement()));
        }
    }
    private void updateSumAndAverageValue(Data data, Jedis jedis){
        updateSumValue(data, jedis);
        String key = RedisSchema.summaryKey(data.getSensorId(), data.getMeasurementType());
        String counter = jedis.hget(key, "counter");
        if (counter == null){
            counter = String.valueOf(jedis.hset(key,
                    "counter",
                    String.valueOf(1)));
        }else {
            counter = String.valueOf(jedis.hincrBy(key, "counter", 1));
        }
        String sum = jedis.hget(key, SummaryType.SUM.name().toLowerCase());
        jedis.hset(key,
                SummaryType.AVERAGE.name().toLowerCase(),
                String.valueOf(Double.parseDouble(sum) / Double.parseDouble(counter)));
    }
    private void updateSumValue(Data data, Jedis jedis){
        String key = RedisSchema.summaryKey(data.getSensorId(), data.getMeasurementType());
        String value = jedis.hget(key, SummaryType.SUM.name().toLowerCase());
        if (value == null){
            jedis.hset(key,
                    SummaryType.SUM.name().toLowerCase(),
                    String.valueOf(data.getMeasurement()));
        }else{
            jedis.hincrByFloat(key,
                    SummaryType.SUM.name().toLowerCase(),
                    data.getMeasurement());
        }
    }
}
