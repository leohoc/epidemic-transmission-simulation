package com.leohoc.ets.infrastructure.config;

public class AreaChartProperties {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public AreaChartProperties(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
