package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class SimulationCoordinatorTest {

    private static final Integer POINT_A_X = 0;
    private static final Integer POINT_A_Y = 0;

    private final SimulationProperties simulationProperties = mock(SimulationProperties.class);
    private final IterationEvolution iterationEvolution = mock(IterationEvolution.class);
    private final PopulationDynamics populationDynamics = mock(PopulationDynamics.class);
    private final GraphicalEnvironment graphicalEnvironment = mock(GraphicalEnvironment.class);
    private final EpidemicStatistics epidemicStatistics = mock(EpidemicStatistics.class);
    private SimulationCoordinator simulationCoordinator;

    @BeforeEach
    void setup() {
        simulationCoordinator = new SimulationCoordinator(
                simulationProperties,
                iterationEvolution,
                populationDynamics,
                graphicalEnvironment,
                epidemicStatistics
        );
    }

    @Test
    void testStartSimulation() {
        // Given
        final int populationSize = 1;
        final double initialInfectedPercent = 0.0;
        final int currentSimulatedDay = 0;
        final Individual individual = buildIndividual();
        final long waitingTimeInMs = 100;

        // When
        when(simulationProperties.getPopulationSize()).thenReturn(populationSize);
        when(simulationProperties.getInitialInfectedPercent()).thenReturn(initialInfectedPercent);
        when(iterationEvolution.getCurrentSimulatedDay()).thenReturn(currentSimulatedDay);
        when(populationDynamics.generateRandomIndividual(eq(initialInfectedPercent), eq(currentSimulatedDay))).thenReturn(individual);
        when(simulationProperties.isGraphicsEnabled()).thenReturn(Boolean.TRUE);
        when(iterationEvolution.hasSimulationFinished()).thenReturn(Boolean.TRUE);
        simulationCoordinator.startSimulation();

        // Then
        verify(populationDynamics, timeout(waitingTimeInMs)).executeDynamicsIterationOn(eq(Arrays.asList(individual)), eq(currentSimulatedDay));
        verify(iterationEvolution, timeout(waitingTimeInMs)).iterate();
        verify(epidemicStatistics, timeout(waitingTimeInMs)).updateAllStatistics(any());
        verify(graphicalEnvironment, timeout(waitingTimeInMs)).repaint(eq(Arrays.asList(individual)), eq(iterationEvolution), eq(epidemicStatistics));
    }

    private Individual buildIndividual() {
        return new Individual(POINT_A_X, POINT_A_Y, DirectionMovement.STANDING, mock(IndividualProperties.class));
    }
}