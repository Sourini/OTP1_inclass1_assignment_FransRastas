package org.example;

public class TempRecord {

    private int id;
    private int fromUnitId;
    private int toUnitId;
    private String fromSymbol;
    private String toSymbol;
    private double inputValue;
    private double resultValue;
    private double distanceKm;
    private double timeHours;
    private double speedKmh;

    public TempRecord(int id, int fromUnitId, int toUnitId, String fromSymbol, String toSymbol,
                      double inputValue, double resultValue, double distanceKm,
                      double timeHours, double speedKmh) {
        this.id = id;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.fromSymbol = fromSymbol;
        this.toSymbol = toSymbol;
        this.inputValue = inputValue;
        this.resultValue = resultValue;
        this.distanceKm = distanceKm;
        this.timeHours = timeHours;
        this.speedKmh = speedKmh;
    }

    public TempRecord(int fromUnitId, int toUnitId, double inputValue, double resultValue,
                      double distanceKm, double timeHours) {
        TemperatureConverter converter = new TemperatureConverter();
        this.id = 0;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.fromSymbol = null;
        this.toSymbol = null;
        this.inputValue = inputValue;
        this.resultValue = resultValue;
        this.distanceKm = distanceKm;
        this.timeHours = timeHours;
        this.speedKmh = converter.speed(distanceKm, timeHours);
    }

    public int getId() {
        return id;
    }

    public int getFromUnitId() {
        return fromUnitId;
    }

    public int getToUnitId() {
        return toUnitId;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public double getInputValue() {
        return inputValue;
    }

    public double getResultValue() {
        return resultValue;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getTimeHours() {
        return timeHours;
    }

    public double getSpeedKmh() {
        return speedKmh;
    }
}
