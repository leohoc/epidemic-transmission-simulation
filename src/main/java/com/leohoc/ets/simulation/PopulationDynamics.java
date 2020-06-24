package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.IndividualProperties;
import com.leohoc.ets.util.RandomUtil;

import java.util.List;

public class PopulationDynamics {

    private final DiseaseBehavior diseaseBehavior;
    private final MovementBehavior movementBehavior;
    private final IndividualProperties individualProperties;
    private final RandomUtil randomUtil;

    public PopulationDynamics(final DiseaseBehavior diseaseBehavior,
                              final MovementBehavior movementBehavior,
                              final IndividualProperties individualProperties,
                              final RandomUtil randomUtil) {
        this.diseaseBehavior = diseaseBehavior;
        this.movementBehavior = movementBehavior;
        this.individualProperties = individualProperties;
        this.randomUtil = randomUtil;
    }

    public Individual generateRandomIndividual(final double initialInfectedPercent, final int currentSimulatedDay) {
        Individual individual = randomIndividual();
        if (shouldGotInfected(initialInfectedPercent)) {
            individual.gotInfected(currentSimulatedDay);
        }
        return individual;
    }

    private Individual randomIndividual() {
        return new Individual(
                movementBehavior.generateRandomXPointWithinMapBoundaries(),
                movementBehavior.generateRandomYPointWithinMapBoundaries(),
                movementBehavior.newRandomDirection(),
                individualProperties
        );
    }

    private boolean shouldGotInfected(final double initialInfectedPercent) {
        return randomUtil.generatePercentWithTwoDigitsScale() < initialInfectedPercent;
    }

    public EpidemicStatistics executeDynamicsIterationOn(final List<Individual> population, final int currentSimulatedDay){

        EpidemicStatistics iterationStatistics = new EpidemicStatistics();

        for (Individual individual : population) {
            final boolean individualStartedIterationHospitalized = individual.isHospitalized();
            movementBehavior.adjustDirectionOf(individual);
            diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);
            executeIndividualInteractionWithPopulation(individual, population, currentSimulatedDay);
            updateIterationStatistics(iterationStatistics, individual, individualStartedIterationHospitalized);
        }
        return iterationStatistics;
    }

    private void updateIterationStatistics(final EpidemicStatistics iterationStatistics,
                                           final Individual individual,
                                           final boolean individualStartedIterationHospitalized) {
        iterationStatistics.updateStatistics(individual.getHealthStatus());
        if (!individualStartedIterationHospitalized && individual.isHospitalized()) {
            iterationStatistics.increaseHospitalizedCount();
        }
    }

    private void executeIndividualInteractionWithPopulation(final Individual individual,
                                                              final List<Individual> population,
                                                              final int currentSimulatedDay) {
        for (Individual passerby : population) {
            if (!individual.equals(passerby)) {
                diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);
            }
        }
    }
}
