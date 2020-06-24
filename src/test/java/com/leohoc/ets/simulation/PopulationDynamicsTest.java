package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.generators.PropertiesGenerator;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.IndividualProperties;
import com.leohoc.ets.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PopulationDynamicsTest {

    private static final int ONE_INVOCATION = 1;
    private static final int ZERO_COUNT = 0;
    private static final int ONE_COUNT = 1;

    @Test
    void testExecuteDynamicsIteration() {
        // Given
        DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
        MovementBehavior movementBehavior = mock(MovementBehavior.class);
        IndividualProperties individualProperties = mock(IndividualProperties.class);
        RandomUtil randomUtil = mock(RandomUtil.class);
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final List<Individual> population = buildPopulation();
        final int currentSimulatedDay = 1;

        // When
        EpidemicStatistics epidemicStatistics = populationDynamics.executeDynamicsIterationOn(population, currentSimulatedDay);

        // Then
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(0)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(0)), eq(population.get(1)), eq(currentSimulatedDay));
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(1)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(0)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(1)), eq(currentSimulatedDay));
        assertEquals(population.size(), epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalHospitalizedCount().intValue());
    }

    @Test
    void testExecuteDynamicsIterationWithHospitalization() {
        // Given
        DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
        MovementBehavior movementBehavior = mock(MovementBehavior.class);
        IndividualProperties individualProperties = mock(IndividualProperties.class);
        RandomUtil randomUtil = mock(RandomUtil.class);
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final List<Individual> population = buildPopulation();
        final int currentSimulatedDay = 1;

        // When
        doAnswer(invocation -> {
            final int individualIndex = 0;
            invocation.getArgument(individualIndex, Individual.class).gotHospitalized(currentSimulatedDay);
            return null;
        }).when(diseaseBehavior).updateHealthCondition(eq(population.get(1)), eq(currentSimulatedDay));
        EpidemicStatistics epidemicStatistics = populationDynamics.executeDynamicsIterationOn(population, currentSimulatedDay);

        // Then
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(0)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(0)), eq(population.get(1)), eq(currentSimulatedDay));
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(currentSimulatedDay));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(1)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(0)), eq(population.get(0)), eq(currentSimulatedDay));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(1)), eq(currentSimulatedDay));
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getCurrentHospitalizedCount().intValue());
        assertEquals(ONE_COUNT, epidemicStatistics.getTotalHospitalizedCount().intValue());
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