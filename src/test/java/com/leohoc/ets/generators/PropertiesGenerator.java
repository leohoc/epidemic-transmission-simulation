package com.leohoc.ets.generators;

import com.leohoc.ets.infrastructure.config.*;

public class PropertiesGenerator {

    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int DEFAULT_MAP_PROPERTY_VALUE = 10;
    private static final int CURRENT_INFO_X = 0;
    private static final int CURRENT_NOT_EXPOSED_Y = 1;
    private static final int CURRENT_INFECTED_Y = 2;
    private static final int CURRENT_HOSPITALIZED_Y = 3;
    private static final int CURRENT_SIMULATION_DAY_Y = 4;
    private static final int TOTAL_INFO_X = 2;
    private static final int TOTAL_INFECTED_Y = 1;
    private static final int TOTAL_HOSPITALIZED_Y = 2;
    private static final int TOTAL_RECOVERED_Y = 3;
    private static final int TOTAL_DEAD_Y = 4;
    private static final int TOTAL_ITERATIONS = 100;
    private static final int ITERATIONS_PER_DAY = 10;

    public static IndividualProperties generateIndividualProperties() {
        return new IndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE
        );
    }

    public static GraphicsProperties generateGraphicsProperties() {
        return new GraphicsProperties(
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                generateCurrentInfoLocation(),
                generateTotalInfoLocation());
    }

    public static IterationsProperties generateIterationsProperties() {
        return new IterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }

    private static TotalInfoLocation generateTotalInfoLocation() {
        return new TotalInfoLocation(
                TOTAL_INFO_X,
                TOTAL_INFECTED_Y,
                TOTAL_HOSPITALIZED_Y,
                TOTAL_RECOVERED_Y,
                TOTAL_DEAD_Y);
    }

    private static CurrentInfoLocation generateCurrentInfoLocation() {
        return new CurrentInfoLocation(
                CURRENT_INFO_X,
                CURRENT_NOT_EXPOSED_Y,
                CURRENT_INFECTED_Y,
                CURRENT_HOSPITALIZED_Y,
                CURRENT_SIMULATION_DAY_Y);
    }
}
