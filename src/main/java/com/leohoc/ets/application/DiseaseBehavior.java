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
        if (individual.isInfected() && reachedRecoverTime(individual.getHealthCondition().getStartDay(), currentSimulatedDay)) {
            if (hasDied()) {
                individual.died(currentSimulatedDay);
            } else {
                individual.recovered(currentSimulatedDay);
            }
        }
    }

    private boolean reachedRecoverTime(int diseaseStartDay, long currentSimulatedDay) {
        return (currentSimulatedDay - diseaseStartDay) > epidemicProperties.getRecoveryDays();
    }

    private boolean hasDied() {
        return RandomUtil.generatePercent() <= epidemicProperties.getDeathPercentage();
    }

}
