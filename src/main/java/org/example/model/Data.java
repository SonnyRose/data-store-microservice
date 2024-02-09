package org.example.model;

import lombok.*;
import org.example.model.types.MeasurementType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Data {
    private Long id;
    private Long sensorId;
    private LocalDateTime timeStamp;
    private double measurement;
    private MeasurementType measurementType;
}
