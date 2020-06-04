package com.leohoc.ets.infrastructure.config;

public class SimulationEpidemicProperties {

    private final int recoveryDays;
    private final double deathPercentage;
    private final double hospitalizationPercentage;
    private final int hospitalizationDays;

    public SimulationEpidemicProperties(final int recoveryDays,
                                        final double deathPercentage,
                                        final double hospitalizationPercentage,
                                        final int hospitalizationDays) {
        this.recoveryDays = recoveryDays;
        this.deathPercentage = deathPercentage;
        this.hospitalizationPercentage = hospitalizationPercentage;
        this.hospitalizationDays = hospitalizationDays;
    }

    public int getRecoveryDays() {
        return recoveryDays;
    }

    public double getDeathPercentage() {
        return deathPercentage;
    }

    public double getHospitalizationPercentage() {
        return hospitalizationPercentage;
    }

    public int getHospitalizationDays() {
        return hospitalizationDays;
    }
}
