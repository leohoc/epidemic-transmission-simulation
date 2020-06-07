package com.leohoc.ets.infrastructure.config;

public class SimulationGraphicsProperties {

    private final int mapWidth;
    private final int mapHeight;
    private final int areaChartWidth;
    private final int areaChartHeight;
    private final int currentInfoX;
    private final int currentNotExposedY;
    private final int currentInfectedY;
    private final int currentHospitalizedY;
    private final int currentSimulationDayY;
    private final int totalInfoX;
    private final int totalInfectedY;
    private final int totalHospitalizedY;
    private final int totalRecoveredY;
    private final int totalDeadY;

    public SimulationGraphicsProperties(final int mapWidth,
                                        final int mapHeight,
                                        final int areaChartWidth,
                                        final int areaChartHeight,
                                        final int currentInfoX,
                                        final int currentNotExposedY,
                                        final int currentInfectedY,
                                        final int currentHospitalizedY,
                                        final int currentSimulationDayY,
                                        final int totalInfoX,
                                        final int totalInfectedY,
                                        final int totalHospitalizedY,
                                        final int totalRecoveredY,
                                        final int totalDeadY) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.areaChartWidth = areaChartWidth;
        this.areaChartHeight = areaChartHeight;
        this.currentInfoX = currentInfoX;
        this.currentNotExposedY = currentNotExposedY;
        this.currentInfectedY = currentInfectedY;
        this.currentHospitalizedY = currentHospitalizedY;
        this.currentSimulationDayY = currentSimulationDayY;
        this.totalInfoX = totalInfoX;
        this.totalInfectedY = totalInfectedY;
        this.totalHospitalizedY = totalHospitalizedY;
        this.totalRecoveredY = totalRecoveredY;
        this.totalDeadY = totalDeadY;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getAreaChartWidth() {
        return areaChartWidth;
    }

    public int getAreaChartHeight() {
        return areaChartHeight;
    }

    public int getCurrentInfoX() {
        return currentInfoX;
    }

    public int getCurrentNotExposedY() {
        return currentNotExposedY;
    }

    public int getCurrentInfectedY() {
        return currentInfectedY;
    }

    public int getCurrentHospitalizedY() {
        return currentHospitalizedY;
    }

    public int getCurrentSimulationDayY() {
        return currentSimulationDayY;
    }

    public int getTotalInfoX() {
        return totalInfoX;
    }

    public int getTotalInfectedY() {
        return totalInfectedY;
    }

    public int getTotalHospitalizedY() {
        return totalHospitalizedY;
    }

    public int getTotalRecoveredY() {
        return totalRecoveredY;
    }

    public int getTotalDeadY() {
        return totalDeadY;
    }
}
