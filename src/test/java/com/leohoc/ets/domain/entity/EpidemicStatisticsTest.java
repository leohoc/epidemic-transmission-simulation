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
        assertEquals(ONE_COUNT, epidemicStatistics.getNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithInfectedHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getNormalCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithRecoveredHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getInfectedCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getRecoveredCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getDeadCount().intValue());
    }

    @Test
    public void testUpdateStatisticsWithDeadHealthStatus() {
        // Given
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        epidemicStatistics.updateStatistics(healthStatus);

        // Then
        assertEquals(ZERO_COUNT, epidemicStatistics.getNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getInfectedCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getRecoveredCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getDeadCount().intValue());
    }
}