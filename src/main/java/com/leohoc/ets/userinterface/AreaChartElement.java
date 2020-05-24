package com.leohoc.ets.userinterface;

import java.awt.*;

public class AreaChartElement {

    private final Integer percentage;
    private final Color color;

    public AreaChartElement(final Integer percentage, final Color color) {
        this.percentage = percentage;
        this.color = color;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Color getColor() {
        return color;
    }
}
