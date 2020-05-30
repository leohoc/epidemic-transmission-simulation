package com.leohoc.ets.domain.enums;

import com.leohoc.ets.util.RandomUtil;

public enum DirectionMovement {

    STANDING(Movements.STAND, Movements.STAND),
    UP(Movements.STAND, -Movements.STEP),
    UP_RIGHT(Movements.STEP, -Movements.STEP),
    RIGHT(Movements.STEP, Movements.STAND),
    DOWN_RIGHT(Movements.STEP, Movements.STEP),
    DOWN(Movements.STAND, Movements.STEP),
    DOWN_LEFT(-Movements.STEP, Movements.STEP),
    LEFT(-Movements.STEP, Movements.STAND),
    UP_LEFT(-Movements.STEP, -Movements.STEP);

    DirectionMovement(final int xAxisMovement, final int yAxisMovement) {
        this.xAxisMovement = xAxisMovement;
        this.yAxisMovement = yAxisMovement;
    }

    private final int xAxisMovement;
    private final int yAxisMovement;

    public int xAxisMovement() {
        return xAxisMovement;
    }

    public int yAxisMovement() {
        return yAxisMovement;
    }

    public static DirectionMovement randomDirectionMovement() {
        final int bound = DirectionMovement.values().length;
        return DirectionMovement.values()[RandomUtil.generateIntLessThan(bound)];
    }

    private static class Movements {
        public static final int STAND = 0;
        public static final int STEP = 1;
    }
}