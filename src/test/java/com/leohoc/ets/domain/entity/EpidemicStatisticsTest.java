package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpidemicStatisticsTest {

    private static final int ZERO_COUNT = 0;
    private static final int ONE_COUNT = 1;
    private static final int FOUR_COUNT = 4;
    private static final int TWO_COUNT = 2;

    @Test
    void testUpdateStatisticsWithNormalHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.NORMAL;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    void testUpdateStatisticsWithInfectedHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    void testUpdateStatisticsWithHospitalizedStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.HOSPITALIZED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    void testUpdateStatisticsWithRecoveredHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    void testUpdateStatisticsWithDeadHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    void testUpdateTotalHospitalizedCount() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();

        // When
        epidemicStatistics.increaseHospitalizedCount();

        // Then
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalHospitalizedCount().intValue());
    }

    @Test
    void testGetTotalInfectedCount() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();

        // When
        epidemicStatistics.updateStatistics(HealthStatus.NORMAL);
        epidemicStatistics.updateStatistics(HealthStatus.INFECTED);
        epidemicStatistics.updateStatistics(HealthStatus.HOSPITALIZED);
        epidemicStatistics.updateStatistics(HealthStatus.RECOVERED);
        epidemicStatistics.updateStatistics(HealthStatus.DEAD);

        // Then
        assertEquals(FOUR_COUNT, epidemicStatistics.getTotalInfectedCount());
    }

    @Test
    void testUpdateAllStatistics() {
        // Given
        EpidemicStatistics totalEpidemicStatistics = new EpidemicStatistics();
        totalEpidemicStatistics.updateStatistics(HealthStatus.NORMAL);
        totalEpidemicStatistics.updateStatistics(HealthStatus.INFECTED);
        totalEpidemicStatistics.updateStatistics(HealthStatus.HOSPITALIZED);
        totalEpidemicStatistics.updateStatistics(HealthStatus.DEAD);
        totalEpidemicStatistics.increaseHospitalizedCount();

        EpidemicStatistics currentEpidemicStatistics = new EpidemicStatistics();
        currentEpidemicStatistics.updateStatistics(HealthStatus.NORMAL);
        currentEpidemicStatistics.updateStatistics(HealthStatus.INFECTED);
        currentEpidemicStatistics.updateStatistics(HealthStatus.HOSPITALIZED);
        currentEpidemicStatistics.updateStatistics(HealthStatus.RECOVERED);
        currentEpidemicStatistics.updateStatistics(HealthStatus.DEAD);
        currentEpidemicStatistics.increaseHospitalizedCount();

        // When
        totalEpidemicStatistics.updateAllStatistics(currentEpidemicStatistics);

        // Then
        assertEquals(ONE_COUNT, totalEpidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ONE_COUNT, totalEpidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ONE_COUNT, totalEpidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ONE_COUNT, totalEpidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ONE_COUNT, totalEpidemicStatistics.getTotalDeadCount().intValue());
        assertEquals(TWO_COUNT, totalEpidemicStatistics.getTotalHospitalizedCount().intValue());
    }
}