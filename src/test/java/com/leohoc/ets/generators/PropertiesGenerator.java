package com.leohoc.ets.generators;

import com.leohoc.ets.infrastructure.config.*;

public class PropertiesGenerator {

    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int TOTAL_ITERATIONS = 100;
    private static final int ITERATIONS_PER_DAY = 10;

    public static IndividualProperties generateIndividualProperties() {
        return new IndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE
        );
    }

    public static IterationsProperties generateIterationsProperties() {
        return new IterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }
}
