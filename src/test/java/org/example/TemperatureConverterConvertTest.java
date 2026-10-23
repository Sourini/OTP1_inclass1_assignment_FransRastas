package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TemperatureConverterConvertTest {
    private final TemperatureConverter c = new TemperatureConverter();

    @Test void celsiusToKelvin() { assertEquals(273.15, c.celsiusToKelvin(0), 1e-9); }
    @Test void convertCelsiusToFahrenheit() { assertEquals(212, c.convert(100, "C", "F"), 1e-9); }
    @Test void convertFahrenheitToCelsius() { assertEquals(0, c.convert(32, "F", "C"), 1e-9); }
    @Test void convertKelvinToFahrenheit() { assertEquals(32, c.convert(273.15, "K", "F"), 1e-9); }
    @Test void convertCelsiusToKelvin() { assertEquals(273.15, c.convert(0, "C", "K"), 1e-9); }
    @Test void convertSameUnit() { assertEquals(42, c.convert(42, "C", "C"), 1e-9); }
    @Test void convertUnknownUnitThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.convert(1, "X", "C"));
        assertThrows(IllegalArgumentException.class, () -> c.convert(1, "C", "X"));
    }
    @Test void convertBelowAbsoluteZeroThrows() { assertThrows(IllegalArgumentException.class, () -> c.convert(-1, "K", "C")); }
    @Test void speed() { assertEquals(50, c.speed(100, 2), 1e-9); }
    @Test void speedInvalid() {
        assertThrows(IllegalArgumentException.class, () -> c.speed(10, 0));
        assertThrows(IllegalArgumentException.class, () -> c.speed(-1, 1));
    }
}
