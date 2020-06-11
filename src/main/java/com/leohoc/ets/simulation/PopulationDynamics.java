package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;

import java.util.List;

public class PopulationDynamics {

    private final DiseaseBehavior diseaseBehavior;
    private final MovementBehavior movementBehavior;

    public PopulationDynamics(final DiseaseBehavior diseaseBehavior, final MovementBehavior movementBehavior) {
        this.diseaseBehavior = diseaseBehavior;
        this.movementBehavior = movementBehavior;
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
