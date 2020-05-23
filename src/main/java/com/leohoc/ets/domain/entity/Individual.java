package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.util.RandomUtil;

import java.awt.*;

public class Individual {

    public static final int HUNDRED_PERCENT = 100;

    private int x;
    private int y;
    private DirectionMovement directionMovement;

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

    public Color getColor() {
        return Color.GRAY;
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
}
