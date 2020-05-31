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
        if (individual.isInfected() && reachedRecoveryTime(individual.getHealthCondition().getStartDay(), currentSimulatedDay)) {
            if (hasDied()) {
                individual.died(currentSimulatedDay);
            } else {
                individual.recovered(currentSimulatedDay);
            }
        }
    }

    public void interactionBetween(final Individual individual, final Individual passerby, final int currentSimulatedDay) {
        if (individual.crossedWayWith(passerby) && passerby.isInfected() && individual.getHealthCondition().hasNoAntibodies()) {
            individual.gotInfected(currentSimulatedDay);
        }
    }

    private boolean reachedRecoveryTime(final int diseaseStartDay, final long currentSimulatedDay) {
        return (currentSimulatedDay - diseaseStartDay) > epidemicProperties.getRecoveryDays();
    }

    protected boolean hasDied() {
        return RandomUtil.generatePercent() < epidemicProperties.getDeathPercentage();
    }

}
