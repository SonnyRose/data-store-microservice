package org.example.model.entry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.types.SummaryType;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SummaryEntry {
    private SummaryType type;
    private Double value;
    private Long counter;
}
