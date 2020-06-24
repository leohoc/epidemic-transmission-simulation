package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.HealthStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthConditionTest {

    @Test
    void testHospitalizationNeedVerified() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.INFECTED);

        // When
        healthCondition.hospitalizationNeedVerified();

        // Then
        assertTrue(healthCondition.isHospitalizationNeedVerified());
    }

    @Test
    void testHospitalizatinNeedNotVerified() {
        // Given
        HealthCondition healthCondition = new HealthCondition(HealthStatus.INFECTED);

        // When
        boolean isHospitalizationNeedVerified = healthCondition.isHospitalizationNeedVerified();

        // Then
        assertFalse(isHospitalizationNeedVerified);
    }
}