package com.leohoc.ets.infrastructure.config;

import java.math.BigDecimal;

public class SimulationEnvironmentProperties {

    private static Integer mapSize;
    private static Integer populationSize;

    static void loadProperties(final Integer mapSize, final Integer populationSize) {
        SimulationEnvironmentProperties.mapSize = mapSize;
        SimulationEnvironmentProperties.populationSize = populationSize;
    }

    public static Integer getMapSize() {
        return mapSize;
    }

    public static Integer getPopulationSize() {
        return populationSize;
    }

    public static BigDecimal getBigDecimalPopulationSize() {
        return BigDecimal.valueOf(populationSize);
    }
}
