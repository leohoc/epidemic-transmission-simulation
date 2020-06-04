package com.leohoc.ets.infrastructure.config;

public class SimulationEpidemicProperties {

    private final int recoveryDays;
    private final double deathPercentage;

    public SimulationEpidemicProperties(final int recoveryDays,
                                        final double deathPercentage) {
        this.recoveryDays = recoveryDays;
        this.deathPercentage = deathPercentage;
    }

    public int getRecoveryDays() {
        return recoveryDays;
    }

    public double getDeathPercentage() {
        return deathPercentage;
    }
}
