package com.leohoc.ets.builders;

import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;

public class PropertiesBuilder {

    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int INDIVIDUAL_INITIAL_BOUNDARY = 0;
    private static final int INDIVIDUAL_END_BOUNDARY = 10;
    private static final int RECOVERY_DAYS = 14;
    private static final int ZERO_DEATH_PERCENTAGE = 0;
    private static final int ZERO_HOSPITALIZATION_PERCENTAGE = 0;
    private static final int HOSPITALIZATION_DAYS = 7;
    private static final int DEFAULT_MAP_PROPERTY_VALUE = 10;
    private static final int INFECTED_COUNT_Y = 1;
    private static final int HOSPITALIZED_COUNT_Y = 2;
    private static final int RECOVERED_COUNT_Y = 3;
    private static final int DEAD_COUNT_Y = 4;
    private static final int RUNNING_DAYS_Y = 5;
    private static final Integer TOTAL_ITERATIONS = 100;
    private static final Integer ITERATIONS_PER_DAY = 10;

    public static SimulationIndividualProperties buildIndividualProperties() {
        return new SimulationIndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                INDIVIDUAL_INITIAL_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_INITIAL_BOUNDARY,
                DEFAULT_PROPERTY_VALUE
        );
    }

    public static SimulationEpidemicProperties buildEpidemicProperties() {
        return new SimulationEpidemicProperties(RECOVERY_DAYS, ZERO_DEATH_PERCENTAGE, ZERO_HOSPITALIZATION_PERCENTAGE, HOSPITALIZATION_DAYS);
    }

    public static SimulationGraphicsProperties buildGraphicsProperties() {
        return new SimulationGraphicsProperties(
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                INFECTED_COUNT_Y,
                HOSPITALIZED_COUNT_Y,
                RECOVERED_COUNT_Y,
                DEAD_COUNT_Y,
                RUNNING_DAYS_Y);
    }

    public static SimulationIterationsProperties buildIterationsProperties() {
        return new SimulationIterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }
}
