package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

import java.util.HashMap;

public class EpidemicStatistics {

    private HashMap<HealthStatus, Integer> healthStatusStatistic = new HashMap<>();

    public EpidemicStatistics() {
        healthStatusStatistic.put(HealthStatus.NORMAL, 0);
        healthStatusStatistic.put(HealthStatus.INFECTED, 0);
        healthStatusStatistic.put(HealthStatus.RECOVERED, 0);
        healthStatusStatistic.put(HealthStatus.DEAD, 0);
    }

    public void updateStatistics(final HealthStatus healthStatus) {
        Integer healthStatusCount = healthStatusStatistic.get(healthStatus);
        healthStatusStatistic.put(healthStatus, ++healthStatusCount);
    }

    public void updateAllStatistics(final HashMap<HealthStatus, Integer> healthStatusStatistic) {
        this.healthStatusStatistic = healthStatusStatistic;
    }

    public HashMap<HealthStatus, Integer> getHealthStatusStatistic() {
        return healthStatusStatistic;
    }

    public Integer getInfectedCount() {
        return healthStatusStatistic.get(HealthStatus.INFECTED);
    }

    public Integer getNormalCount() {
        return healthStatusStatistic.get(HealthStatus.NORMAL);
    }

    public Integer getRecoveredCount() {
        return healthStatusStatistic.get(HealthStatus.RECOVERED);
    }

    public Integer getDeadCount() {
        return healthStatusStatistic.get(HealthStatus.DEAD);
    }
}
