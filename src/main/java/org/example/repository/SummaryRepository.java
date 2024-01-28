package org.example.repository;

import org.example.model.Data;
import org.example.model.Summary;
import org.example.model.MeasurementType;
import org.example.model.SummaryType;
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
