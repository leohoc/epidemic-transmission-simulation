package com.leohoc.ets.domain.enums;

import java.awt.*;

public enum HealthCondition {

    NORMAL,
    INFECTED,
    RECOVERED,
    DEAD;

    public Color getColor() {
        if (this.equals(INFECTED)) {
            return Color.RED;
        }
        if (this.equals(RECOVERED)) {
            return Color.GREEN;
        }
        if (this.equals(DEAD)) {
            return Color.BLACK;
        }
        return Color.LIGHT_GRAY;
    }
}
