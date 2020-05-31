package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;
import com.leohoc.ets.infrastructure.config.SimulationProperties;
import com.leohoc.ets.infrastructure.config.SimulationPropertiesLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationTest {

    private static final int POPULATION_SIZE = 10;
    private static final int ZERO_INITIAL_INFECTED_PERCENT = 0;
    private static final int ONE_HUNDRED_INITIAL_INFECTED_PERCENT = 100;
    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int INDIVIDUAL_INITIAL_BOUNDARY = 0;
    private static final int INDIVIDUAL_END_BOUNDARY = 10;
    private static final int TOTAL_ITERATIONS = 10;
    private static final int ITERATIONS_PER_DAY = 1;

    private Simulation simulation;
    private SimulationPropertiesLoader simulationPropertiesLoader = mock(SimulationPropertiesLoader.class);

    @Test
    public void testGenerateEntirelyHealthyPopulation() {
        // Given
        when(simulationPropertiesLoader.loadSimulationProperties()).thenReturn(buildSimulationProperties(ZERO_INITIAL_INFECTED_PERCENT));
        when(simulationPropertiesLoader.loadIndividualProperties()).thenReturn(buildIndividualProperties());
        simulation = new Simulation(simulationPropertiesLoader);

        // When
        List<Individual> population = simulation.generatePopulation();

        // Then
        assertEquals(POPULATION_SIZE, population.size());
        assertFalse(population.stream().allMatch(Individual::isInfected));
    }

    @Test
    public void testGenerateEntirelyInfectedPopulation() {
        // Given
        when(simulationPropertiesLoader.loadSimulationProperties()).thenReturn(buildSimulationProperties(ONE_HUNDRED_INITIAL_INFECTED_PERCENT));
        when(simulationPropertiesLoader.loadIndividualProperties()).thenReturn(buildIndividualProperties());
        when(simulationPropertiesLoader.loadIterationsProperties()).thenReturn(buildIterationsProperties());
        simulation = new Simulation(simulationPropertiesLoader);

        // When
        List<Individual> population = simulation.generatePopulation();

        // Then
        assertEquals(POPULATION_SIZE, population.size());
        assertTrue(population.stream().allMatch(Individual::isInfected));
    }

    private SimulationProperties buildSimulationProperties(final int initialInfectedPercent) {
        return new SimulationProperties(POPULATION_SIZE, initialInfectedPercent);
    }

    private SimulationIndividualProperties buildIndividualProperties() {
        return new SimulationIndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                INDIVIDUAL_INITIAL_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_INITIAL_BOUNDARY
        );
    }

    private SimulationIterationsProperties buildIterationsProperties() {
        return new SimulationIterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }
}