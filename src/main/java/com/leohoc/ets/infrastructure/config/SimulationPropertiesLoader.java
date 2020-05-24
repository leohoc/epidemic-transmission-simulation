package com.leohoc.ets.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class SimulationPropertiesLoader {

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";

    public static void loadProperties() {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {

            Properties properties = new Properties();
            properties.load(input);

            loadEnvironmentProperties(properties);
            loadIndividualProperties(properties);
            loadEpidemicProperties(properties);
            loadGraphicsProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadEpidemicProperties(final Properties properties) {
        final int initialInfectedPercent = parseInt(properties.getProperty("simulation.epidemic.initialconditions.infectedpercent"));
        final int simulationTotalTimeInMs = parseInt(properties.getProperty("simulation.epidemic.totaltime"));
        final int simulatedDayDurationInMs = parseInt(properties.getProperty("simulation.epidemic.daydurationinms"));
        final int receoveryDays = parseInt(properties.getProperty("simulation.epidemic.recoverydays"));
        final int deathPercentage = parseInt(properties.getProperty("simulation.epidemic.deathpercentage"));
        SimulationEpidemicProperties.loadProperties(initialInfectedPercent, simulationTotalTimeInMs, simulatedDayDurationInMs, receoveryDays, deathPercentage);
    }

    private static void loadIndividualProperties(final Properties properties) {
        final int upBoundary = parseInt(properties.getProperty("simulation.individual.boundary.up"));
        final int rightBoundary = parseInt(properties.getProperty("simulation.individual.boundary.right"));
        final int downBoundary = parseInt(properties.getProperty("simulation.individual.boundary.down"));
        final int leftBoundary = parseInt(properties.getProperty("simulation.individual.boundary.left"));
        final int individualWidth = parseInt(properties.getProperty("simulation.individual.width"));
        final int individualHeight = parseInt(properties.getProperty("simulation.individual.height"));
        final int directionChangeProbability = parseInt(properties.getProperty("simulation.individual.direction.changeprobability"));
        SimulationIndividualProperties.loadProperties(
                                            individualWidth,
                                            individualHeight,
                                            directionChangeProbability,
                                            upBoundary,
                                            rightBoundary,
                                            downBoundary,
                                            leftBoundary);
    }

    private static void loadEnvironmentProperties(final Properties properties) {
        final int mapSize = parseInt(properties.getProperty("simulation.environment.map.size"));
        final int populationSize = parseInt(properties.getProperty("simulation.environment.population.size"));
        SimulationEnvironmentProperties.loadProperties(mapSize, populationSize);
    }

    private static void loadGraphicsProperties(final Properties properties) {
        final int areaChartWidth = parseInt(properties.getProperty("simulation.graphics.areachart.width"));
        final int areaChartHeight = parseInt(properties.getProperty("simulation.graphics.areachart.height"));
        final int areaChartElementWidth = parseInt(properties.getProperty("simulation.graphics.areachart.element.width"));
        final int infectedCountY = parseInt(properties.getProperty("simulation.graphics.info.infected.y"));
        final int recoveredCountY = parseInt(properties.getProperty("simulation.graphics.info.recovered.y"));
        final int deadCountY = parseInt(properties.getProperty("simulation.graphics.info.dead.y"));
        SimulationGraphicsProperties.loadProperties(areaChartWidth, areaChartHeight, areaChartElementWidth, infectedCountY, recoveredCountY, deadCountY);
    }
}