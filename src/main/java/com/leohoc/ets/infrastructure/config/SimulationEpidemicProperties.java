package com.leohoc.ets.infrastructure.config;

import java.math.BigDecimal;

public class SimulationEpidemicProperties {

    private static int initialInfectedPercent;
    private static long totalTimeInMs;
    private static long simulatedDayDurationInMs;

    static void loadProperties(final int initialInfectedPercent, final long totalTimeInMs, final long simulatedDayDurationInMs) {
        SimulationEpidemicProperties.initialInfectedPercent = initialInfectedPercent;
        SimulationEpidemicProperties.totalTimeInMs = totalTimeInMs;
        SimulationEpidemicProperties.simulatedDayDurationInMs = simulatedDayDurationInMs;
    }
    public static long getTotalTimeInMs() {
        return totalTimeInMs;
    }

    public static BigDecimal getInitialInfectedPercent() {
        return BigDecimal.valueOf(initialInfectedPercent);
    }

    public static long getSimulatedDayDurationInMs() {
        return simulatedDayDurationInMs;
    }
}
