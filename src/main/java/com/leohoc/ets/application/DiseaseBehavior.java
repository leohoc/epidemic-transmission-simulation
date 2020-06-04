package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.util.RandomUtil;

public class DiseaseBehavior {

    private final SimulationEpidemicProperties epidemicProperties;

    public DiseaseBehavior(final SimulationEpidemicProperties epidemicProperties) {
        this.epidemicProperties = epidemicProperties;
    }

    public void updateHealthCondition(final Individual individual, final int currentSimulatedDay) {
        if (individual.isInfected()) {

            final int individualInfectionDay = individual.getHealthCondition().getStartDay();

            if (reachedHospitalizationTime(individualInfectionDay, currentSimulatedDay) && shouldBeHospitalized()) {
                individual.gotHospitalized(individualInfectionDay);
            }

            if (reachedRecoveryTime(individualInfectionDay, currentSimulatedDay)) {
                if (hasDied()) {
                    individual.died(currentSimulatedDay);
                } else {
                    individual.recovered(currentSimulatedDay);
                }
            }
        }
    }

    public void interactionBetween(final Individual individual, final Individual passerby, final int currentSimulatedDay) {
        if (individual.crossedWayWith(passerby) && passerby.isInfected() && individual.getHealthCondition().hasNoAntibodies()) {
            individual.gotInfected(currentSimulatedDay);
        }
    }

    private boolean reachedHospitalizationTime(final int individualInfectionDay, final int currentSimulatedDay) {
        return (currentSimulatedDay - individualInfectionDay) == epidemicProperties.getHospitalizationDays();
    }

    private boolean shouldBeHospitalized() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < epidemicProperties.getHospitalizationPercentage();
    }

    private boolean reachedRecoveryTime(final int individualInfectionDay, final long currentSimulatedDay) {
        return (currentSimulatedDay - individualInfectionDay) > epidemicProperties.getRecoveryDays();
    }

    protected boolean hasDied() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < epidemicProperties.getDeathPercentage();
    }

}
