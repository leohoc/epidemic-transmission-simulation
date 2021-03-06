package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.IndividualProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndividualTest {

    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int INITIAL_X = 0;
    private static final int INITIAL_Y = 0;

    private IndividualProperties individualProperties = mock(IndividualProperties.class);

    @BeforeEach
    void setup() {
        when(individualProperties.getIndividualWidth()).thenReturn(1);
        when(individualProperties.getIndividualHeight()).thenReturn(1);
    }

    @Test
    void testLivingIndividualMovement() {
        // Given
        DirectionMovement newDirectionMovement = DirectionMovement.RIGHT;
        Individual individual = spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties));

        // When
        individual.moveTo(newDirectionMovement);

        // Then
        final int expectedFinalX = 1;
        final int expectedFinalY = 0;
        assertEquals(expectedFinalX, individual.getX());
        assertEquals(expectedFinalY, individual.getY());
    }

    @Test
    void testDeadIndividualMovement() {
        // Given
        DirectionMovement newDirectionMovement = DirectionMovement.RIGHT;
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties);
        individual.died(CURRENT_SIMULATED_DAY);

        // When
        individual.moveTo(newDirectionMovement);

        // Then
        final int expectedFinalX = 0;
        final int expectedFinalY = 0;
        assertEquals(expectedFinalX, individual.getX());
        assertEquals(expectedFinalY, individual.getY());
    }

    @Test
    void testIndividualGotInfected() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties);

        // When
        individual.gotInfected(CURRENT_SIMULATED_DAY);

        // Then
        assertTrue(individual.isInfected());
    }

    @Test
    void testIndividualGotHospitalized() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties);

        // When
        individual.gotHospitalized(CURRENT_SIMULATED_DAY);

        // Then
        assertTrue(individual.isHospitalized());
    }

    @Test
    void testIndividualDied() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, individualProperties);

        // When
        individual.died(CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthStatus());
        assertEquals(CURRENT_SIMULATED_DAY, individual.getHealthCondition().getStartDay().intValue());
        assertEquals(DirectionMovement.STANDING, individual.getDirectionMovement());
    }

    @Test
    void testIndividualRecovered() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, individualProperties);

        // When
        individual.recovered(CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.RECOVERED, individual.getHealthStatus());
        assertEquals(CURRENT_SIMULATED_DAY, individual.getHealthCondition().getStartDay().intValue());
    }

    @Test
    void testIndividualCrossedWayWithPasserBy() {
        // Given
        int individualX = 0;
        int individualY = 0;
        Individual individual = spy(new Individual(individualX, individualY, DirectionMovement.STANDING, individualProperties));
        int passerbyX = 1;
        int passerbyY = 1;
        Individual passerby = new Individual(passerbyX, passerbyY, DirectionMovement.STANDING, individualProperties);

        // When
        when(individual.getWidth()).thenReturn(2);
        when(individual.getHeight()).thenReturn(2);
        boolean hasCrossedWay = individual.crossedWayWith(passerby);

        // Then
        assertTrue(hasCrossedWay);
    }

    @Test
    void testIndividualHasNotCrossedWayWithPasserBy() {
        // Given
        int individualX = 0;
        int individualY = 0;
        Individual individual = spy(new Individual(individualX, individualY, DirectionMovement.STANDING, individualProperties));
        int passerbyX = 1;
        int passerbyY = 1;
        Individual passerby = new Individual(passerbyX, passerbyY, DirectionMovement.STANDING, individualProperties);

        // When
        when(individual.getWidth()).thenReturn(1);
        when(individual.getHeight()).thenReturn(1);
        boolean hasCrossedWay = individual.crossedWayWith(passerby);

        // Then
        assertFalse(hasCrossedWay);
    }
}