package com.leohoc.ets.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionMovementTest {

    @Test
    public void testRandomDirectionMovementWithHundredPercentSocialIsolation() {
        // Given
        final double socialIsolationPercent = 100.0;

        // When
        DirectionMovement directionMovement = DirectionMovement.randomDirectionMovement(socialIsolationPercent);

        // Then
        assertEquals(DirectionMovement.STANDING, directionMovement);
    }

    @Test
    public void testRandomDirectionMovementWithZeroPercentSocialIsolation() {
        // Given
        final double socialIsolationPercent = 0.0;

        // When
        DirectionMovement directionMovement = DirectionMovement.randomDirectionMovement(socialIsolationPercent);

        // Then
        assertTrue(DirectionMovement.movementDirections().contains(directionMovement));
    }
}