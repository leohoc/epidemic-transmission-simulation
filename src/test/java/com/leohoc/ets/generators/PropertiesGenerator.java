package com.leohoc.ets.generators;

import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;
import com.leohoc.ets.infrastructure.config.SimulationIterationsProperties;

public class PropertiesGenerator {

    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int INDIVIDUAL_INITIAL_BOUNDARY = 0;
    private static final int INDIVIDUAL_END_BOUNDARY = 10;
    private static final int RECOVERY_DAYS = 14;
    private static final int ZERO_DEATH_PERCENTAGE = 0;
    private static final int ZERO_HOSPITALIZATION_PERCENTAGE = 0;
    private static final int HOSPITALIZATION_DAYS = 7;
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

    public static SimulationIndividualProperties generateIndividualProperties() {
        return new SimulationIndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                INDIVIDUAL_INITIAL_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_INITIAL_BOUNDARY,
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE
        );
    }

    public static SimulationEpidemicProperties generateEpidemicProperties() {
        return new SimulationEpidemicProperties(RECOVERY_DAYS, ZERO_DEATH_PERCENTAGE, ZERO_HOSPITALIZATION_PERCENTAGE, HOSPITALIZATION_DAYS);
    }

    public static SimulationGraphicsProperties generateGraphicsProperties() {
        return new SimulationGraphicsProperties(
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                CURRENT_INFO_X,
                CURRENT_NOT_EXPOSED_Y,
                CURRENT_INFECTED_Y,
                CURRENT_HOSPITALIZED_Y,
                CURRENT_SIMULATION_DAY_Y,
                TOTAL_INFO_X,
                TOTAL_INFECTED_Y,
                TOTAL_HOSPITALIZED_Y,
                TOTAL_RECOVERED_Y,
                TOTAL_DEAD_Y);
    }

    public static SimulationIterationsProperties generateIterationsProperties() {
        return new SimulationIterationsProperties(TOTAL_ITERATIONS, ITERATIONS_PER_DAY);
    }
}
