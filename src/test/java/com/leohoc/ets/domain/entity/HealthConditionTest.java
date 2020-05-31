package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthConditionTest {

    @Test
    public void testNormalHealthStatusHasNoAntibodies() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.NORMAL);

        // When
        boolean hasNoAntibodies = healthCondition.hasNoAntibodies();

        // Then
        assertTrue(hasNoAntibodies);
    }

    @Test
    public void testInfectedHealthStatusHasAntibodies() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.INFECTED);

        // When
        boolean hasNoAntibodies = healthCondition.hasNoAntibodies();

        // Then
        assertFalse(hasNoAntibodies);
    }

    @Test
    public void testRecoveredHealthStatusHasAntibodies() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.RECOVERED);

        // When
        boolean hasNoAntibodies = healthCondition.hasNoAntibodies();

        // Then
        assertFalse(hasNoAntibodies);
    }

    @Test
    public void testDeadHealthStatusHasAntibodies() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.DEAD);

        // When
        boolean hasNoAntibodies = healthCondition.hasNoAntibodies();

        // Then
        assertFalse(hasNoAntibodies);
    }

}