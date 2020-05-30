package com.leohoc.ets.infrastructure.config;

public class SimulationGraphicsProperties {

    private final int mapWidth;
    private final int mapHeight;
    private final int areaChartWidth;
    private final int areaChartHeight;
    private final int infectedCountY;
    private final int recoveredCountY;
    private final int deadCountY;
    private final int epidemicRunningDaysY;

    public SimulationGraphicsProperties(final int mapWidth,
                                        final int mapHeight,
                                        final int areaChartWidth,
                                        final int areaChartHeight,
                                        final int infectedCountY,
                                        final int recoveredCountY,
                                        final int deadCountY,
                                        final int epidemicRunningDaysY) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.areaChartWidth = areaChartWidth;
        this.areaChartHeight = areaChartHeight;
        this.infectedCountY = infectedCountY;
        this.recoveredCountY = recoveredCountY;
        this.deadCountY = deadCountY;
        this.epidemicRunningDaysY = epidemicRunningDaysY;
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

    public int getInfectedCountY() {
        return infectedCountY;
    }

    public int getRecoveredCountY() {
        return recoveredCountY;
    }

    public int getDeadCountY() {
        return deadCountY;
    }

    public int getEpidemicRunningDaysY() {
        return epidemicRunningDaysY;
    }
}
