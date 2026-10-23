package org.example;

public class TemperatureConverter {

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }

    public double convert(
            double value,
            String from,
            String to
    ) {
        if (from.equals(to)) {
            return value;
        }

        double celsius = switch (from) {
            case "C" -> value;
            case "F" -> fahrenheitToCelsius(value);
            case "K" -> kelvinToCelsius(value);
            default -> throw new IllegalArgumentException(
                    "Unknown temperature unit: " + from
            );
        };

        return switch (to) {
            case "C" -> celsius;
            case "F" -> celsiusToFahrenheit(celsius);
            case "K" -> celsiusToKelvin(celsius);
            default -> throw new IllegalArgumentException(
                    "Unknown temperature unit: " + to
            );
        };
    }

    public double speed(
            double distanceKm,
            double timeHours
    ) {
        if (distanceKm < 0 || timeHours <= 0) {
            throw new IllegalArgumentException(
                    "Distance cannot be negative and "
                            + "time must be greater than zero"
            );
        }

        return distanceKm / timeHours;
    }
}