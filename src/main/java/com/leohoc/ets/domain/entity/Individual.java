package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.util.RandomUtil;

import static com.leohoc.ets.domain.enums.DirectionMovement.*;
import static com.leohoc.ets.domain.enums.HealthStatus.*;

public class Individual {

    private int x;
    private int y;
    private DirectionMovement directionMovement;
    private HealthCondition healthCondition;
    private SimulationIndividualProperties individualProperties;

    public static Individual randomIndividual(SimulationIndividualProperties individualProperties) {
        return new Individual(
                RandomUtil.generateIntBetween(individualProperties.getLeftBoundary(), individualProperties.getRightBoundary()),
                RandomUtil.generateIntBetween(individualProperties.getUpBoundary(), individualProperties.getDownBoundary()),
                randomDirectionMovement(),
                individualProperties
        );
    }

    public Individual(final int x, final int y, final DirectionMovement directionMovement, SimulationIndividualProperties individualProperties) {
        this.x = x;
        this.y = y;
        this.directionMovement = directionMovement;
        this.healthCondition = new HealthCondition(NORMAL);
        this.individualProperties = individualProperties;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return individualProperties.getIndividualWidth();
    }

    public int getHeight() {
        return individualProperties.getIndividualHeight();
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public HealthStatus getHealthStatus() {
        return healthCondition.getHealthStatus();
    }

    public void move() {
        if (!isDead()) {
            adjustDirection();
            x += directionMovement.xAxisMovement();
            y += directionMovement.yAxisMovement();
        }
    }

    public boolean isInfected() {
        return healthCondition.getHealthStatus().equals(HealthStatus.INFECTED);
    }

    public void gotInfected(int currentDay) {
        healthCondition = new HealthCondition(INFECTED, currentDay);
    }

    public void died(int deathDay) {
        healthCondition = new HealthCondition(DEAD, deathDay);
        directionMovement = STANDING;
    }

    public void recovered(int recoveryDay) {
        healthCondition = new HealthCondition(RECOVERED, recoveryDay);
    }

    private boolean isDead() {
        return getHealthStatus().equals(DEAD);
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
        return x <= individualProperties.getLeftBoundary();
    }

    private boolean reachedRightBoundary() {
        return x >= individualProperties.getRightBoundary();
    }

    private boolean reachedUpBoundary() {
        return y <= individualProperties.getUpBoundary();
    }

    private boolean reachedDownBoundary() {
        return y >= individualProperties.getDownBoundary();
    }

    private boolean shouldChangeDirectionRandomly() {
        return RandomUtil.generatePercent() <= individualProperties.getDirectionChangeProbability();
    }

    private void changeDirection() {
        this.directionMovement = randomDirectionMovement();
    }

    public boolean crossedWayWith(Individual passerby) {
        return (passerby.getX() >= x) && (passerby.getX() < x + getWidth()) && (passerby.getY() >= y) && (passerby.getY() < y + getHeight());
    }
}
