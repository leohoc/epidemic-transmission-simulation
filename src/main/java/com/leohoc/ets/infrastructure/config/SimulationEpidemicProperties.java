package com.leohoc.ets.infrastructure.config;

public class SimulationEpidemicProperties {

    private final int recoveryDays;
    private final int deathPercentage;

    public SimulationEpidemicProperties(final int recoveryDays,
                                        final int deathPercentage) {
        this.recoveryDays = recoveryDays;
        this.deathPercentage = deathPercentage;
    }

    public int getRecoveryDays() {
        return recoveryDays;
    }

    public int getDeathPercentage() {
        return deathPercentage;
    }
}
