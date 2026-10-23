package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TempRecordTest {

    @Test
    void newRecordCalculatesSpeed() {
        TempRecord record = new TempRecord(1, 2, 100, 212, 120, 2);
        assertEquals(0, record.getId());
        assertEquals(60, record.getSpeedKmh(), 0.0001);
        assertNull(record.getFromSymbol());
        assertNull(record.getToSymbol());
    }

    @Test
    void newRecordWithZeroTimeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new TempRecord(1, 2, 0, 0, 10, 0));
    }

    @Test
    void recordFromDatabaseKeepsAllValues() {
        TempRecord record = new TempRecord(5, 1, 2, "C", "F", 100, 212, 150, 3, 50);
        assertEquals(5, record.getId());
        assertEquals(1, record.getFromUnitId());
        assertEquals(2, record.getToUnitId());
        assertEquals("C", record.getFromSymbol());
        assertEquals("F", record.getToSymbol());
        assertEquals(100, record.getInputValue(), 0.0001);
        assertEquals(212, record.getResultValue(), 0.0001);
        assertEquals(150, record.getDistanceKm(), 0.0001);
        assertEquals(3, record.getTimeHours(), 0.0001);
        assertEquals(50, record.getSpeedKmh(), 0.0001);
    }
}
