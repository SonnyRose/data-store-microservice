package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;


import java.util.*;

@Getter
@Setter
@ToString
public class Summary {
    private long sensorId;
    private Map<MeasurementType, List<SummaryEntry>> values;
    public Summary(){
        this.values = new HashMap<>();
    }
    public void addValues(MeasurementType type, SummaryEntry value){
        Objects.requireNonNull(type, "Measurement type cannot be null");
        Objects.requireNonNull(value, "Summary entry cannot be null");
        if (values.containsKey(type)){
            List<SummaryEntry> entries = new ArrayList<>(values.get(type));
            entries.add(value);
            values.put(type, entries);
        }else {
            values.put(type, List.of(value));
        }
    }
}
