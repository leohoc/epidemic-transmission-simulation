package com.leohoc.ets.infrastructure.config;

public class SimulationGraphicsProperties {

    private static int areaChartWidth;
    private static int areaChartHeight;
    private static int areaChartElementWidth;

    static void loadProperties(final int areaChartWidth, final int areaChartHeight, final int areaChartElementWidth) {
        SimulationGraphicsProperties.areaChartWidth = areaChartWidth;
        SimulationGraphicsProperties.areaChartHeight = areaChartHeight;
        SimulationGraphicsProperties.areaChartElementWidth = areaChartElementWidth;
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
}
