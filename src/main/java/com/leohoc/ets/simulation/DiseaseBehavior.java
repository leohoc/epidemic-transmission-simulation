package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.EpidemicProperties;
import com.leohoc.ets.util.RandomUtil;

public class DiseaseBehavior {

    private final EpidemicProperties epidemicProperties;
    private final HealthSystemResources healthSystemResources;

    public DiseaseBehavior(final EpidemicProperties epidemicProperties, final HealthSystemResources healthSystemResources) {
        this.epidemicProperties = epidemicProperties;
        this.healthSystemResources = healthSystemResources;
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
                healthSystemResources.releaseICUBed();
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

        if (!individual.getHealthCondition().isHospitalizationNeedVerified() && reachedHospitalizationTime(individualInfectionStartDay, currentSimulatedDay)) {
            individual.getHealthCondition().hospitalizationNeedVerified();
            if (!individual.isHospitalized() && shouldBeHospitalized()) {
                sendToICU(individual, currentSimulatedDay, individualInfectionStartDay);
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

    private void sendToICU(Individual individual, int currentSimulatedDay, int individualInfectionStartDay) {
        if (healthSystemResources.hasAvailableICUBed()) {
            healthSystemResources.fillICUBed();
            individual.gotHospitalized(individualInfectionStartDay);
        } else {
            individual.died(currentSimulatedDay);
        }
    }
}
