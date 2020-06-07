package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

public class HealthCondition {

    private static final Integer DEFAULT_START_DAY = 0;

    private final HealthStatus healthStatus;
    private final Integer startDay;
    private boolean hospitalizationNeedVerified;

    public HealthCondition(final HealthStatus healthStatus, final Integer startDay) {
        this.healthStatus = healthStatus;
        this.startDay = startDay;
        this.hospitalizationNeedVerified = Boolean.FALSE;
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

    public boolean isHospitalizationNeedVerified() {
        return hospitalizationNeedVerified;
    }

    public void hospitalizationNeedVerified() {
        this.hospitalizationNeedVerified = Boolean.TRUE;
    }
}