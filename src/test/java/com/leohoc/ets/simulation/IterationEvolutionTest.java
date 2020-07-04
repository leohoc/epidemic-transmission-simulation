package com.leohoc.ets.simulation;

import com.leohoc.ets.infrastructure.config.IterationsProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IterationEvolutionTest {

    private IterationsProperties iterationsProperties = mock(IterationsProperties.class);
    private IterationEvolution iterationEvolution;

    @BeforeEach
    void setup() {
        iterationEvolution = new IterationEvolution(iterationsProperties);
        when(iterationsProperties.getTotalIterations()).thenReturn(100);
        when(iterationsProperties.getIterationsPerDay()).thenReturn(10);
    }

    @Test
    void testIterate() {
        // Given
        iterationEvolution.setCurrentIteration(10);

        // When
        iterationEvolution.iterate();

        // Then
        int expectedIteration = 11;
        assertEquals(expectedIteration, iterationEvolution.getCurrentIteration());
    }

    @Test
    void testGetCurrentSimulatedDays() {
        // Given
        iterationEvolution.setCurrentIteration(50);

        // When
        int actualCurrentSimulatedDay = iterationEvolution.getCurrentSimulatedDay();

        // Then
        int expectedCurrentSimulatedDay = 5;
        assertEquals(expectedCurrentSimulatedDay, actualCurrentSimulatedDay);
    }

    @Test
    void testSimulationFinished() {
        // Given
        iterationEvolution.setCurrentIteration(101);

        // When
        boolean hasSimulationFinished = iterationEvolution.hasSimulationFinished();

        // Then
        assertTrue(hasSimulationFinished);
    }

    @Test
    void testSimulationNotFinished() {
        // Given
        iterationEvolution.setCurrentIteration(60);

        // When
        boolean hasSimulationFinished = iterationEvolution.hasSimulationFinished();

        // Then
        assertFalse(hasSimulationFinished);
    }
}