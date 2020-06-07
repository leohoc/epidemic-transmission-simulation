package com.leohoc.ets.simulation;

public class HealthSystemResources {

    private int availableICUBeds;

    public HealthSystemResources(int availableICUBeds) {
        this.availableICUBeds = availableICUBeds;
    }

    public boolean hasAvailableICUBed() {
        return availableICUBeds > 0;
    }

    public void fillICUBed() {
        availableICUBeds--;
    }

    public void releaseICUBed() {
        availableICUBeds++;
    }

    public int getAvailableICUBeds() {
        return availableICUBeds;
    }
}
