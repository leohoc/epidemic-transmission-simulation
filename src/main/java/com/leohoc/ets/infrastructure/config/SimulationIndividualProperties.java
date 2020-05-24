package com.leohoc.ets.infrastructure.config;

public class SimulationIndividualProperties {

    private static Integer individualWidth;
    private static Integer individualHeight;
    private static Integer directionChangeProbability;
    private static Integer upBoundary;
    private static Integer rightBoundary;
    private static Integer downBoundary;
    private static Integer leftBoundary;

    static void loadProperties(final Integer individualWidth,
                               final Integer individualHeight,
                               final Integer directionChangeProbability,
                               final Integer upBoundary,
                               final Integer rightBoundary,
                               final Integer downBoundary,
                               final Integer leftBoundary) {
        SimulationIndividualProperties.individualWidth = individualWidth;
        SimulationIndividualProperties.individualHeight = individualHeight;
        SimulationIndividualProperties.directionChangeProbability = directionChangeProbability;
        SimulationIndividualProperties.upBoundary = upBoundary;
        SimulationIndividualProperties.rightBoundary = rightBoundary;
        SimulationIndividualProperties.downBoundary = downBoundary;
        SimulationIndividualProperties.leftBoundary = leftBoundary;
    }

    public static Integer getIndividualWidth() {
        return individualWidth;
    }

    public static Integer getIndividualHeight() {
        return individualHeight;
    }

    public static Integer getDirectionChangeProbability() {
        return directionChangeProbability;
    }

    public static Integer getUpBoundary() {
        return upBoundary;
    }

    public static Integer getRightBoundary() {
        return rightBoundary;
    }

    public static Integer getDownBoundary() {
        return downBoundary;
    }

    public static Integer getLeftBoundary() {
        return leftBoundary;
    }
}
