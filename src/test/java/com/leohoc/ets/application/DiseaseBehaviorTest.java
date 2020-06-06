package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.leohoc.ets.builders.PropertiesBuilder.buildEpidemicProperties;
import static com.leohoc.ets.builders.PropertiesBuilder.buildIndividualProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class DiseaseBehaviorTest {

    private static final Integer POINT_A_X = 0;
    private static final Integer POINT_A_Y = 0;
    private static final Integer POINT_B_X = 10;
    private static final Integer POINT_B_Y = 10;
    private static final Integer SIMULATION_START_DAY = 0;

    private DiseaseBehavior diseaseBehavior;

    @BeforeEach
    public void setup() {
        diseaseBehavior = spy(new DiseaseBehavior(buildEpidemicProperties()));
    }

    @Test
    public void testUpdateHealthConditionGivenUninfectedIndividual() {
        // Given
        Individual individual = buildIndividual();

        // When
        Integer currentSimulatedDay = 15;
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testUpdateHealthConditionBeforeReachingRecoveryTime() {
        // Given
        Individual individual = buildIndividual();

        // When
        Integer currentSimulatedDay = 5;
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testUpdateHealthConditionToRecovered() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(diseaseBehavior.hasDied()).thenReturn(Boolean.FALSE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.RECOVERED, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testUpdateHealthConditionToDead() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(diseaseBehavior.hasDied()).thenReturn(Boolean.TRUE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testUpdateHealthConditionToHospitalized() {
        // Given
        Integer currentSimulatedDay = 7;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(diseaseBehavior.shouldBeHospitalized()).thenReturn(Boolean.TRUE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.HOSPITALIZED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testUpdateHealthConditionShouldNotBeHospitalized() {
        // Given
        Integer currentSimulatedDay = 7;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(diseaseBehavior.shouldBeHospitalized()).thenReturn(Boolean.FALSE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenHealthyIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        Integer currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenRecoveredIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        individual.recovered(SIMULATION_START_DAY);
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        int currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.RECOVERED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenDeadIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        individual.died(SIMULATION_START_DAY);
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        Integer currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenInfectedIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        Integer currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenIndividualAndHealthyPasserby() {
        // Given
        Individual individual = buildIndividual();
        Individual passerby = buildIndividual();

        // When
        Integer currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    public void testInteractionBetweenHealthyIndividualAndInfectedPasserbyWithoutCrossingWays() {
        // Given
        Individual individual = buildIndividual();
        Individual passerby = buildIndividual(POINT_B_X, POINT_B_Y);
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        Integer currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    private Individual buildIndividual() {
        return buildIndividual(POINT_A_X, POINT_A_Y);
    }

    private Individual buildIndividual(final Integer x, final Integer y) {
        return new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
    }
}