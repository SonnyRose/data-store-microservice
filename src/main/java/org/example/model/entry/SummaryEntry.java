package org.example.model.entry;

import lombok.*;
import org.example.model.types.SummaryType;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SummaryEntry {
    private SummaryType type;
    private Double value;
    private Long counter;
}
