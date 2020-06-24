package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.EpidemicProperties;
import com.leohoc.ets.util.RandomUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.leohoc.ets.generators.PropertiesGenerator.generateIndividualProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DiseaseBehaviorTest {

    private static final Integer POINT_A_X = 0;
    private static final Integer POINT_A_Y = 0;
    private static final Integer POINT_B_X = 10;
    private static final Integer POINT_B_Y = 10;
    private static final Integer SIMULATION_START_DAY = 0;
    private static final int ONE_INVOCATION = 1;

    private EpidemicProperties epidemicProperties;
    private DiseaseBehavior diseaseBehavior;
    private HealthSystemResources healthSystemResources;
    private RandomUtil randomUtil;

    @BeforeEach
    void setup() {
        epidemicProperties = mock(EpidemicProperties.class);
        healthSystemResources = mock(HealthSystemResources.class);
        randomUtil = mock(RandomUtil.class);
        diseaseBehavior = new DiseaseBehavior(epidemicProperties, healthSystemResources, randomUtil);
    }

    @Test
    void testUpdateHealthConditionGivenUninfectedIndividual() {
        // Given
        Individual individual = buildIndividual();

        // When
        int currentSimulatedDay = 15;
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionGivenInfectedIndividualBeforeReachingRecoveryTime() {
        // Given
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        int currentSimulatedDay = 5;
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionFromInfectedToRecovered() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(5.0);
        when(epidemicProperties.getDeathPercentage()).thenReturn(4.99);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.RECOVERED, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionFromHospitalizedToRecovered() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotHospitalized(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(10.0);
        when(epidemicProperties.getDeathPercentage()).thenReturn(5.0);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        verify(healthSystemResources, times(ONE_INVOCATION)).releaseICUBed();
        assertEquals(HealthStatus.RECOVERED, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionFromInfectedToDead() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(4.0);
        when(epidemicProperties.getDeathPercentage()).thenReturn(5.0);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionFromHospitalizedToDead() {
        // Given
        Integer currentSimulatedDay = 15;
        Individual individual = buildIndividual();
        individual.gotHospitalized(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(4.0);
        when(epidemicProperties.getDeathPercentage()).thenReturn(5.0);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        verify(healthSystemResources, times(ONE_INVOCATION)).releaseICUBed();
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionToHospitalized() {
        // Given
        int currentSimulatedDay = 7;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(4.0);
        when(epidemicProperties.getHospitalizationPercentage()).thenReturn(5.0);
        when(healthSystemResources.hasAvailableICUBed()).thenReturn(Boolean.TRUE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        verify(healthSystemResources, times(ONE_INVOCATION)).fillICUBed();
        assertEquals(HealthStatus.HOSPITALIZED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionToDeadDueToICUBedUnavailability() {
        // Given
        Integer currentSimulatedDay = 7;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(4.0);
        when(epidemicProperties.getHospitalizationPercentage()).thenReturn(5.0);
        when(healthSystemResources.hasAvailableICUBed()).thenReturn(Boolean.FALSE);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(currentSimulatedDay, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testUpdateHealthConditionShouldNotBeHospitalized() {
        // Given
        int currentSimulatedDay = 7;
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);

        // When
        when(epidemicProperties.getHospitalizationDays()).thenReturn(7);
        when(epidemicProperties.getRecoveryDays()).thenReturn(14);
        when(randomUtil.generatePercentWithTwoDigitsScale()).thenReturn(6.0);
        when(epidemicProperties.getHospitalizationPercentage()).thenReturn(5.0);
        diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testInteractionBetweenHealthyIndividualAndInfectedPasserby() {
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
    void testInteractionBetweenRecoveredIndividualAndInfectedPasserby() {
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
    void testInteractionBetweenDeadIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        individual.died(SIMULATION_START_DAY);
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        int currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.DEAD, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testInteractionBetweenInfectedIndividualAndInfectedPasserby() {
        // Given
        Individual individual = buildIndividual();
        individual.gotInfected(SIMULATION_START_DAY);
        Individual passerby = buildIndividual();
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        int currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.INFECTED, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testInteractionBetweenIndividualAndHealthyPasserby() {
        // Given
        Individual individual = buildIndividual();
        Individual passerby = buildIndividual();

        // When
        int currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    @Test
    void testInteractionBetweenHealthyIndividualAndInfectedPasserbyWithoutCrossingWays() {
        // Given
        Individual individual = buildIndividual();
        Individual passerby = buildIndividual(POINT_B_X, POINT_B_Y);
        passerby.gotInfected(SIMULATION_START_DAY);

        // When
        int currentSimulatedDay = 1;
        diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);

        // Then
        assertEquals(HealthStatus.NORMAL, individual.getHealthCondition().getHealthStatus());
        assertEquals(SIMULATION_START_DAY, individual.getHealthCondition().getStartDay());
    }

    private Individual buildIndividual() {
        return buildIndividual(POINT_A_X, POINT_A_Y);
    }

    private Individual buildIndividual(final Integer x, final Integer y) {
        return new Individual(x, y, DirectionMovement.STANDING, generateIndividualProperties());
    }
}