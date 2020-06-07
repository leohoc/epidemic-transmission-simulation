package com.leohoc.ets.infrastructure.config;

public class SimulationProperties {

    private final int populationSize;
    private final double initialInfectedPercent;
    private final boolean graphicsEnabled;

    public SimulationProperties(final int populationSize, final double initialInfectedPercent, final boolean graphicsEnabled) {
        this.populationSize = populationSize;
        this.initialInfectedPercent = initialInfectedPercent;
        this.graphicsEnabled = graphicsEnabled;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getInitialInfectedPercent() {
        return initialInfectedPercent;
    }

    public boolean isGraphicsEnabled() {
        return graphicsEnabled;
    }
}
