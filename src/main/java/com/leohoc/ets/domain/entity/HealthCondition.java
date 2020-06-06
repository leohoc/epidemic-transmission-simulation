package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

public class HealthCondition {

    private static final Integer DEFAULT_START_DAY = 0;

    private final HealthStatus healthStatus;
    private final Integer startDay;

    public HealthCondition(final HealthStatus healthStatus, final Integer startDay) {
        this.healthStatus = healthStatus;
        this.startDay = startDay;
    }

    public HealthCondition(final HealthStatus healthStatus) {
        this(healthStatus, DEFAULT_START_DAY);
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public Integer getStartDay() {
        return startDay;
    }
}