package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class EpidemicStatistics {

    private Map<HealthStatus, Integer> currentHealthStatusStatistic = new EnumMap<>(HealthStatus.class);
    private Integer totalHospitalizedCount = 0;

    public EpidemicStatistics() {
        for (HealthStatus healthStatus : Arrays.asList(HealthStatus.values())) {
            currentHealthStatusStatistic.put(healthStatus, 0);
        }
    }

    public void updateStatistics(final HealthStatus healthStatus) {
        Integer healthStatusCount = currentHealthStatusStatistic.get(healthStatus);
        currentHealthStatusStatistic.put(healthStatus, ++healthStatusCount);
    }

    public Integer getCurrentHospitalizedCount() {
        return currentHealthStatusStatistic.get(HealthStatus.HOSPITALIZED);
    }

    public Integer getCurrentInfectedCount() {
        return currentHealthStatusStatistic.get(HealthStatus.INFECTED);
    }

    public Integer getCurrentNormalCount() {
        return currentHealthStatusStatistic.get(HealthStatus.NORMAL);
    }

    public Integer getTotalRecoveredCount() {
        return currentHealthStatusStatistic.get(HealthStatus.RECOVERED);
    }

    public Integer getTotalDeadCount() {
        return currentHealthStatusStatistic.get(HealthStatus.DEAD);
    }

    public void increaseHospitalizedCount() {
        totalHospitalizedCount++;
    }

    public Integer getTotalHospitalizedCount() {
        return totalHospitalizedCount;
    }

    public int getTotalInfectedCount() {
        return getCurrentInfectedCount() + getCurrentHospitalizedCount() + getTotalRecoveredCount() + getTotalDeadCount();
    }

    public void updateAllStatistics(final EpidemicStatistics epidemicStatistics) {
        this.currentHealthStatusStatistic = epidemicStatistics.getCurrentHealthStatusStatistic();
        this.totalHospitalizedCount += epidemicStatistics.totalHospitalizedCount;
    }

    public Map<HealthStatus, Integer> getCurrentHealthStatusStatistic() {
        return currentHealthStatusStatistic;
    }
}
