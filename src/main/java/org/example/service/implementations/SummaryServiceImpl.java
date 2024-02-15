package org.example.service.implementations;

import lombok.RequiredArgsConstructor;
import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.example.model.exception.SensorNotFoundException;
import org.example.repository.SummaryRepository;
import org.example.service.interfaces.SummaryService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {
    private final SummaryRepository summaryRepository;
    @Override
    public Summary get(long sensorId,
                       Set<MeasurementType> measurementTypes,
                       Set<SummaryType> summaryTypes
    ) {
        return summaryRepository
                .findBySensorId(sensorId,
                        measurementTypes == null ? Set.of(MeasurementType.values()) : measurementTypes,
                        summaryTypes == null ? Set.of(SummaryType.values()) : summaryTypes)
                .orElseThrow(SensorNotFoundException::new);
    }
    @Override
    public void handle(Data data) {
        summaryRepository.handle(data);
    }
}
