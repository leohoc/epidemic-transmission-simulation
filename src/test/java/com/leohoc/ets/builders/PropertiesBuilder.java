package com.leohoc.ets.builders;

import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;
import com.leohoc.ets.infrastructure.config.SimulationIndividualProperties;

public class PropertiesBuilder {

    private static final int DEFAULT_PROPERTY_VALUE = 1;
    private static final int INDIVIDUAL_INITIAL_BOUNDARY = 0;
    private static final int INDIVIDUAL_END_BOUNDARY = 10;
    private static final int RECOVERY_DAYS = 14;
    private static final int ZERO_DEATH_PERCENTAGE = 0;

    private static final int DEFAULT_MAP_PROPERTY_VALUE = 10;


    public static SimulationIndividualProperties buildIndividualProperties() {
        return new SimulationIndividualProperties(
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                DEFAULT_PROPERTY_VALUE,
                INDIVIDUAL_INITIAL_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_END_BOUNDARY,
                INDIVIDUAL_INITIAL_BOUNDARY
        );
    }

    public static SimulationEpidemicProperties buildEpidemicProperties() {
        return new SimulationEpidemicProperties(RECOVERY_DAYS, ZERO_DEATH_PERCENTAGE);
    }

    public static SimulationGraphicsProperties buildGraphicsProperties() {
        return new SimulationGraphicsProperties(
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE,
                DEFAULT_MAP_PROPERTY_VALUE);
    }
}
