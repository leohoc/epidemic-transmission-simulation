package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;

import java.util.List;

public class PopulationDynamics {

    private final DiseaseBehavior diseaseBehavior;

    public PopulationDynamics(final DiseaseBehavior diseaseBehavior) {
        this.diseaseBehavior = diseaseBehavior;
    }

    public EpidemicStatistics executeDynamicsIterationOn(final List<Individual> population, final int currentSimulatedDay){

        EpidemicStatistics iterationStatistics = new EpidemicStatistics();

        for (Individual individual : population) {
            executeMovementBehaviorOn(individual);
            diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);
            executeIndividualInteractionWithPopulation(individual, population, currentSimulatedDay);
            iterationStatistics.updateStatistics(individual.getHealthStatus());
        }
        return iterationStatistics;
    }

    protected void executeMovementBehaviorOn(final Individual individual) {
        individual.move();
    }

    protected void executeIndividualInteractionWithPopulation(final Individual individual,
                                                              final List<Individual> population,
                                                              final int currentSimulatedDay) {
        for (Individual passerby : population) {
            if (!individual.equals(passerby)) {
                diseaseBehavior.interactionBetween(individual, passerby, currentSimulatedDay);
            }
        }
    }
}
