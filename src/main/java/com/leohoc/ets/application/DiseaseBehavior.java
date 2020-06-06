package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.util.RandomUtil;

public class DiseaseBehavior {

    private final SimulationEpidemicProperties epidemicProperties;
    private final HealthSystemResources healthSystemResources;

    public DiseaseBehavior(final SimulationEpidemicProperties epidemicProperties, final int availableUCIBeds) {
        this.epidemicProperties = epidemicProperties;
        this.healthSystemResources = new HealthSystemResources(availableUCIBeds);
    }

    public void updateHealthCondition(final Individual individual, final int currentSimulatedDay) {
        if (individual.isInfected()) {
            checkHospitalizationNeeds(individual, currentSimulatedDay);
            checkRecovering(individual, currentSimulatedDay);
        }
    }

    public void interactionBetween(final Individual individual, final Individual passerby, final int currentSimulatedDay) {
        if (individual.crossedWayWith(passerby) && passerby.isInfected() && !individual.getHealthStatus().hasAntibodies()) {
            individual.gotInfected(currentSimulatedDay);
        }
    }

    private void checkRecovering(final Individual individual, final int currentSimulatedDay) {
        if (reachedRecoveryTime(individual.getHealthCondition().getStartDay(), currentSimulatedDay)) {
            if (individual.isHospitalized()) {
                healthSystemResources.releaseUCIBed();
            }
            if (hasDied()) {
                individual.died(currentSimulatedDay);
            } else {
                individual.recovered(currentSimulatedDay);
            }
        }
    }

    private void checkHospitalizationNeeds(final Individual individual, final int currentSimulatedDay) {
        final int individualInfectionStartDay = individual.getHealthCondition().getStartDay();
        if (!individual.isHospitalized() && reachedHospitalizationTime(individualInfectionStartDay, currentSimulatedDay) && shouldBeHospitalized()) {
            if (healthSystemResources.hasAvailableUCIBed()) {
                healthSystemResources.fillUCIBed();
                individual.gotHospitalized(individualInfectionStartDay);
            } else {
                individual.died(currentSimulatedDay);
            }
        }
    }

    private boolean reachedHospitalizationTime(final int individualInfectionDay, final int currentSimulatedDay) {
        return (currentSimulatedDay - individualInfectionDay) == epidemicProperties.getHospitalizationDays();
    }

    protected boolean shouldBeHospitalized() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < epidemicProperties.getHospitalizationPercentage();
    }

    private boolean reachedRecoveryTime(final int individualInfectionDay, final long currentSimulatedDay) {
        return (currentSimulatedDay - individualInfectionDay) > epidemicProperties.getRecoveryDays();
    }

    protected boolean hasDied() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < epidemicProperties.getDeathPercentage();
    }
}
