package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationHealthSystemCapacityProperties;
import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;
import com.leohoc.ets.infrastructure.config.SimulationProperties;
import com.leohoc.ets.infrastructure.config.SimulationPropertiesLoader;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.leohoc.ets.builders.PropertiesBuilder.buildIndividualProperties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationTest {

    private static final int POPULATION_SIZE = 10;
    private static final int ZERO_INITIAL_INFECTED_PERCENT = 0;
    private static final int ONE_HUNDRED_INITIAL_INFECTED_PERCENT = 100;
    private static final int TOTAL_ITERATIONS = 10;
    private static final int ITERATIONS_PER_DAY = 1;
    private static final int AVAILABLE_BEDS = 2;

    private Simulation simulation;
    private SimulationPropertiesLoader simulationPropertiesLoader = mock(SimulationPropertiesLoader.class);

    @Test
    public void testGenerateEntirelyHealthyPopulation() {
        // Given
        when(simulationPropertiesLoader.loadHealthSystemCapacityProperties()).thenReturn(buildHealthSystemProperties());
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
        when(simulationPropertiesLoader.loadHealthSystemCapacityProperties()).thenReturn(buildHealthSystemProperties());
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

    @Test
    public void testRunSimulation() {
        // Given
        when(simulationPropertiesLoader.loadHealthSystemCapacityProperties()).thenReturn(buildHealthSystemProperties());
        when(simulationPropertiesLoader.loadIterationsProperties()).thenReturn(buildIterationsProperties());
        simulation = spy(new Simulation(simulationPropertiesLoader));

        // When
        simulation.runSimulation();

        // Then
        verify(simulation, Mockito.times(TOTAL_ITERATIONS)).runDynamicsIteration();
    }

    private SimulationProperties buildSimulationProperties(final int initialInfectedPercent) {
        return new SimulationProperties(POPULATION_SIZE, initialInfectedPercent);
    }

    private SimulationIterationsProperties buildIterationsProperties() {
        return new SimulationIterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }

    private SimulationHealthSystemCapacityProperties buildHealthSystemProperties() {
        return new SimulationHealthSystemCapacityProperties(AVAILABLE_BEDS);
    }
}