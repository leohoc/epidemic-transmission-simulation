package com.leohoc.ets.infrastructure.config;

public class SimulationIndividualProperties {

    private final int individualWidth;
    private final int individualHeight;
    private final int upBoundary;
    private final int rightBoundary;
    private final int downBoundary;
    private final int leftBoundary;
    private final double directionChangeProbability;

    public SimulationIndividualProperties(final int individualWidth,
                                          final int individualHeight,
                                          final int upBoundary,
                                          final int rightBoundary,
                                          final int downBoundary,
                                          final int leftBoundary,
                                          final double directionChangeProbability) {
        this.individualWidth = individualWidth;
        this.individualHeight = individualHeight;
        this.upBoundary = upBoundary;
        this.rightBoundary = rightBoundary;
        this.downBoundary = downBoundary;
        this.leftBoundary = leftBoundary;
        this.directionChangeProbability = directionChangeProbability;
    }

    public int getIndividualWidth() {
        return individualWidth;
    }

    public int getIndividualHeight() {
        return individualHeight;
    }

    public int getUpBoundary() {
        return upBoundary;
    }

    public int getRightBoundary() {
        return rightBoundary;
    }

    public int getDownBoundary() {
        return downBoundary;
    }

    public int getLeftBoundary() {
        return leftBoundary;
    }

    public double getDirectionChangeProbability() {
        return directionChangeProbability;
    }
}
