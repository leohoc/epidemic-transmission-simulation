package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;

import java.util.Arrays;
import java.util.HashMap;

public class EpidemicStatistics {

    private HashMap<HealthStatus, Integer> currentHealthStatusStatistic = new HashMap<>();
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

    public void updateAllStatistics(final EpidemicStatistics epidemicStatistics) {
        this.currentHealthStatusStatistic = epidemicStatistics.getCurrentHealthStatusStatistic();
        this.totalHospitalizedCount += epidemicStatistics.totalHospitalizedCount;
    }

    public void increaseHospitalizedCount() {
        totalHospitalizedCount++;
    }

    public HashMap<HealthStatus, Integer> getCurrentHealthStatusStatistic() {
        return currentHealthStatusStatistic;
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

    public Integer getTotalHospitalizedCount() {
        return totalHospitalizedCount;
    }

    public int getTotalInfectedCount() {
        return getCurrentInfectedCount() + getCurrentHospitalizedCount() + getTotalRecoveredCount() + getTotalDeadCount();
    }

    public void printStatisticsGlimpse(final int currentDay) {
        System.out.println("-------------------------------------");
        System.out.println("CURRENT DAY: " + currentDay);
        System.out.println("CURRENT NORMAL COUNT: " + getCurrentNormalCount());
        System.out.println("CURRENT INFECTED COUNT: " + getCurrentInfectedCount());
        System.out.println("CURRENT HOSPITALIZED COUNT: " + getCurrentHospitalizedCount());
        System.out.println("TOTAL INFECTED COUNT: " + getTotalInfectedCount());
        System.out.println("TOTAL HOSPITALIZED COUNT: " + getTotalHospitalizedCount());
        System.out.println("TOTAL RECOVERED COUNT: " + getTotalRecoveredCount());
        System.out.println("TOTAL DEAD COUNT: " + getTotalDeadCount());
    }
}
