package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.model.entry.SummaryEntry;
import org.example.model.types.MeasurementType;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (type == null || value == null){
            return;
        }
        if (values.containsKey(type)){
            List<SummaryEntry> entries = new ArrayList<>(values.get(type));
            entries.add(value);
            values.put(type, entries);
        }else {
            values.put(type, List.of(value));
        }
    }
}
