package org.example.repository.interfaces;

import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.types.MeasurementType;
import org.example.model.types.SummaryType;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface SummaryRepository  {
    Optional<Summary> findBySensorId(
            long sensorId,
            Set<MeasurementType> measurementTypes,
            Set<SummaryType> summaryTypes
    );
    void handle(Data data);
}
