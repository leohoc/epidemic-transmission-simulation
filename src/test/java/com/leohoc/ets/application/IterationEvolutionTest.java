package com.leohoc.ets.application;

import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IterationEvolutionTest {

    private static final Integer TOTAL_ITERATIONS = 100;
    private static final Integer ITERATIONS_PER_DAY = 10;

    private IterationEvolution iterationEvolution;

    @BeforeEach
    public void setup() {
        iterationEvolution = new IterationEvolution(new SimulationIterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY));
    }

    @Test
    public void testIterate() {
        // Given
        iterationEvolution.setCurrentIteration(10);

        // When
        iterationEvolution.iterate();

        // Then
        int expectedIteration = 11;
        assertEquals(expectedIteration, iterationEvolution.getCurrentIteration());
    }

    @Test
    public void testGetCurrentSimulatedDays() {
        // Given
        iterationEvolution.setCurrentIteration(50);

        // When
        int actualCurrentSimulatedDay = iterationEvolution.getCurrentSimulatedDay();

        // Then
        int expectedCurrentSimulatedDay = 5;
        assertEquals(expectedCurrentSimulatedDay, actualCurrentSimulatedDay);
    }

    @Test
    public void testSimulationFinished() {
        // Given
        iterationEvolution.setCurrentIteration(101);

        // When
        boolean hasSimulationFinished = iterationEvolution.hasSimulationFinished();

        // Then
        assertTrue(hasSimulationFinished);
    }

    @Test
    public void testSimulationNotFinished() {
        // Given
        iterationEvolution.setCurrentIteration(60);

        // When
        boolean hasSimulationFinished = iterationEvolution.hasSimulationFinished();

        // Then
        assertFalse(hasSimulationFinished);
    }
}