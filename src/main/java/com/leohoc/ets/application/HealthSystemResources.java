package com.leohoc.ets.application;

public class HealthSystemResources {

    private int availableUCIBeds;

    public HealthSystemResources(int availableUCIBeds) {
        this.availableUCIBeds = availableUCIBeds;
    }

    public boolean hasAvailableUCIBed() {
        return availableUCIBeds > 0;
    }

    public void fillUCIBed() {
        availableUCIBeds--;
    }

    public void releaseUCIBed() {
        availableUCIBeds++;
    }
}
