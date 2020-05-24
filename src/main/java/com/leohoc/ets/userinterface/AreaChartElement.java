package com.leohoc.ets.userinterface;

import java.awt.*;

public class AreaChartElement {

    private final Integer count;
    private final Color color;

    public AreaChartElement(final Integer count, final Color color) {
        this.count = count;
        this.color = color;
    }

    public Integer getCount() {
        return count;
    }

    public Color getColor() {
        return color;
    }
}
