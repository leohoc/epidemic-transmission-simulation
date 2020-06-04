package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

import java.util.Arrays;
import java.util.HashMap;

public class EpidemicStatistics {

    private HashMap<HealthStatus, Integer> healthStatusStatistic = new HashMap<>();

    public EpidemicStatistics() {
        for (HealthStatus healthStatus : Arrays.asList(HealthStatus.values())) {
            healthStatusStatistic.put(healthStatus, 0);
        }
    }

    public void updateStatistics(final HealthStatus healthStatus) {
        Integer healthStatusCount = healthStatusStatistic.get(healthStatus);
        healthStatusStatistic.put(healthStatus, ++healthStatusCount);
    }

    public void updateAllStatistics(final EpidemicStatistics epidemicStatistics) {
        this.healthStatusStatistic = epidemicStatistics.getHealthStatusStatistic();
    }

    public HashMap<HealthStatus, Integer> getHealthStatusStatistic() {
        return healthStatusStatistic;
    }

    public Integer getHospitalizedCount() {
        return healthStatusStatistic.get(HealthStatus.HOSPITALIZED);
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
