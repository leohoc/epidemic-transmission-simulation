package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.generators.PropertiesGenerator;
import com.leohoc.ets.infrastructure.config.SimulationMovementProperties;
import org.junit.jupiter.api.Test;

import static com.leohoc.ets.generators.PropertiesGenerator.generateIndividualProperties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MovementBehaviorTest {

    private static final int INITIAL_POINT = 0;
    private static final int MIDDLE_POINT = 5;
    private static final int FINAL_POINT = 10;

    @Test
    public void testAdjustDirectionWithoutChangingIt() {
        // Given
        DirectionMovement currentDirectionMovement = DirectionMovement.RIGHT;
        Individual individual = new Individual(MIDDLE_POINT, MIDDLE_POINT, currentDirectionMovement, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(currentDirectionMovement, individual.getDirectionMovement());
    }

    @Test
    public void testAdjustDirectionChangingIt() {
        // Given
        DirectionMovement currentDirectionMovement = DirectionMovement.LEFT;
        Individual individual = new Individual(MIDDLE_POINT, MIDDLE_POINT, currentDirectionMovement, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        DirectionMovement newDirectionMovement = DirectionMovement.RIGHT;
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.TRUE);
        when(movementBehavior.newRandomDirection()).thenReturn(newDirectionMovement);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(newDirectionMovement, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingLeftMapBoundary() {
        // Given
        Individual individual = new Individual(INITIAL_POINT, MIDDLE_POINT, DirectionMovement.LEFT, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.RIGHT, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingRightMapBoundary() {
        // Given
        Individual individual = new Individual(FINAL_POINT, MIDDLE_POINT, DirectionMovement.RIGHT, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.LEFT, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingUpMapBoundary() {
        // Given
        Individual individual = new Individual(MIDDLE_POINT, INITIAL_POINT, DirectionMovement.UP, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.DOWN, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingDownMapBoundary() {
        // Given
        Individual individual = new Individual(MIDDLE_POINT, FINAL_POINT, DirectionMovement.DOWN, generateIndividualProperties());
        MovementBehavior movementBehavior = spy(new MovementBehavior(PropertiesGenerator.generateMovementProperties()));

        // When
        when(movementBehavior.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.UP, individual.getDirectionMovement());
    }

    @Test
    public void testShouldChangeDirectionWithZeroPercentChance() {
        // Given
        SimulationMovementProperties movementProperties = mock(SimulationMovementProperties.class);
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties);

        // When
        when(movementProperties.getDirectionChangeProbability()).thenReturn(0.0);
        boolean shouldChangeDirection = movementBehavior.shouldChangeDirectionRandomly();

        // Then
        assertFalse(shouldChangeDirection);
    }

    @Test
    public void testShouldChangeDirectionWithOneHundredPercentChance() {
        // Given
        SimulationMovementProperties movementProperties = mock(SimulationMovementProperties.class);
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties);

        // When
        when(movementProperties.getDirectionChangeProbability()).thenReturn(100.0);
        boolean shouldChangeDirection = movementBehavior.shouldChangeDirectionRandomly();

        // Then
        assertTrue(shouldChangeDirection);
    }
}