package com.leohoc.ets.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimulationPropertiesLoader {

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";

    public static void loadProperties() {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {

            Properties properties = new Properties();
            properties.load(input);

            Integer mapSize = Integer.parseInt(properties.getProperty("simulation.environment.map.size"));
            Integer populationSize = Integer.parseInt(properties.getProperty("simulation.environment.population.size"));
            SimulationEnvironmentProperties.loadProperties(mapSize, populationSize);

            Integer upBoundary = Integer.parseInt(properties.getProperty("simulation.individual.boundary.up"));
            Integer rightBoundary = Integer.parseInt(properties.getProperty("simulation.individual.boundary.right"));
            Integer downBoundary = Integer.parseInt(properties.getProperty("simulation.individual.boundary.down"));
            Integer leftBoundary = Integer.parseInt(properties.getProperty("simulation.individual.boundary.left"));
            Integer individualWidth = Integer.parseInt(properties.getProperty("simulation.individual.width"));
            Integer individualHeight = Integer.parseInt(properties.getProperty("simulation.individual.height"));
            Integer directionChangeProbability = Integer.parseInt(properties.getProperty("simulation.individual.direction.changeprobability"));
            SimulationIndividualProperties.loadProperties(
                                                individualWidth,
                                                individualHeight,
                                                directionChangeProbability,
                                                upBoundary,
                                                rightBoundary,
                                                downBoundary,
                                                leftBoundary);

            Integer initialInfectedPercent = Integer.parseInt(properties.getProperty("simulation.epidemic.initialconditions.infectedpercent"));
            SimulationEpidemicProperties.loadProperties(initialInfectedPercent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
