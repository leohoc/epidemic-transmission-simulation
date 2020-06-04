package com.leohoc.ets.infrastructure.config;

public class SimulationProperties {

    private final int populationSize;
    private final double initialInfectedPercent;

    public SimulationProperties(final int populationSize, final double initialInfectedPercent) {
        this.populationSize = populationSize;
        this.initialInfectedPercent = initialInfectedPercent;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getInitialInfectedPercent() {
        return initialInfectedPercent;
    }
}
