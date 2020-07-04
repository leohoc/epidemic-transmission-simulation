package com.leohoc.ets.infrastructure.config;

public class ChartPanelProperties {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final CurrentInfoLocation currentInfoLocation;
    private final TotalInfoLocation totalInfoLocation;


    public ChartPanelProperties(final int x,
                                final int y,
                                final int width,
                                final int height,
                                final CurrentInfoLocation currentInfoLocation,
                                final TotalInfoLocation totalInfoLocation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.currentInfoLocation = currentInfoLocation;
        this.totalInfoLocation = totalInfoLocation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrentInfoX() {
        return currentInfoLocation.getX();
    }

    public int getCurrentNotExposedY() {
        return currentInfoLocation.getNotExposedY();
    }

    public int getCurrentInfectedY() {
        return currentInfoLocation.getInfectedY();
    }

    public int getCurrentHospitalizedY() {
        return currentInfoLocation.getHospitalizedY();
    }

    public int getCurrentSimulationDayY() {
        return currentInfoLocation.getSimulationDayY();
    }

    public int getTotalInfoX() {
        return totalInfoLocation.getInfoX();
    }

    public int getTotalInfectedY() {
        return totalInfoLocation.getInfectedY();
    }

    public int getTotalHospitalizedY() {
        return totalInfoLocation.getHospitalizedY();
    }

    public int getTotalRecoveredY() {
        return totalInfoLocation.getRecoveredY();
    }

    public int getTotalDeadY() {
        return totalInfoLocation.getDeadY();
    }
}
