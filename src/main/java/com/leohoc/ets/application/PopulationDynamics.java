package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;

import java.util.List;

public class PopulationDynamics {

    private final DiseaseBehavior diseaseBehavior;

    public PopulationDynamics(final SimulationEpidemicProperties epidemicProperties) {
        this.diseaseBehavior = new DiseaseBehavior(epidemicProperties);
    }

    public EpidemicStatistics executeDynamicsIterationOn(final List<Individual> population, final int currentSimulatedDay){

        EpidemicStatistics iterationStatistics = new EpidemicStatistics();

        for (Individual individual : population) {
            individual.move();
            diseaseBehavior.updateHealthCondition(individual, currentSimulatedDay);
            executeIndividualInteractionWithPopulation(individual, population, currentSimulatedDay);
            iterationStatistics.updateStatistics(individual.getHealthStatus());
        }
        return iterationStatistics;
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
