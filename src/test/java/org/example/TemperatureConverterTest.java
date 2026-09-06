package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {

    private final TemperatureConverter TestConverter =
            new TemperatureConverter();

    @Test
    void fahrenheitToCelsiusConvertsMultipleTemperatures() {
        assertEquals(0.0, TestConverter.fahrenheitToCelsius(32), 0.1);
        assertEquals(100.0, TestConverter.fahrenheitToCelsius(212), 0.1);
        assertEquals(-40.0, TestConverter.fahrenheitToCelsius(-40), 0.1);
    }

    @Test
    void celsiusToFahrenheitConvertsMultipleTemperatures() {
        assertEquals(32.0, TestConverter.celsiusToFahrenheit(0), 0.1);
        assertEquals(212.0, TestConverter.celsiusToFahrenheit(100), 0.1);
        assertEquals(-40.0, TestConverter.celsiusToFahrenheit(-40), 0.1);
    }

    @Test
    void isExtremeTemperatureChecksEdgeCases() {
        assertTrue(TestConverter.isExtremeTemperature(-69.420));
        assertTrue(TestConverter.isExtremeTemperature(67.67));
        assertFalse(TestConverter.isExtremeTemperature(20.0));
        assertFalse(TestConverter.isExtremeTemperature(-40.0));
        assertFalse(TestConverter.isExtremeTemperature(50.0));
    }
}