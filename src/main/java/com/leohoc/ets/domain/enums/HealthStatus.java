package com.leohoc.ets.domain.enums;

import java.util.Arrays;

public enum HealthStatus {

    NORMAL,
    INFECTED,
    HOSPITALIZED,
    RECOVERED,
    DEAD;

    public boolean isInfected() {
        return Arrays.asList(INFECTED, HOSPITALIZED).contains(this);
    }

    public boolean allowedToMove() {
        return Arrays.asList(NORMAL, INFECTED, RECOVERED).contains(this);
    }
}
