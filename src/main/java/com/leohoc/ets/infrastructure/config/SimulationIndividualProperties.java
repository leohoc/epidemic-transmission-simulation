package com.leohoc.ets.infrastructure.config;

public class SimulationIndividualProperties {

    private final int individualWidth;
    private final int individualHeight;
    private final int directionChangeProbability;
    private final int upBoundary;
    private final int rightBoundary;
    private final int downBoundary;
    private final int leftBoundary;

    public SimulationIndividualProperties(final Integer individualWidth,
                                          final Integer individualHeight,
                                          final Integer directionChangeProbability,
                                          final Integer upBoundary,
                                          final Integer rightBoundary,
                                          final Integer downBoundary,
                                          final Integer leftBoundary) {
        this.individualWidth = individualWidth;
        this.individualHeight = individualHeight;
        this.directionChangeProbability = directionChangeProbability;
        this.upBoundary = upBoundary;
        this.rightBoundary = rightBoundary;
        this.downBoundary = downBoundary;
        this.leftBoundary = leftBoundary;
    }

    public Integer getIndividualWidth() {
        return individualWidth;
    }

    public Integer getIndividualHeight() {
        return individualHeight;
    }

    public Integer getDirectionChangeProbability() {
        return directionChangeProbability;
    }

    public Integer getUpBoundary() {
        return upBoundary;
    }

    public Integer getRightBoundary() {
        return rightBoundary;
    }

    public Integer getDownBoundary() {
        return downBoundary;
    }

    public Integer getLeftBoundary() {
        return leftBoundary;
    }
}
