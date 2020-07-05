package com.leohoc.ets.userinterface;

import java.awt.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaChartElement element = (AreaChartElement) o;
        return Objects.equals(count, element.count) &&
                Objects.equals(color, element.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, color);
    }
}
