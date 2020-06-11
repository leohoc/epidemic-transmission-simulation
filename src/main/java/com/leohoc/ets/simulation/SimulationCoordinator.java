package com.leohoc.ets.simulation;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class SimulationCoordinator {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 25;

    private final EpidemicStatistics epidemicStatistics = new EpidemicStatistics();;
    private final List<Individual> population = new ArrayList<>();
    private final SimulationProperties simulationProperties;
    private final SimulationIndividualProperties individualProperties;
    private final IterationEvolution iterationEvolution;
    private final PopulationDynamics populationDynamics;
    private final GraphicalEnvironment graphicalEnvironment;
    private final MovementBehavior movementBehavior;

    public SimulationCoordinator(final SimulationPropertiesLoader propertiesLoader) {
        final SimulationHealthSystemCapacityProperties healthSystemCapacityProperties = propertiesLoader.loadHealthSystemCapacityProperties();
        this.simulationProperties = propertiesLoader.loadSimulationProperties();
        this.individualProperties = propertiesLoader.loadIndividualProperties();
        this.iterationEvolution = new IterationEvolution(propertiesLoader.loadIterationsProperties());
        this.graphicalEnvironment = new GraphicalEnvironment(propertiesLoader.loadGraphicsProperties(), healthSystemCapacityProperties.getAvailableBeds());
        this.movementBehavior = new MovementBehavior(propertiesLoader.loadMovementProperties());

        final HealthSystemResources healthSystemResources = new HealthSystemResources(healthSystemCapacityProperties.getAvailableBeds());
        final DiseaseBehavior diseaseBehavior = new DiseaseBehavior(propertiesLoader.loadEpidemicProperties(), healthSystemResources);
        this.populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior);
    }

    public void startSimulation() {
        this.population.addAll(generatePopulation());
        new Thread(() -> runSimulation()).start();
        if (simulationProperties.isGraphicsEnabled()) {
            new Thread(() -> runGraphicalEnvironment()).start();
        }
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

    protected boolean shouldGotInfected(final double initialInfectedPercent) {
        return RandomUtil.generatePercentWithTwoDigitsScale() < initialInfectedPercent;
    }

    protected void runSimulation() {
        do {
            runDynamicsIteration();
            iterationEvolution.iterate();
        } while (!iterationEvolution.hasSimulationFinished());
        printStatistics();
    }

    protected void runDynamicsIteration() {
        EpidemicStatistics iterationStatistics = populationDynamics.executeDynamicsIterationOn(population, iterationEvolution.getCurrentSimulatedDay());
        epidemicStatistics.updateAllStatistics(iterationStatistics);
    }

    private void runGraphicalEnvironment() {
        graphicalEnvironment.initialize(population, iterationEvolution, epidemicStatistics);
        graphicalEnvironment.setVisible(true);

        while (!iterationEvolution.hasSimulationFinished()) {
            sleepFor(GRAPHICS_UPDATE_TIME_MS);
            graphicalEnvironment.getImagePanel(population, iterationEvolution, epidemicStatistics).repaint();
        }
    }

    private void sleepFor(final Integer timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printStatistics() {
        System.out.println("TOTAL RUNNING DAYS: " + iterationEvolution.getCurrentSimulatedDay());
        System.out.println("CURRENT NORMAL COUNT: " + epidemicStatistics.getCurrentNormalCount());
        System.out.println("CURRENT INFECTED COUNT: " + epidemicStatistics.getCurrentInfectedCount());
        System.out.println("CURRENT HOSPITALIZED COUNT: " + epidemicStatistics.getCurrentHospitalizedCount());
        System.out.println("TOTAL INFECTED COUNT: " + epidemicStatistics.getTotalInfectedCount());
        System.out.println("TOTAL HOSPITALIZED COUNT: " + epidemicStatistics.getTotalHospitalizedCount());
        System.out.println("TOTAL RECOVERED COUNT: " + epidemicStatistics.getTotalRecoveredCount());
        System.out.println("TOTAL DEAD COUNT: " + epidemicStatistics.getTotalDeadCount());
    }
}
