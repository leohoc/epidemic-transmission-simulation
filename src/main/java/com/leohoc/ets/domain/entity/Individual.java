package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthCondition;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.util.RandomUtil;

import java.awt.*;

import static com.leohoc.ets.domain.enums.HealthCondition.INFECTED;

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
                DirectionMovement.randomDirectionMovement()
        );
    }

    public Individual(final int x, final int y, final DirectionMovement directionMovement) {
        this.x = x;
        this.y = y;
        this.directionMovement = directionMovement;
        this.healthCondition = HealthCondition.NORMAL;
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

    public void move() {
        adjustDirection();
        x += directionMovement.xAxisMovement();
        y += directionMovement.yAxisMovement();
    }

    private void adjustDirection() {
        changeDirectionByReachingMapBoundaries();
        if (shouldChangeDirectionRandomly()) {
            changeDirection();
        };
    }

    private void changeDirectionByReachingMapBoundaries() {

        if (reachedLeftBoundary()) {
            directionMovement = DirectionMovement.RIGHT;
        }

        if (reachedRightBoundary()) {
            directionMovement = DirectionMovement.LEFT;
        }

        if (reachedUpBoundary()) {
            directionMovement = DirectionMovement.DOWN;
        }

        if (reachedDownBoundary()) {
            directionMovement = DirectionMovement.UP;
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
        return RandomUtil.generateIntLessThan(HUNDRED_PERCENT) < SimulationIndividualProperties.getDirectionChangeProbability();
    }

    private void changeDirection() {
        this.directionMovement = DirectionMovement.randomDirectionMovement();
    }

    public void gotInfected() {
        healthCondition = INFECTED;
    }

    public void interactionWith(Individual passerby) {
        if (crossedWayWith(passerby) && passerby.getHealthCondition().equals(INFECTED)) {
            healthCondition = INFECTED;
        }
    }

    private boolean crossedWayWith(Individual passerby) {
        return (passerby.getX() >= x) && (passerby.getX() < x + getWidth()) && (passerby.getY() >= y) && (passerby.getY() < y + getHeight());
    }
}
