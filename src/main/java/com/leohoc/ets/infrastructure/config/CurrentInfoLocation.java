package com.leohoc.ets.infrastructure.config;

public class CurrentInfoLocation {

    private final int x;
    private final int notExposedY;
    private final int infectedY;
    private final int hospitalizedY;
    private final int simulationDayY;

    public CurrentInfoLocation(final int x,
                               final int notExposedY,
                               final int infectedY,
                               final int hospitalizedY,
                               final int simulationDayY) {
        this.x = x;
        this.notExposedY = notExposedY;
        this.infectedY = infectedY;
        this.hospitalizedY = hospitalizedY;
        this.simulationDayY = simulationDayY;
    }

    public int getX() {
        return x;
    }

    public int getNotExposedY() {
        return notExposedY;
    }

    public int getInfectedY() {
        return infectedY;
    }

    public int getHospitalizedY() {
        return hospitalizedY;
    }

    public int getSimulationDayY() {
        return simulationDayY;
    }
}
