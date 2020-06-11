package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.generators.PropertiesGenerator;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PopulationDynamicsTest {

    private static final int ONE_INVOCATION = 1;

    @Test
    public void testExecuteDynamicsIteration() {
        // Given
        DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
        MovementBehavior movementBehavior = mock(MovementBehavior.class);
        PopulationDynamics populationDynamics = spy(new PopulationDynamics(diseaseBehavior, movementBehavior));
        List<Individual> population = buildPopulation();
        int currentSimulatedDay = 1;

        // When
        EpidemicStatistics statistics = populationDynamics.executeDynamicsIterationOn(population, currentSimulatedDay);

        // Then
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(0)));
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(currentSimulatedDay));
        verify(populationDynamics, times(ONE_INVOCATION)).executeIndividualInteractionWithPopulation(eq(population.get(0)), eq(population), eq(currentSimulatedDay));
        verify(populationDynamics, times(ONE_INVOCATION)).executeIndividualInteractionWithPopulation(eq(population.get(1)), eq(population), eq(currentSimulatedDay));
        assertEquals(population.size(), statistics.getCurrentNormalCount().intValue());
    }

    @Test
    public void testExecuteIndividualInteractionWithPopulation() {
        // Given
        DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
        MovementBehavior movementBehavior = mock(MovementBehavior.class);
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior);
        List<Individual> population = buildPopulation();
        int currentSimulatedDay = 0;

        // When
        populationDynamics.executeIndividualInteractionWithPopulation(population.get(0), population, currentSimulatedDay);

        // Then
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(0)), eq(population.get(1)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(0)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(1)), eq(currentSimulatedDay));
    }

    private List<Individual> buildPopulation() {
        return Arrays.asList(buildIndividual(), buildIndividual());
    }

    private Individual buildIndividual() {
        final int x = 0;
        final int y = 0;
        final DirectionMovement directionMovement = DirectionMovement.STANDING;
        return new Individual(x, y, directionMovement, PropertiesGenerator.generateIndividualProperties());
    }
}