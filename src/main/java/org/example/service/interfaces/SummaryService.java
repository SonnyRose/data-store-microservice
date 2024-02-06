package org.example.service.interfaces;

import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface SummaryService {
    Summary get(long sensorId,
                Set<MeasurementType> measurementTypes,
                Set<SummaryType> summaryTypes);
    void handle(Data data);
}
