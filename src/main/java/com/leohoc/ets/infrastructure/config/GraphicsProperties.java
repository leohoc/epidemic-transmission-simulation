package com.leohoc.ets.infrastructure.config;

public class GraphicsProperties {

    private final int width;
    private final int height;

    public GraphicsProperties(final int width,
                              final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
