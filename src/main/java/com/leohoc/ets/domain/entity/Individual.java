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

    public Individual(final int x, final int y, final DirectionMovement directionMovement, final SimulationIndividualProperties individualProperties) {
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
        if (healthCondition.getHealthStatus().allowedToMove()) {
            adjustDirection();
            x += directionMovement.xAxisMovement();
            y += directionMovement.yAxisMovement();
        }
    }

    public boolean isInfected() {
        return healthCondition.getHealthStatus().infected();
    }

    public boolean isHospitalized() {
        return healthCondition.getHealthStatus().equals(HOSPITALIZED);
    }

    public void gotInfected(final int currentDay) {
        healthCondition = new HealthCondition(INFECTED, currentDay);
    }

    public void gotHospitalized(final int infectionStartDay) {
        healthCondition = new HealthCondition(HOSPITALIZED, infectionStartDay);
        directionMovement = STANDING;
    }

    public void died(final int deathDay) {
        healthCondition = new HealthCondition(DEAD, deathDay);
        directionMovement = STANDING;
    }

    public void recovered(final int recoveryDay) {
        healthCondition = new HealthCondition(RECOVERED, recoveryDay);
    }

    protected void adjustDirection() {
        changeDirectionByReachingMapBoundaries();
        if (shouldChangeDirectionRandomly()) {
            changeDirection();
        }
    }

    public boolean crossedWayWith(final Individual passerby) {
        return (passerby.getX() >= x) && (passerby.getX() < x + getWidth()) && (passerby.getY() >= y) && (passerby.getY() < y + getHeight());
    }

    protected DirectionMovement getDirectionMovement() {
        return this.directionMovement;
    }

    protected void changeDirectionByReachingMapBoundaries() {

        if (reachedLeftBoundary()) {
            directionMovement = RIGHT;
            return;
        }

        if (reachedRightBoundary()) {
            directionMovement = LEFT;
            return;
        }

        if (reachedUpBoundary()) {
            directionMovement = DOWN;
            return;
        }

        if (reachedDownBoundary()) {
            directionMovement = UP;
        }
    }

    protected boolean reachedLeftBoundary() {
        return x <= individualProperties.getLeftBoundary();
    }

    protected boolean reachedRightBoundary() {
        return x >= individualProperties.getRightBoundary();
    }

    protected boolean reachedUpBoundary() {
        return y <= individualProperties.getUpBoundary();
    }

    protected boolean reachedDownBoundary() {
        return y >= individualProperties.getDownBoundary();
    }

    protected boolean shouldChangeDirectionRandomly() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < individualProperties.getDirectionChangeProbability();
    }

    protected void changeDirection() {
        this.directionMovement = randomDirectionMovement();
    }
}
