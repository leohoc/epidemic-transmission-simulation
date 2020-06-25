package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.infrastructure.config.MovementProperties;
import com.leohoc.ets.util.RandomUtil;

import static com.leohoc.ets.domain.enums.DirectionMovement.*;

public class MovementBehavior {

    private final MovementProperties movementProperties;
    private final RandomUtil randomUtil;

    public MovementBehavior(final MovementProperties movementProperties, final RandomUtil randomUtil) {
        this.movementProperties = movementProperties;
        this.randomUtil = randomUtil;
    }

    public void adjustDirectionOf(final Individual individual) {
        if (shouldChangeDirectionRandomly()) {
            individual.moveTo(newRandomDirection());
        }
        individual.moveTo(getDirectionConsideringMapBoundariesOf(individual));
    }

    private boolean shouldChangeDirectionRandomly() {
        return randomUtil.generatePercentWithTwoDigitsScale() < movementProperties.getDirectionChangeProbability();
    }

    public DirectionMovement newRandomDirection() {
        if (shouldRespectSocialIsolation(movementProperties.getSocialIsolationPercent())) {
            return STANDING;
        }

        final int bound = DirectionMovement.movementDirections().size();
        return movementDirections().get(randomUtil.generateIntLessThan(bound));
    }

    private boolean shouldRespectSocialIsolation(double socialIsolationPercent) {
        return randomUtil.generatePercentWithTwoDigitsScale() < socialIsolationPercent;
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

    public int generateRandomXPointWithinMapBoundaries() {
        return randomUtil.generateIntBetween(movementProperties.getLeftBoundary(), movementProperties.getRightBoundary());
    }

    public int generateRandomYPointWithinMapBoundaries() {
        return randomUtil.generateIntBetween(movementProperties.getUpBoundary(), movementProperties.getDownBoundary());
    }
}
