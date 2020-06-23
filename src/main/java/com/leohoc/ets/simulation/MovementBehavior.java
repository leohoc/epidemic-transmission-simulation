package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.MovementProperties;
import com.leohoc.ets.util.RandomUtil;

import static com.leohoc.ets.domain.enums.DirectionMovement.*;

public class MovementBehavior {

    private final MovementProperties movementProperties;

    public MovementBehavior(final MovementProperties movementProperties) {
        this.movementProperties = movementProperties;
    }

    public void adjustDirectionOf(final Individual individual) {
        if (shouldChangeDirectionRandomly()) {
            individual.moveTo(newRandomDirection());
        }
        individual.moveTo(getDirectionConsideringMapBoundariesOf(individual));
    }

    private DirectionMovement getDirectionConsideringMapBoundariesOf(final Individual individual) {
        if (reachedLeftBoundary(individual.getX())) {
            return RIGHT;
        }
        if (reachedRightBoundary(individual.getX())) {
            return LEFT;
        }
        if (reachedUpBoundary(individual.getY())) {
            return DOWN;
        }
        if (reachedDownBoundary(individual.getY())) {
            return UP;
        }
        return individual.getDirectionMovement();
    }

    private boolean reachedLeftBoundary(final int x) {
        return x <= movementProperties.getLeftBoundary();
    }

    private boolean reachedRightBoundary(final int x) {
        return x >= movementProperties.getRightBoundary();
    }

    private boolean reachedUpBoundary(final int y) {
        return y <= movementProperties.getUpBoundary();
    }

    private boolean reachedDownBoundary(final int y) {
        return y >= movementProperties.getDownBoundary();
    }

    protected boolean shouldChangeDirectionRandomly() {
        return RandomUtil.generatePercentWithTwoDigitsScale() < movementProperties.getDirectionChangeProbability();
    }

    public DirectionMovement newRandomDirection() {
        return randomDirectionMovement(movementProperties.getSocialIsolationPercent());
    }

    public int generateRandomXPointWithinMapBoundaries() {
        return RandomUtil.generateIntBetween(movementProperties.getLeftBoundary(), movementProperties.getRightBoundary());
    }

    public int generateRandomYPointWithinMapBoundaries() {
        return RandomUtil.generateIntBetween(movementProperties.getUpBoundary(), movementProperties.getDownBoundary());
    }
}
