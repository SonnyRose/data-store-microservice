package org.example.model.entry;

import org.example.model.types.SummaryType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SummaryEntryTest {
    @Test
    public void emptyConstructorTest(){
        SummaryEntry entry = new SummaryEntry();

            assertThat(entry.getType()).isNull();
            assertThat(entry.getCounter()).isNull();
            assertThat(entry.getValue()).isNull();
    }
    @Test
    public void testSetAndGetters(){
        SummaryType type = SummaryType.SUM;
        Double value = 10.5;
        Long counter = 2L;
        SummaryEntry entry = new SummaryEntry();
        entry.setType(type);
        entry.setValue(value);
        entry.setCounter(counter);

        assertThat(entry.getValue()).isEqualTo(value);
        assertThat(entry.getCounter()).isEqualTo(counter);
        assertThat(entry.getType()).isEqualTo(type);
    }
    @Test
    public void summaryTypeToStringTest(){
        SummaryEntry entry = new SummaryEntry();
        entry.setType(SummaryType.MIN);
        String string = entry.toString();
        assertThat(string).contains("type=MIN");
    }
    @Test
    public void nullValuesTest(){
        SummaryEntry entry = new SummaryEntry();
        entry.setType(null);
        entry.setValue(null);
        entry.setCounter(null);

        assertThat(entry.getValue()).isNull();
        assertThat(entry.getCounter()).isNull();
        assertThat(entry.getType()).isNull();
    }
    @Test
    public void equalityTest(){
        SummaryEntry entry1 = new SummaryEntry();
        entry1.setType(SummaryType.AVERAGE);
        entry1.setValue(10.0);
        entry1.setCounter(5L);

        SummaryEntry entry2 = new SummaryEntry();
        entry2.setType(SummaryType.AVERAGE);
        entry2.setValue(10.0);
        entry2.setCounter(5L);

        assertThat(entry1).isEqualTo(entry2);
    }
    @Test
    public void hashCodeTest(){
        SummaryEntry entry1 = new SummaryEntry();
        entry1.setType(SummaryType.SUM);
        entry1.setValue(20.0);
        entry1.setCounter(3L);

        SummaryEntry entry2 = new SummaryEntry();
        entry2.setType(SummaryType.SUM);
        entry2.setValue(20.0);
        entry2.setCounter(3L);

        assertThat(entry1.hashCode()).isEqualTo(entry2.hashCode());
    }
    @Test
    public void toStringTest(){
        SummaryEntry entry = new SummaryEntry();
        entry.setType(SummaryType.MAX);
        entry.setValue(15.5);
        entry.setCounter(8L);

        assertThat(entry.toString())
                .isEqualTo("SummaryEntry(type=MAX, value=15.5, counter=8)");
    }
}
