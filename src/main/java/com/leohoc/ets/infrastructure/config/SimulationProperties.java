package com.leohoc.ets.infrastructure.config;

public class SimulationProperties {

    private final int populationSize;
    private final int initialInfectedPercent;

    public SimulationProperties(int populationSize, int initialInfectedPercent) {
        this.populationSize = populationSize;
        this.initialInfectedPercent = initialInfectedPercent;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getInitialInfectedPercent() {
        return initialInfectedPercent;
    }
}