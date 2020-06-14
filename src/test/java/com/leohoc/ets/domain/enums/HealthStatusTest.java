package com.leohoc.ets.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthStatusTest {

    @Test
    void testNormalHealthStatusNotInfected() {
        // Given
        HealthStatus healthStatus = HealthStatus.NORMAL;

        // When
        boolean infected = healthStatus.infected();

        // Then
        assertFalse(infected);
    }

    @Test
    void testInfectedHealthStatusInfected() {
        // Given
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        boolean infected = healthStatus.infected();

        // Then
        assertTrue(infected);
    }

    @Test
    void testHospitalizedHealthStatusInfected() {
        // Given
        HealthStatus healthStatus = HealthStatus.HOSPITALIZED;

        // When
        boolean infected = healthStatus.infected();

        // Then
        assertTrue(infected);
    }

    @Test
    void testRecoveredHealthStatusNotInfected() {
        // Given
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        boolean infected = healthStatus.infected();

        // Then
        assertFalse(infected);
    }

    @Test
    void testDeadHealthStatusNotInfected() {
        // Given
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        boolean infected = healthStatus.infected();

        // Then
        assertFalse(infected);
    }

    @Test
    void testNormalHealthStatusAllowedToMove() {
        // Given
        HealthStatus healthStatus = HealthStatus.NORMAL;

        // When
        boolean allowedToMove = healthStatus.allowedToMove();

        // Then
        assertTrue(allowedToMove);
    }

    @Test
    void testInfectedHealthStatusAllowedToMove() {
        // Given
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        boolean allowedToMove = healthStatus.allowedToMove();

        // Then
        assertTrue(allowedToMove);
    }

    @Test
    void testHospitalizedHealthStatusNotAllowedToMove() {
        // Given
        HealthStatus healthStatus = HealthStatus.HOSPITALIZED;

        // When
        boolean allowedToMove = healthStatus.allowedToMove();

        // Then
        assertFalse(allowedToMove);
    }

    @Test
    void testRecoveredHealthStatusAllowedToMove() {
        // Given
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        boolean allowedToMove = healthStatus.allowedToMove();

        // Then
        assertTrue(allowedToMove);
    }

    @Test
    void testDeadHealthStatusNotAllowedToMove() {
        // Given
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        boolean allowedToMove = healthStatus.allowedToMove();

        // Then
        assertFalse(allowedToMove);
    }

    @Test
    void testNormalHealthStatusHasNotAntibodies() {
        // Given
        HealthStatus healthStatus = HealthStatus.NORMAL;

        // When
        boolean hasAntibodies = healthStatus.hasAntibodies();

        // Then
        assertFalse(hasAntibodies);
    }

    @Test
    void testInfectedHealthStatusHasAntibodies() {
        // Given
        HealthStatus healthStatus = HealthStatus.INFECTED;

        // When
        boolean hasAntibodies = healthStatus.hasAntibodies();

        // Then
        assertTrue(hasAntibodies);
    }

    @Test
    void testHospitalizedHealthStatusHasAntibodies() {
        // Given
        HealthStatus healthStatus = HealthStatus.HOSPITALIZED;

        // When
        boolean hasAntibodies = healthStatus.hasAntibodies();

        // Then
        assertTrue(hasAntibodies);
    }

    @Test
    void testRecoveredHealthStatusHasAntibodies() {
        // Given
        HealthStatus healthStatus = HealthStatus.RECOVERED;

        // When
        boolean hasAntibodies = healthStatus.hasAntibodies();

        // Then
        assertTrue(hasAntibodies);
    }

    @Test
    void testDeadHealthStatusHasAntibodies() {
        // Given
        HealthStatus healthStatus = HealthStatus.DEAD;

        // When
        boolean hasAntibodies = healthStatus.hasAntibodies();

        // Then
        assertTrue(hasAntibodies);
    }

}