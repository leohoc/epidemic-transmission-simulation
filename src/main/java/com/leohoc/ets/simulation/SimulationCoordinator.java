package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimulationCoordinator {

    private static final Logger LOGGER = Logger.getLogger(SimulationCoordinator.class.getName());

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 25;
    private final List<Individual> population = new ArrayList<>();
    private final SimulationProperties simulationProperties;
    private final SimulationIndividualProperties individualProperties;
    private final IterationEvolution iterationEvolution;
    private final PopulationDynamics populationDynamics;
    private final GraphicalEnvironment graphicalEnvironment;
    private final MovementBehavior movementBehavior;
    private final EpidemicStatistics epidemicStatistics;

    public SimulationCoordinator(final SimulationProperties simulationProperties,
                                 final SimulationIndividualProperties individualProperties,
                                 final IterationEvolution iterationEvolution,
                                 final PopulationDynamics populationDynamics,
                                 final GraphicalEnvironment graphicalEnvironment,
                                 final MovementBehavior movementBehavior,
                                 final EpidemicStatistics epidemicStatistics) {
        this.simulationProperties = simulationProperties;
        this.individualProperties = individualProperties;
        this.iterationEvolution = iterationEvolution;
        this.populationDynamics = populationDynamics;
        this.graphicalEnvironment = graphicalEnvironment;
        this.movementBehavior = movementBehavior;
        this.epidemicStatistics = epidemicStatistics;
    }

    public void startSimulation() {
        this.population.addAll(generatePopulation());
        if (simulationProperties.isGraphicsEnabled()) {
            graphicalEnvironment.initialize(population, iterationEvolution, epidemicStatistics);
            new Thread(this::runGraphicalEnvironment).start();
        }
        new Thread(this::runSimulation).start();
    }

    protected List<Individual> generatePopulation() {
        List<Individual> initialPopulation = new ArrayList<>();
        for (int i = 0; i < simulationProperties.getPopulationSize(); i++) {
            Individual individual = randomIndividual();
            if (shouldGotInfected(simulationProperties.getInitialInfectedPercent())) {
                individual.gotInfected(iterationEvolution.getCurrentSimulatedDay());
            }
            initialPopulation.add(individual);
        }
        return initialPopulation;
    }

    private Individual randomIndividual() {
        return new Individual(
                movementBehavior.generateRandomXPointWithinMapBoundaries(),
                movementBehavior.generateRandomYPointWithinMapBoundaries(),
                movementBehavior.newRandomDirection(),
                individualProperties
        );
    }

    private boolean shouldGotInfected(final double initialInfectedPercent) {
        return RandomUtil.generatePercentWithTwoDigitsScale() < initialInfectedPercent;
    }

    protected void runSimulation() {
        do {
            runDynamicsIteration();
            iterationEvolution.iterate();
        } while (!iterationEvolution.hasSimulationFinished());
        printStatistics();
    }

    private void runDynamicsIteration() {
        EpidemicStatistics iterationStatistics = populationDynamics.executeDynamicsIterationOn(population, iterationEvolution.getCurrentSimulatedDay());
        epidemicStatistics.updateAllStatistics(iterationStatistics);
    }

    private void runGraphicalEnvironment() {
        while (!iterationEvolution.hasSimulationFinished()) {
            sleepFor(GRAPHICS_UPDATE_TIME_MS);
            graphicalEnvironment.getImagePanel(population, iterationEvolution, epidemicStatistics).repaint();
        }
    }

    private void sleepFor(final Integer timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printStatistics() {
        LOGGER.info("TOTAL RUNNING DAYS: " + iterationEvolution.getCurrentSimulatedDay());
        LOGGER.info("CURRENT NORMAL COUNT: " + epidemicStatistics.getCurrentNormalCount());
        LOGGER.info("CURRENT INFECTED COUNT: " + epidemicStatistics.getCurrentInfectedCount());
        LOGGER.info("CURRENT HOSPITALIZED COUNT: " + epidemicStatistics.getCurrentHospitalizedCount());
        LOGGER.info("TOTAL INFECTED COUNT: " + epidemicStatistics.getTotalInfectedCount());
        LOGGER.info("TOTAL HOSPITALIZED COUNT: " + epidemicStatistics.getTotalHospitalizedCount());
        LOGGER.info("TOTAL RECOVERED COUNT: " + epidemicStatistics.getTotalRecoveredCount());
        LOGGER.info("TOTAL DEAD COUNT: " + epidemicStatistics.getTotalDeadCount());
    }
}
