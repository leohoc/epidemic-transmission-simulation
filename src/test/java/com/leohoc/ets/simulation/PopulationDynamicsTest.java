package com.leohoc.ets.simulation;

import com.leohoc.ets.generators.PropertiesGenerator;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        PopulationDynamics populationDynamics = spy(new PopulationDynamics(diseaseBehavior));
        List<Individual> population = buildPopulation();
        int currentSimulatedDay = 1;

        // When
        EpidemicStatistics statistics = populationDynamics.executeDynamicsIterationOn(population, currentSimulatedDay);

        // Then
        verify(populationDynamics, times(ONE_INVOCATION)).executeMovementBehaviorOn(eq(population.get(0)));
        verify(populationDynamics, times(ONE_INVOCATION)).executeMovementBehaviorOn(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(currentSimulatedDay));
        verify(populationDynamics, times(ONE_INVOCATION)).executeIndividualInteractionWithPopulation(eq(population.get(0)), eq(population), eq(currentSimulatedDay));
        verify(populationDynamics, times(ONE_INVOCATION)).executeIndividualInteractionWithPopulation(eq(population.get(1)), eq(population), eq(currentSimulatedDay));
        assertEquals(population.size(), statistics.getCurrentNormalCount().intValue());
    }

    @Test
    public void testExecuteMovementBehaviorOnIndividual() {
        // Given
        Individual individual = Mockito.spy(buildIndividual());
        PopulationDynamics populationDynamics = new PopulationDynamics(mock(DiseaseBehavior.class));

        // When
        populationDynamics.executeMovementBehaviorOn(individual);

        // Then
        verify(individual, times(ONE_INVOCATION)).move();
    }

    @Test
    public void testExecuteIndividualInteractionWithPopulation() {
        // Given
        DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior);
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
        return Individual.randomIndividual(PropertiesGenerator.generateIndividualProperties());
    }
}