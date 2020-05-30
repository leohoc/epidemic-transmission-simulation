package com.leohoc.ets.application;

import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;

public class IterationEvolution {

    private final SimulationIterationsProperties properties;
    private int currentIteration;

    public IterationEvolution(final SimulationIterationsProperties properties) {
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
        return currentIteration > properties.getTotalIterations();
    }
}