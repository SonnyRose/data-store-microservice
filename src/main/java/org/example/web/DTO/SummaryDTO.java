package org.example.web.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class SummaryDTO {
    private long sensorId;
    private Map<MeasurementType, List<SummaryEntry>> values;
}
