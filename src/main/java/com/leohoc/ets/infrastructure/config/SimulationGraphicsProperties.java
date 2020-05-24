package com.leohoc.ets.infrastructure.config;

public class SimulationGraphicsProperties {

    private static int areaChartWidth;
    private static int areaChartHeight;
    private static int areaChartElementWidth;
    private static int infectedCountY;
    private static int recoveredCountY;
    private static int deadCountY;

    static void loadProperties(final int areaChartWidth,
                               final int areaChartHeight,
                               final int areaChartElementWidth,
                               final int infectedCountY,
                               final int recoveredCountY,
                               final int deadCountY) {
        SimulationGraphicsProperties.areaChartWidth = areaChartWidth;
        SimulationGraphicsProperties.areaChartHeight = areaChartHeight;
        SimulationGraphicsProperties.areaChartElementWidth = areaChartElementWidth;
        SimulationGraphicsProperties.infectedCountY = infectedCountY;
        SimulationGraphicsProperties.recoveredCountY = recoveredCountY;
        SimulationGraphicsProperties.deadCountY = deadCountY;
    }

    public static int getAreaChartWidth() {
        return areaChartWidth;
    }

    public static int getAreaChartHeight() {
        return areaChartHeight;
    }

    public static int getAreaChartElementWidth() {
        return areaChartElementWidth;
    }

    public static int getInfectedCountY() {
        return infectedCountY;
    }

    public static int getRecoveredCountY() {
        return recoveredCountY;
    }

    public static int getDeadCountY() {
        return deadCountY;
    }
}
