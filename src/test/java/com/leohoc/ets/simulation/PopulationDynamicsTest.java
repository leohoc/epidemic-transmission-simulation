package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
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
    private static final int CURRENT_SIMULATED_DAY = 1;

    private final DiseaseBehavior diseaseBehavior = mock(DiseaseBehavior.class);
    private final MovementBehavior movementBehavior = mock(MovementBehavior.class);
    private final IndividualProperties individualProperties = mock(IndividualProperties.class);
    private final RandomUtil randomUtil = mock(RandomUtil.class);

    @Test
    void testGenerateRandomUninfectedIndividual() {
        // Given
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final double initialInfectedPercent = 5.0;

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(6.0);
        Individual individual = populationDynamics.generateRandomIndividual(initialInfectedPercent, CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthStatus());
    }

    @Test
    void testGenerateRandomInfectedIndividual() {
        // Given
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final double initialInfectedPercent = 5.0;

        // When
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(4.9);
        Individual individual = populationDynamics.generateRandomIndividual(initialInfectedPercent, CURRENT_SIMULATED_DAY);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthStatus());
    }

    @Test
    void testExecuteDynamicsIteration() {
        // Given
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final List<Individual> population = buildPopulation();

        // When
        EpidemicStatistics epidemicStatistics = populationDynamics.executeDynamicsIterationOn(population, CURRENT_SIMULATED_DAY);

        // Then
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(0)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(0)), eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(1)), eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(0)), eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        assertEquals(population.size(), epidemicStatistics.getCurrentNormalCount().intValue());
        assertEquals(ZERO_COUNT, epidemicStatistics.getTotalHospitalizedCount().intValue());
    }

    @Test
    void testExecuteDynamicsIterationWithHospitalization() {
        // Given
        PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior, individualProperties, randomUtil);
        final List<Individual> population = buildPopulation();

        // When
        doAnswer(invocation -> {
            final int individualIndex = 0;
            invocation.getArgument(individualIndex, Individual.class).gotHospitalized(CURRENT_SIMULATED_DAY);
            return null;
        }).when(diseaseBehavior).updateHealthCondition(eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        EpidemicStatistics epidemicStatistics = populationDynamics.executeDynamicsIterationOn(population, CURRENT_SIMULATED_DAY);

        // Then
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(0)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(0)), eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        verify(movementBehavior, times(ONE_INVOCATION)).adjustDirectionOf(eq(population.get(1)));
        verify(diseaseBehavior, times(ONE_INVOCATION)).updateHealthCondition(eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, times(ONE_INVOCATION)).interactionBetween(eq(population.get(1)), eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(0)), eq(population.get(0)), eq(CURRENT_SIMULATED_DAY));
        verify(diseaseBehavior, never()).interactionBetween(eq(population.get(1)), eq(population.get(1)), eq(CURRENT_SIMULATED_DAY));
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
        return new Individual(x, y, directionMovement, individualProperties);
    }
}