package com.leohoc.ets.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimulationProperties {

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";

    private static Integer mapSize;
    private static Integer populationSize;
    private static Integer individualWidth;
    private static Integer individualHeight;

    static {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {

            Properties properties = new Properties();
            properties.load(input);

            mapSize = Integer.parseInt(properties.getProperty("simulation.map.size"));
            populationSize = Integer.parseInt(properties.getProperty("simulation.population.size"));
            individualWidth = Integer.parseInt(properties.getProperty("simulation.individual.width"));
            individualHeight = Integer.parseInt(properties.getProperty("simulation.individual.height"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getMapSize() {
        return mapSize;
    }

    public Integer getPopulationSize() {
        return populationSize;
    }

    public Integer getIndividualWidth() {
        return individualWidth;
    }

    public Integer getIndividualHeight() {
        return individualHeight;
    }
}
