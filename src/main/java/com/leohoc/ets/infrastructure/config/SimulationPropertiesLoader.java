package com.leohoc.ets.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class SimulationPropertiesLoader {

    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimulationProperties loadSimulationProperties() {
        final int populationSize = parseInt(properties.getProperty("simulation.population.size"));
        final int initialInfectedPercent = parseInt(properties.getProperty("simulation.initialconditions.infectedpercent"));
        return new SimulationProperties(populationSize, initialInfectedPercent);
    }

    public SimulationIterationsProperties loadIterationsProperties() {
        final int totalIterations = parseInt(properties.getProperty("simulation.iterations.totaliterations"));
        final int iterationsPerDay = parseInt(properties.getProperty("simulation.iterations.iterationsperday"));
        return new SimulationIterationsProperties(totalIterations, iterationsPerDay);
    }

    public SimulationEpidemicProperties loadEpidemicProperties() {
        final int recoveryDays = parseInt(properties.getProperty("simulation.epidemic.recoverydays"));
        final int deathPercentage = parseInt(properties.getProperty("simulation.epidemic.deathpercentage"));
        return new SimulationEpidemicProperties(recoveryDays, deathPercentage);
    }

    public SimulationIndividualProperties loadIndividualProperties() {
        final int upBoundary = parseInt(properties.getProperty("simulation.individual.boundary.up"));
        final int rightBoundary = parseInt(properties.getProperty("simulation.individual.boundary.right"));
        final int downBoundary = parseInt(properties.getProperty("simulation.individual.boundary.down"));
        final int leftBoundary = parseInt(properties.getProperty("simulation.individual.boundary.left"));
        final int individualWidth = parseInt(properties.getProperty("simulation.individual.width"));
        final int individualHeight = parseInt(properties.getProperty("simulation.individual.height"));
        final int directionChangeProbability = parseInt(properties.getProperty("simulation.individual.direction.changeprobability"));
        return new SimulationIndividualProperties(individualWidth, individualHeight, directionChangeProbability, upBoundary, rightBoundary, downBoundary, leftBoundary);
    }

    public SimulationGraphicsProperties loadGraphicsProperties() {
        final int mapWidth = parseInt(properties.getProperty("simulation.graphics.map.width"));
        final int mapHeight = parseInt(properties.getProperty("simulation.graphics.map.height"));
        final int areaChartWidth = parseInt(properties.getProperty("simulation.graphics.areachart.width"));
        final int areaChartHeight = parseInt(properties.getProperty("simulation.graphics.areachart.height"));
        final int infectedCountY = parseInt(properties.getProperty("simulation.graphics.info.infected.y"));
        final int recoveredCountY = parseInt(properties.getProperty("simulation.graphics.info.recovered.y"));
        final int deadCountY = parseInt(properties.getProperty("simulation.graphics.info.dead.y"));
        final int epidemicRunningDaysY = parseInt(properties.getProperty("simulation.graphics.info.runningdays.y"));
        return new SimulationGraphicsProperties(mapWidth, mapHeight, areaChartWidth, areaChartHeight, infectedCountY, recoveredCountY, deadCountY, epidemicRunningDaysY);
    }
}