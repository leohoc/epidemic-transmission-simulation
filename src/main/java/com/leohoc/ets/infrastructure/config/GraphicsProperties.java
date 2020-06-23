package com.leohoc.ets.infrastructure.config;

public class GraphicsProperties {

    private final int mapWidth;
    private final int mapHeight;
    private final int areaChartWidth;
    private final int areaChartHeight;
    private final CurrentInfoLocation currentInfoLocation;
    private final TotalInfoLocation totalInfoLocation;


    public GraphicsProperties(final int mapWidth,
                              final int mapHeight,
                              final int areaChartWidth,
                              final int areaChartHeight,
                              final CurrentInfoLocation currentInfoLocation,
                              final TotalInfoLocation totalInfoLocation) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.areaChartWidth = areaChartWidth;
        this.areaChartHeight = areaChartHeight;
        this.currentInfoLocation = currentInfoLocation;
        this.totalInfoLocation = totalInfoLocation;
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
