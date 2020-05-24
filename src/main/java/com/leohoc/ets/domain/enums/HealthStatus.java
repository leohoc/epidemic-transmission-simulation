package com.leohoc.ets.domain.enums;

import java.awt.*;

public enum HealthStatus {

    NORMAL,
    INFECTED,
    RECOVERED,
    DEAD;

    public Color getColor() {
        if (this.equals(INFECTED)) {
            return Color.BLUE;
        }
        if (this.equals(RECOVERED)) {
            return Color.GREEN;
        }
        if (this.equals(DEAD)) {
            return Color.RED;
        }
        return Color.LIGHT_GRAY;
    }
}
