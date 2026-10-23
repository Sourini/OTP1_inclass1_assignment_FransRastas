package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureConverterTest {

    private final TemperatureConverter converter =
            new TemperatureConverter();

    @Test
    void convertsTemperatures() {
        assertEquals(
                0,
                converter.fahrenheitToCelsius(32),
                0.001
        );

        assertEquals(
                212,
                converter.celsiusToFahrenheit(100),
                0.001
        );

        assertEquals(
                0,
                converter.kelvinToCelsius(273.15),
                0.001
        );

        assertEquals(
                273.15,
                converter.celsiusToKelvin(0),
                0.001
        );

        assertEquals(
                32,
                converter.convert(273.15, "K", "F"),
                0.001
        );

        assertEquals(
                10,
                converter.convert(10, "C", "C"),
                0.001
        );
    }

    @Test
    void detectsExtremeTemperatures() {
        assertTrue(
                converter.isExtremeTemperature(-41)
        );

        assertTrue(
                converter.isExtremeTemperature(51)
        );

        assertFalse(
                converter.isExtremeTemperature(-40)
        );

        assertFalse(
                converter.isExtremeTemperature(50)
        );
    }

    @Test
    void calculatesSpeedAndRejectsInvalidInput() {
        assertEquals(
                50,
                converter.speed(100, 2),
                0.001
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> converter.speed(10, 0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(1, "X", "C")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(1, "C", "X")
        );
    }
}