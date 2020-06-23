package com.leohoc.ets.infrastructure.config;

public class HealthSystemCapacityProperties {

    private final int availableBeds;

    public HealthSystemCapacityProperties(final int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }
}
