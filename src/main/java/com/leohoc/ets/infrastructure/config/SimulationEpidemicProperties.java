package com.leohoc.ets.infrastructure.config;

import java.math.BigDecimal;

public class SimulationEpidemicProperties {

    private static int initialInfectedPercent;
    private static long totalTimeInMs;
    private static long simulatedDayDurationInMs;
    private static int recoveryDays;

    static void loadProperties(final int initialInfectedPercent, final long totalTimeInMs, final long simulatedDayDurationInMs, final int recoveryDays) {
        SimulationEpidemicProperties.initialInfectedPercent = initialInfectedPercent;
        SimulationEpidemicProperties.totalTimeInMs = totalTimeInMs;
        SimulationEpidemicProperties.simulatedDayDurationInMs = simulatedDayDurationInMs;
        SimulationEpidemicProperties.recoveryDays = recoveryDays;
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

    public static int getRecoveryDays() {
        return recoveryDays;
    }
}
