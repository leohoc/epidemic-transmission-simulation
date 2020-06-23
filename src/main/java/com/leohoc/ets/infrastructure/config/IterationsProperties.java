package com.leohoc.ets.infrastructure.config;

public class IterationsProperties {

    private final int totalIterations;
    private final int iterationsPerDay;

    public IterationsProperties(final int totalIterations, final int iterationsPerDay) {
        this.totalIterations = totalIterations;
        this.iterationsPerDay = iterationsPerDay;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public int getIterationsPerDay() {
        return iterationsPerDay;
    }
}
