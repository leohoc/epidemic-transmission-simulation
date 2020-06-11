package com.leohoc.ets.infrastructure.config;

public class SimulationIndividualProperties {

    private final int individualWidth;
    private final int individualHeight;

    public SimulationIndividualProperties(final int individualWidth,
                                          final int individualHeight) {
        this.individualWidth = individualWidth;
        this.individualHeight = individualHeight;
    }

    public int getIndividualWidth() {
        return individualWidth;
    }

    public int getIndividualHeight() {
        return individualHeight;
    }
}
