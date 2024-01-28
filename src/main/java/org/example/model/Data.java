package org.example.model;

import lombok.*;
import org.example.model.enums.MeasurementType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Data {
    private Long id;
    private Long sensorId;
    private LocalDateTime timeStamp;
    private double measurement;
    private MeasurementType measurementType;
}
