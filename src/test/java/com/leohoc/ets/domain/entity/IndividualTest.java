package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.leohoc.ets.builders.PropertiesBuilder.buildIndividualProperties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndividualTest {

    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int INITIAL_X = 0;
    private static final int INITIAL_Y = 0;
    private static final int ONE_INVOCATION = 1;

    @Test
    public void testLivingIndividualMovement() {
        // Given
        DirectionMovement directionMovement = DirectionMovement.RIGHT;
        Individual individual = spy(new Individual(INITIAL_X, INITIAL_Y, directionMovement, buildIndividualProperties()));

        // When
        doNothing().when(individual).adjustDirection();
        individual.move();

        // Then
        final int expectedFinalX = 1;
        final int expectedFinalY = 0;
        assertEquals(expectedFinalX, individual.getX());
        assertEquals(expectedFinalY, individual.getY());
    }
    @Test
    public void testDeadIndividualMovement() {
        // Given
        DirectionMovement directionMovement = DirectionMovement.RIGHT;
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, directionMovement, buildIndividualProperties());
        individual.died(CURRENT_SIMULATED_DAY);

        // When
        individual.move();

        // Then
        final int expectedFinalX = 0;
        final int expectedFinalY = 0;
        assertEquals(expectedFinalX, individual.getX());
        assertEquals(expectedFinalY, individual.getY());
    }

    @Test
    public void testIndividualGotInfected() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, buildIndividualProperties());

        // When
        individual.gotInfected(CURRENT_SIMULATED_DAY);

        // Then
        assertTrue(individual.isInfected());
    }

    @Test
    public void testIndividualDied() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, buildIndividualProperties());

        // When
        individual.died(CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthStatus());
        assertEquals(CURRENT_SIMULATED_DAY, individual.getHealthCondition().getStartDay().intValue());
        assertEquals(DirectionMovement.STANDING, individual.getDirectionMovement());
    }

    @Test
    public void testIndividualRecovered() {
        // Given
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, buildIndividualProperties());

        // When
        individual.recovered(CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.RECOVERED, individual.getHealthStatus());
        assertEquals(CURRENT_SIMULATED_DAY, individual.getHealthCondition().getStartDay().intValue());
    }

    @Test
    public void testAdjustDirectionWithoutChangingIt() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, buildIndividualProperties()));

        // When
        when(individual.shouldChangeDirectionRandomly()).thenReturn(Boolean.FALSE);
        individual.adjustDirection();

        // Then
        verify(individual, times(ONE_INVOCATION)).changeDirectionByReachingMapBoundaries();
        verify(individual, never()).changeDirection();
    }

    @Test
    public void testAdjustDirectionChangingIt() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, buildIndividualProperties()));

        // When
        when(individual.shouldChangeDirectionRandomly()).thenReturn(Boolean.TRUE);
        individual.adjustDirection();

        // Then
        verify(individual, times(ONE_INVOCATION)).changeDirectionByReachingMapBoundaries();
        verify(individual, times(ONE_INVOCATION)).changeDirection();
    }

    @Test
    public void testChangeDirectionByReachingLeftMapBoundary() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.LEFT, buildIndividualProperties()));

        // When
        when(individual.reachedLeftBoundary()).thenReturn(Boolean.TRUE);
        individual.changeDirectionByReachingMapBoundaries();

        // Then
        assertEquals(DirectionMovement.RIGHT, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingRightMapBoundary() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.RIGHT, buildIndividualProperties()));

        // When
        when(individual.reachedLeftBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedRightBoundary()).thenReturn(Boolean.TRUE);
        individual.changeDirectionByReachingMapBoundaries();

        // Then
        assertEquals(DirectionMovement.LEFT, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingUpMapBoundary() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.UP, buildIndividualProperties()));

        // When
        when(individual.reachedLeftBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedRightBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedUpBoundary()).thenReturn(Boolean.TRUE);
        individual.changeDirectionByReachingMapBoundaries();

        // Then
        assertEquals(DirectionMovement.DOWN, individual.getDirectionMovement());
    }

    @Test
    public void testChangeDirectionByReachingDownMapBoundary() {
        // Given
        Individual individual = Mockito.spy(new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.DOWN, buildIndividualProperties()));

        // When
        when(individual.reachedLeftBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedRightBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedUpBoundary()).thenReturn(Boolean.FALSE);
        when(individual.reachedDownBoundary()).thenReturn(Boolean.TRUE);
        individual.changeDirectionByReachingMapBoundaries();

        // Then
        assertEquals(DirectionMovement.UP, individual.getDirectionMovement());
    }

    @Test
    public void testReachedLeftMapBoundary() {
        // Given
        int initialX = 0;
        int initialY = 0;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.LEFT, individualProperties);

        // When
        when(individualProperties.getLeftBoundary()).thenReturn(0);
        boolean hasReachedLeftBoundary = individual.reachedLeftBoundary();

        assertTrue(hasReachedLeftBoundary);
    }

    @Test
    public void testHasNotReachedLeftMapBoundary() {
        // Given
        int initialX = 1;
        int initialY = 0;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.LEFT, individualProperties);

        // When
        when(individualProperties.getLeftBoundary()).thenReturn(0);
        boolean hasReachedLeftBoundary = individual.reachedLeftBoundary();

        assertFalse(hasReachedLeftBoundary);
    }

    @Test
    public void testReachedRightMapBoundary() {
        // Given
        int initialX = 10;
        int initialY = 0;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.RIGHT, individualProperties);

        // When
        when(individualProperties.getRightBoundary()).thenReturn(10);
        boolean hasReachedRightBoundary = individual.reachedRightBoundary();

        assertTrue(hasReachedRightBoundary);
    }

    @Test
    public void testHasNotReachedRightMapBoundary() {
        // Given
        int initialX = 9;
        int initialY = 0;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.RIGHT, individualProperties);

        // When
        when(individualProperties.getRightBoundary()).thenReturn(10);
        boolean hasReachedRightBoundary = individual.reachedRightBoundary();

        assertFalse(hasReachedRightBoundary);
    }

    @Test
    public void testReachedUpMapBoundary() {
        // Given
        int initialX = 0;
        int initialY = 0;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.UP, individualProperties);

        // When
        when(individualProperties.getUpBoundary()).thenReturn(0);
        boolean hasReachedUpBoundary = individual.reachedUpBoundary();

        assertTrue(hasReachedUpBoundary);
    }

    @Test
    public void testHasNotReachedUpMapBoundary() {
        // Given
        int initialX = 0;
        int initialY = 1;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.UP, individualProperties);

        // When
        when(individualProperties.getUpBoundary()).thenReturn(0);
        boolean hasReachedUpBoundary = individual.reachedUpBoundary();

        assertFalse(hasReachedUpBoundary);
    }

    @Test
    public void testReachedDownMapBoundary() {
        // Given
        int initialX = 0;
        int initialY = 10;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.DOWN, individualProperties);

        // When
        when(individualProperties.getDownBoundary()).thenReturn(10);
        boolean hasReachedDownBoundary = individual.reachedDownBoundary();

        assertTrue(hasReachedDownBoundary);
    }

    @Test
    public void testHasNotReachedDownMapBoundary() {
        // Given
        int initialX = 0;
        int initialY = 9;
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(initialX, initialY, DirectionMovement.DOWN, individualProperties);

        // When
        when(individualProperties.getDownBoundary()).thenReturn(10);
        boolean hasReachedDownBoundary = individual.reachedDownBoundary();

        assertFalse(hasReachedDownBoundary);
    }

    @Test
    public void testShouldChangeDirectionWithZeroPercentChance() {
        // Given
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties);

        // When
        when(individualProperties.getDirectionChangeProbability()).thenReturn(0.0);
        boolean shouldChangeDirection = individual.shouldChangeDirectionRandomly();

        // Then
        assertFalse(shouldChangeDirection);
    }

    @Test
    public void testShouldChangeDirectionWithOneHundredPercentChance() {
        // Given
        SimulationIndividualProperties individualProperties = mock(SimulationIndividualProperties.class);
        Individual individual = new Individual(INITIAL_X, INITIAL_Y, DirectionMovement.STANDING, individualProperties);

        // When
        when(individualProperties.getDirectionChangeProbability()).thenReturn(100.0);
        boolean shouldChangeDirection = individual.shouldChangeDirectionRandomly();

        // Then
        assertTrue(shouldChangeDirection);
    }

    @Test
    public void testIndividualCrossedWayWithPasserBy() {
        // Given
        int individualX = 0;
        int individualY = 0;
        Individual individual = spy(new Individual(individualX, individualY, DirectionMovement.STANDING, buildIndividualProperties()));
        int passerbyX = 1;
        int passerbyY = 1;
        Individual passerby = new Individual(passerbyX, passerbyY, DirectionMovement.STANDING, buildIndividualProperties());

        // When
        when(individual.getWidth()).thenReturn(2);
        when(individual.getHeight()).thenReturn(2);
        boolean hasCrossedWay = individual.crossedWayWith(passerby);

        // Then
        assertTrue(hasCrossedWay);
    }

    @Test
    public void testIndividualHasNotCrossedWayWithPasserBy() {
        // Given
        int individualX = 0;
        int individualY = 0;
        Individual individual = spy(new Individual(individualX, individualY, DirectionMovement.STANDING, buildIndividualProperties()));
        int passerbyX = 1;
        int passerbyY = 1;
        Individual passerby = new Individual(passerbyX, passerbyY, DirectionMovement.STANDING, buildIndividualProperties());

        // When
        when(individual.getWidth()).thenReturn(1);
        when(individual.getHeight()).thenReturn(1);
        boolean hasCrossedWay = individual.crossedWayWith(passerby);

        // Then
        assertFalse(hasCrossedWay);
    }
}