package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.util.RandomUtil;

import static com.leohoc.ets.domain.enums.DirectionMovement.*;
import static com.leohoc.ets.domain.enums.HealthStatus.*;
import static com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties.getRecoveryDays;
import static com.leohoc.ets.infrastructure.config.SimulationIndividualProperties.getDirectionChangeProbability;

public class Individual {

    public static final int HUNDRED_PERCENT = 100;

    private int x;
    private int y;
    private DirectionMovement directionMovement;
    private HealthCondition healthCondition;

    public static Individual randomIndividual() {
        return new Individual(
                RandomUtil.generateIntBetween(SimulationIndividualProperties.getLeftBoundary(), SimulationIndividualProperties.getRightBoundary()),
                RandomUtil.generateIntBetween(SimulationIndividualProperties.getUpBoundary(), SimulationIndividualProperties.getDownBoundary()),
                randomDirectionMovement()
        );
    }

    public Individual(final int x, final int y, final DirectionMovement directionMovement) {
        this.x = x;
        this.y = y;
        this.directionMovement = directionMovement;
        this.healthCondition = new HealthCondition(NORMAL);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return SimulationIndividualProperties.getIndividualWidth();
    }

    public int getHeight() {
        return SimulationIndividualProperties.getIndividualHeight();
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public HealthStatus getHealthStatus() {
        return healthCondition.getHealthStatus();
    }

    public void move() {
        adjustDirection();
        x += directionMovement.xAxisMovement();
        y += directionMovement.yAxisMovement();
    }

    public void gotInfected(long currentDay) {
        healthCondition = new HealthCondition(INFECTED, currentDay);
    }

    public void interactionWith(Individual passerby, long currentDay) {
        if (crossedWayWith(passerby) && passerby.isInfected() && !this.getHealthCondition().hasAntibodies()) {
            this.gotInfected(currentDay);
        }
    }

    public void updateHealthCondition(long currentDay) {
        if (hasRecovered(getRecoveryDays(), currentDay)) {
            healthCondition = new HealthCondition(RECOVERED, currentDay);
        }
    }

    private void adjustDirection() {
        changeDirectionByReachingMapBoundaries();
        if (shouldChangeDirectionRandomly()) {
            changeDirection();
        }
    }

    private void changeDirectionByReachingMapBoundaries() {

        if (reachedLeftBoundary()) {
            directionMovement = RIGHT;
        }

        if (reachedRightBoundary()) {
            directionMovement = LEFT;
        }

        if (reachedUpBoundary()) {
            directionMovement = DOWN;
        }

        if (reachedDownBoundary()) {
            directionMovement = UP;
        }
    }

    private boolean reachedLeftBoundary() {
        return x <= SimulationIndividualProperties.getLeftBoundary();
    }

    private boolean reachedRightBoundary() {
        return x >= SimulationIndividualProperties.getRightBoundary();
    }

    private boolean reachedUpBoundary() {
        return y <= SimulationIndividualProperties.getUpBoundary();
    }

    private boolean reachedDownBoundary() {
        return y >= SimulationIndividualProperties.getDownBoundary();
    }

    private boolean shouldChangeDirectionRandomly() {
        return RandomUtil.generateIntLessThan(HUNDRED_PERCENT) < getDirectionChangeProbability();
    }

    private void changeDirection() {
        this.directionMovement = randomDirectionMovement();
    }

    private boolean crossedWayWith(Individual passerby) {
        return (passerby.getX() >= x) && (passerby.getX() < x + getWidth()) && (passerby.getY() >= y) && (passerby.getY() < y + getHeight());
    }

    private boolean hasRecovered(final int recoverAverageDays, final long currentDay) {
        return isInfected() && reachedRecoverTime(recoverAverageDays, currentDay);
    }

    private boolean isInfected() {
        return healthCondition.getHealthStatus().equals(HealthStatus.INFECTED);
    }

    private boolean reachedRecoverTime(int recoverAverageDays, long currentDay) {
        return (currentDay - healthCondition.getStartDay()) > recoverAverageDays;
    }
}
