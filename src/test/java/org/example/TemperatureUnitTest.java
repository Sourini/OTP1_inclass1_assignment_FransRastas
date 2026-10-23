package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TemperatureUnitTest {

    @Test
    void gettersWork() {
        TemperatureUnit unit = new TemperatureUnit(1, "Celsius", "C");
        assertEquals(1, unit.getId());
        assertEquals("Celsius", unit.getName());
        assertEquals("C", unit.getSymbol());
    }

    @Test
    void toStringShowsNameAndSymbol() {
        TemperatureUnit unit = new TemperatureUnit(1, "Celsius", "C");
        assertEquals("Celsius (C)", unit.toString());
    }
}
