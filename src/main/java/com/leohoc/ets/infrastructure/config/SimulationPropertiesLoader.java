package com.leohoc.ets.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class SimulationPropertiesLoader {

    private static final Logger LOGGER = Logger.getLogger(SimulationPropertiesLoader.class.getName());
    private static final String PROPERTIES_FILE_ERROR = "Error loading properties file";
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, PROPERTIES_FILE_ERROR, e);
        }
    }

    public SimulationProperties loadSimulationProperties() {
        final int populationSize = parseInt(properties.getProperty("simulation.population.size"));
        final double initialInfectedPercent = parseDouble(properties.getProperty("simulation.initialconditions.infectedpercent"));
        final boolean graphicsEnabled = parseBoolean(properties.getProperty("simulation.graphics.enabled"));
        return new SimulationProperties(populationSize, initialInfectedPercent, graphicsEnabled);
    }

    public IterationsProperties loadIterationsProperties() {
        final int totalIterations = parseInt(properties.getProperty("simulation.iterations.totaliterations"));
        final int iterationsPerDay = parseInt(properties.getProperty("simulation.iterations.iterationsperday"));
        return new IterationsProperties(totalIterations, iterationsPerDay);
    }

    public EpidemicProperties loadEpidemicProperties() {
        final int recoveryDays = parseInt(properties.getProperty("simulation.epidemic.recoverydays"));
        final double deathPercentage = parseDouble(properties.getProperty("simulation.epidemic.deathpercentage"));
        final double hospitalizationPercentage = parseDouble(properties.getProperty("simulation.epidemic.hospitalizationpercentage"));
        final int hospitalizationDays = parseInt(properties.getProperty("simulation.epidemic.hospitalizationdays"));
        return new EpidemicProperties(recoveryDays, deathPercentage, hospitalizationPercentage, hospitalizationDays);
    }

    public IndividualProperties loadIndividualProperties() {
        final int individualWidth = parseInt(properties.getProperty("simulation.individual.width"));
        final int individualHeight = parseInt(properties.getProperty("simulation.individual.height"));
        return new IndividualProperties(individualWidth, individualHeight);
    }

    public MovementProperties loadMovementProperties() {
        final int upBoundary = parseInt(properties.getProperty("simulation.movement.boundary.up"));
        final int rightBoundary = parseInt(properties.getProperty("simulation.movement.boundary.right"));
        final int downBoundary = parseInt(properties.getProperty("simulation.movement.boundary.down"));
        final int leftBoundary = parseInt(properties.getProperty("simulation.movement.boundary.left"));
        final double directionChangeProbability = parseDouble(properties.getProperty("simulation.movement.changeprobability"));
        final double socialIsolationPercent = parseDouble(properties.getProperty("simulation.movement.socialisolationpercent"));
        return new MovementProperties(upBoundary, rightBoundary, downBoundary, leftBoundary, directionChangeProbability, socialIsolationPercent);
    }

    public GraphicsProperties loadGraphicsProperties() {

        final int width = parseInt(properties.getProperty("simulation.graphics.map.width"));
        final int height = parseInt(properties.getProperty("simulation.graphics.map.height"));

        return new GraphicsProperties(width, height);
    }

    public ChartPanelProperties loadChartPanelProperties() {

        final int x = parseInt(properties.getProperty("simulation.graphics.chartpanel.x"));
        final int y = parseInt(properties.getProperty("simulation.graphics.chartpanel.y"));
        final int width = parseInt(properties.getProperty("simulation.graphics.chartpanel.width"));
        final int height = parseInt(properties.getProperty("simulation.graphics.chartpanel.height"));

        final int currentInfoX = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.current.x"));
        final int currentNotExposedY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.current.notexposed.y"));
        final int currentInfectedY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.current.infected.y"));
        final int currentHospitalizedY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.current.hospitalized.y"));
        final int currentSimulationDayY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.current.simulationday.y"));
        final CurrentInfoLocation currentInfoLocation = new CurrentInfoLocation(currentInfoX, currentNotExposedY, currentInfectedY, currentHospitalizedY, currentSimulationDayY);

        final int totalInfoX = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.total.x"));
        final int totalInfectedY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.total.infected.y"));
        final int totalHospitalizedY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.total.hospitalized.y"));
        final int totalRecoveredY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.total.recovered.y"));
        final int totalDeadY = parseInt(properties.getProperty("simulation.graphics.chartpanel.info.total.dead.y"));
        final TotalInfoLocation totalInfoLocation = new TotalInfoLocation(totalInfoX, totalInfectedY, totalHospitalizedY, totalRecoveredY, totalDeadY);

        return new ChartPanelProperties(x, y, width, height, currentInfoLocation, totalInfoLocation);
    }

    public AreaChartProperties loadAreaChartProperties() {
        final int x = parseInt(properties.getProperty("simulation.graphics.areachart.x"));
        final int y = parseInt(properties.getProperty("simulation.graphics.areachart.y"));
        final int width = parseInt(properties.getProperty("simulation.graphics.areachart.width"));
        final int height = parseInt(properties.getProperty("simulation.graphics.areachart.height"));
        return new AreaChartProperties(x, y, width, height);
    }

    public HealthSystemCapacityProperties loadHealthSystemCapacityProperties() {
        final int availableBeds = parseInt(properties.getProperty("simulation.healthsystem.icus.availablebeds"));
        return new HealthSystemCapacityProperties(availableBeds);
    }
}