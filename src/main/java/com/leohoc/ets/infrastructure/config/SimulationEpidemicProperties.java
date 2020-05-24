package com.leohoc.ets.infrastructure.config;

import java.math.BigDecimal;

public class SimulationEpidemicProperties {

    private static int initialInfectedPercent;
    private static int totalTimeInMs;

    static void loadProperties(final int initialInfectedPercent, final int totalTimeInMs) {
        SimulationEpidemicProperties.initialInfectedPercent = initialInfectedPercent;
        SimulationEpidemicProperties.totalTimeInMs = totalTimeInMs;
    }
    public static int getTotalTimeInMs() {
        return totalTimeInMs;
    }

    public static BigDecimal getInitialInfectedPercent() {
        return BigDecimal.valueOf(initialInfectedPercent);
    }
}
