package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.MovementProperties;
import com.leohoc.ets.util.RandomUtil;
import org.junit.jupiter.api.Test;


import static com.leohoc.ets.generators.PropertiesGenerator.generateIndividualProperties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MovementBehaviorTest {

    private static final int INITIAL_POINT = 0;
    private static final int MIDDLE_POINT = 5;
    private static final int FINAL_POINT = 10;
    private static final int ONE_INVOCATION = 1;

    private final MovementProperties movementProperties = mock(MovementProperties.class);
    private final RandomUtil randomUtil = mock(RandomUtil.class);

    @Test
    void testAdjustDirectionWithoutChangingIt() {
        // Given
        DirectionMovement currentDirectionMovement = DirectionMovement.RIGHT;
        Individual individual = new Individual(MIDDLE_POINT, MIDDLE_POINT, currentDirectionMovement, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(5.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(currentDirectionMovement, individual.getDirectionMovement());
    }

    @Test
    void testAdjustDirectionRespectingSocialIsolation() {
        // Given
        DirectionMovement currentDirectionMovement = DirectionMovement.LEFT;
        Individual individual = new Individual(MIDDLE_POINT, MIDDLE_POINT, currentDirectionMovement, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(15.0);
        when(movementProperties.getSocialIsolationPercent()).thenReturn(15.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.STANDING, individual.getDirectionMovement());
    }

    @Test
    void testChangeDirectionByReachingLeftMapBoundary() {
        // Given
        Individual individual = new Individual(INITIAL_POINT, MIDDLE_POINT, DirectionMovement.LEFT, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(5.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.RIGHT, individual.getDirectionMovement());
    }

    @Test
    void testChangeDirectionByReachingRightMapBoundary() {
        // Given
        Individual individual = new Individual(FINAL_POINT, MIDDLE_POINT, DirectionMovement.RIGHT, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(5.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.LEFT, individual.getDirectionMovement());
    }

    @Test
    void testChangeDirectionByReachingUpMapBoundary() {
        // Given
        Individual individual = new Individual(MIDDLE_POINT, INITIAL_POINT, DirectionMovement.UP, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(5.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.DOWN, individual.getDirectionMovement());
    }

    @Test
    void testChangeDirectionByReachingDownMapBoundary() {
        // Given
        Individual individual = new Individual(MIDDLE_POINT, FINAL_POINT, DirectionMovement.DOWN, generateIndividualProperties());
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getDirectionChangeProbability()).thenReturn(5.0);
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.adjustDirectionOf(individual);

        // Then
        assertEquals(DirectionMovement.UP, individual.getDirectionMovement());
    }

    @Test
    void testNewRandomDirection() {
        // Given
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);
        final int expectedDirectionMovementIndex = 0;

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(movementProperties.getSocialIsolationPercent()).thenReturn(5.0);
        when(randomUtil.generateIntLessThan(eq(DirectionMovement.movementDirections().size()))).thenReturn(expectedDirectionMovementIndex);
        DirectionMovement actualDirectionMovement = movementBehavior.newRandomDirection();

        // Then
        assertEquals(DirectionMovement.movementDirections().get(expectedDirectionMovementIndex), actualDirectionMovement);
    }

    @Test
    void testGenerateRandomXPointWithinMapBoundaries() {
        // Given
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(movementProperties.getLeftBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getRightBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.generateRandomXPointWithinMapBoundaries();

        // Then
        verify(randomUtil, times(ONE_INVOCATION)).generateIntBetween(INITIAL_POINT, FINAL_POINT);
    }

    @Test
    void testGenerateRandomYPointWithinMapBoundaries() {
        // Given
        MovementBehavior movementBehavior = new MovementBehavior(movementProperties, randomUtil);

        // When
        when(movementProperties.getUpBoundary()).thenReturn(INITIAL_POINT);
        when(movementProperties.getDownBoundary()).thenReturn(FINAL_POINT);
        movementBehavior.generateRandomYPointWithinMapBoundaries();

        // Then
        verify(randomUtil, times(ONE_INVOCATION)).generateIntBetween(INITIAL_POINT, FINAL_POINT);
    }
}