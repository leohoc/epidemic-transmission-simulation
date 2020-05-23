package com.leohoc.ets.userinterface;

import java.awt.*;

public class AreaChartElement {

    private Integer percentage;
    private Color color;

    public AreaChartElement(Integer percentage, Color color) {
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
