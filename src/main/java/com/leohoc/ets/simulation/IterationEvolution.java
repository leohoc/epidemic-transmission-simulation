package com.leohoc.ets.simulation;

import com.leohoc.ets.infrastructure.config.IterationsProperties;

public class IterationEvolution {

    private final IterationsProperties properties;
    private int currentIteration;

    public IterationEvolution(final IterationsProperties properties) {
        this.properties = properties;
        this.currentIteration = 0;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public int getTotalIterations() {
        return properties.getTotalIterations();
    }

    public void iterate() {
        this.currentIteration++;
    }

    public int getCurrentSimulatedDay() {
        return currentIteration / properties.getIterationsPerDay();
    }

    public boolean hasSimulationFinished() {
        return currentIteration >= properties.getTotalIterations();
    }

    protected void setCurrentIteration(final int currentIteration) {
        this.currentIteration = currentIteration;
    }
}