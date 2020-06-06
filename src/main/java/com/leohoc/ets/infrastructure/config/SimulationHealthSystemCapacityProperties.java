package com.leohoc.ets.infrastructure.config;

public class SimulationHealthSystemCapacityProperties {

    private final int availableBeds;

    public SimulationHealthSystemCapacityProperties(final int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }
}
