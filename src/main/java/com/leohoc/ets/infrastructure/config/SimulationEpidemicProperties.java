package com.leohoc.ets.infrastructure.config;

public class SimulationEpidemicProperties {

    private static int initialInfectedPercent;

    public static void loadProperties(final int initialInfectedPercent) {
        SimulationEpidemicProperties.initialInfectedPercent = initialInfectedPercent;
    }

    public static int getInitialInfectedPercent() {
        return initialInfectedPercent;
    }
}
