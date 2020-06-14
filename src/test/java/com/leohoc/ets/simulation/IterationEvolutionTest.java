package com.leohoc.ets.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.leohoc.ets.generators.PropertiesGenerator.generateIterationsProperties;
import static org.junit.jupiter.api.Assertions.*;

class IterationEvolutionTest {

    private IterationEvolution iterationEvolution;

    @BeforeEach
    void setup() {
        iterationEvolution = new IterationEvolution(generateIterationsProperties());
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