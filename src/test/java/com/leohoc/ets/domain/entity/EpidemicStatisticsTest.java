package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpidemicStatisticsTest {

    private static final int ONE_COUNT = 1;
    private static final int ZERO_COUNT = 0;

    @Test
    public void testUpdateStatisticsWithNormalHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.NORMAL;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithInfectedHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithRecoveredHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithDeadHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getCurrentInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalRecoveredCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalDeadCount().intValue());
    }
}