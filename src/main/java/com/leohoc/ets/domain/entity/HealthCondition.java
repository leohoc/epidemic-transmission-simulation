package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

public class HealthCondition {

    private final HealthStatus healthStatus;
    private final int startDay;

    public HealthCondition(final HealthStatus healthStatus, final int startDay) {
        this.healthStatus = healthStatus;
        this.startDay = startDay;
    }

    public HealthCondition(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
        this.startDay = 0;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public int getStartDay() {
        return startDay;
    }

    public boolean hasAntibodies() {
        return healthStatus.equals(HealthStatus.INFECTED) || healthStatus.equals(HealthStatus.RECOVERED) || healthStatus.equals(HealthStatus.DEAD);
    }
}
