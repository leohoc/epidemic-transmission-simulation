package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationCoordinatorTest {

    private static final int ONE_INVOCATION = 1;
    private static final int TWO_INVOCATIONS = 2;

    private final SimulationProperties simulationProperties = mock(SimulationProperties.class);
    private final IndividualProperties individualProperties = mock(IndividualProperties.class);
    private final IterationEvolution iterationEvolution = mock(IterationEvolution.class);
    private final PopulationDynamics populationDynamics = mock(PopulationDynamics.class);
    private final GraphicalEnvironment graphicalEnvironment = mock(GraphicalEnvironment.class);
    private final MovementBehavior movementBehavior = mock(MovementBehavior.class);
    private final EpidemicStatistics epidemicStatistics = mock(EpidemicStatistics.class);
    private SimulationCoordinator simulationCoordinator;

    @BeforeEach
    void setup() {
        simulationCoordinator = new SimulationCoordinator(
                simulationProperties,
                individualProperties,
                iterationEvolution,
                populationDynamics,
                graphicalEnvironment,
                movementBehavior,
                epidemicStatistics
        );
    }

    @Test
    void testGenerateEntirelyHealthyPopulation() {
        // Given
        final int populationSize = 10;
        final int mapStartPoint = 0;
        final double initialInfectedPercent = 0.0;

        // When
        when(simulationProperties.getPopulationSize()).thenReturn(populationSize);
        when(movementBehavior.generateRandomXPointWithinMapBoundaries()).thenReturn(mapStartPoint);
        when(movementBehavior.generateRandomYPointWithinMapBoundaries()).thenReturn(mapStartPoint);
        when(movementBehavior.newRandomDirection()).thenReturn(DirectionMovement.STANDING);
        when(simulationProperties.getInitialInfectedPercent()).thenReturn(initialInfectedPercent);
        List<Individual> population = simulationCoordinator.generatePopulation();

        // Then
        assertEquals(populationSize, population.size());
        assertFalse(population.stream().allMatch(Individual::isInfected));
    }

    @Test
    void testGenerateEntirelyInfectedPopulation() {
        // Given
        final int populationSize = 10;
        final int mapStartPoint = 0;
        final double initialInfectedPercent = 100.0;

        // When
        when(simulationProperties.getPopulationSize()).thenReturn(populationSize);
        when(movementBehavior.generateRandomXPointWithinMapBoundaries()).thenReturn(mapStartPoint);
        when(movementBehavior.generateRandomYPointWithinMapBoundaries()).thenReturn(mapStartPoint);
        when(movementBehavior.newRandomDirection()).thenReturn(DirectionMovement.STANDING);
        when(simulationProperties.getInitialInfectedPercent()).thenReturn(initialInfectedPercent);
        List<Individual> population = simulationCoordinator.generatePopulation();

        // Then
        assertEquals(populationSize, population.size());
        assertTrue(population.stream().allMatch(Individual::isInfected));
    }

    @Test
    void testRunSimulation() {
        // Given
        final EpidemicStatistics iterationEpidemicStatistics = new EpidemicStatistics();

        // When
        when(populationDynamics.executeDynamicsIterationOn(anyList(), anyInt())).thenReturn(iterationEpidemicStatistics);
        when(iterationEvolution.hasSimulationFinished()).thenReturn(Boolean.TRUE);
        simulationCoordinator.runSimulation();

        // Then
        verify(iterationEvolution, times(ONE_INVOCATION)).iterate();
        verify(epidemicStatistics, times(ONE_INVOCATION)).updateAllStatistics(eq(iterationEpidemicStatistics));
        verify(iterationEvolution, times(TWO_INVOCATIONS)).getCurrentSimulatedDay();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getCurrentNormalCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getCurrentInfectedCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getCurrentHospitalizedCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getTotalInfectedCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getTotalHospitalizedCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getTotalRecoveredCount();
        verify(epidemicStatistics, times(ONE_INVOCATION)).getTotalDeadCount();
    }
}