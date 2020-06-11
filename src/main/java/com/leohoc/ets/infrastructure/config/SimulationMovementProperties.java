package com.leohoc.ets.infrastructure.config;

public class SimulationMovementProperties {

    private final int upBoundary;
    private final int rightBoundary;
    private final int downBoundary;
    private final int leftBoundary;
    private final double directionChangeProbability;
    private final double socialIsolationPercent;

    public SimulationMovementProperties(final int upBoundary,
                                        final int rightBoundary,
                                        final int downBoundary,
                                        final int leftBoundary,
                                        final double directionChangeProbability,
                                        final double socialIsolationPercent) {
        this.upBoundary = upBoundary;
        this.rightBoundary = rightBoundary;
        this.downBoundary = downBoundary;
        this.leftBoundary = leftBoundary;
        this.directionChangeProbability = directionChangeProbability;
        this.socialIsolationPercent = socialIsolationPercent;
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

    public double getSocialIsolationPercent() {
        return socialIsolationPercent;
    }
}
