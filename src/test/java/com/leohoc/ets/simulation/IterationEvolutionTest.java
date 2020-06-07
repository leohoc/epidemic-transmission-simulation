package com.leohoc.ets.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.leohoc.ets.builders.PropertiesBuilder.buildIterationsProperties;
import static org.junit.jupiter.api.Assertions.*;

class IterationEvolutionTest {

    private IterationEvolution iterationEvolution;

    @BeforeEach
    public void setup() {
        iterationEvolution = new IterationEvolution(buildIterationsProperties());
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