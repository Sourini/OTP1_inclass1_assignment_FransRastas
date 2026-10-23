package org.example;

public record TempRecord(
        int id,
        int fromUnitId,
        int toUnitId,
        String fromSymbol,
        String toSymbol,
        double inputValue,
        double resultValue,
        double distanceKm,
        double timeHours,
        double speedKmh
) {

    public TempRecord(
            int fromUnitId,
            int toUnitId,
            String fromSymbol,
            String toSymbol,
            double inputValue,
            double resultValue,
            double distanceKm,
            double timeHours
    ) {
        this(
                0,
                fromUnitId,
                toUnitId,
                fromSymbol,
                toSymbol,
                inputValue,
                resultValue,
                distanceKm,
                timeHours,
                calculateSpeed(distanceKm, timeHours)
        );
    }

    private static double calculateSpeed(
            double distanceKm,
            double timeHours
    ) {
        if (distanceKm < 0 || timeHours <= 0) {
            throw new IllegalArgumentException(
                    "Distance cannot be negative and time must be greater than zero");
        }

        return distanceKm / timeHours;
    }
}