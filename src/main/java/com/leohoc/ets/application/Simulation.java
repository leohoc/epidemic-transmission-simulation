package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 20;
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);

    private final SimulationProperties simulationProperties;
    private final List<Individual> population = new ArrayList<>();
    private final IterationEvolution iterationEvolution;
    private final DiseaseBehavior diseaseBehavior;
    private final GraphicalEnvironment graphicalEnvironment;

    public Simulation(SimulationPropertiesLoader simulationPropertiesLoader) {
        this.iterationEvolution = new IterationEvolution(simulationPropertiesLoader.loadIterationsProperties());
        this.diseaseBehavior = new DiseaseBehavior(simulationPropertiesLoader.loadEpidemicProperties());
        this.graphicalEnvironment = new GraphicalEnvironment(simulationPropertiesLoader.loadGraphicsProperties());
        this.simulationProperties = simulationPropertiesLoader.loadSimulationProperties();
        this.generatePopulation(simulationPropertiesLoader.loadIndividualProperties());
    }

    private void generatePopulation(SimulationIndividualProperties individualProperties) {
        for (int i = 0; i < simulationProperties.getPopulationSize(); i++) {
            Individual individual = Individual.randomIndividual(individualProperties);
            if (shouldGotInfected(simulationProperties.getInitialInfectedPercent())) {
                individual.gotInfected(iterationEvolution.getCurrentSimulatedDay());
            }
            population.add(individual);
        }
    }

    public void startSimulation() {
        new Thread(() -> runSimulation()).start();
        new Thread(() -> runGraphicalEnvironment()).start();
    }

    private boolean shouldGotInfected(final int initialInfectedPercent) {
        return RandomUtil.generateIntLessThan(HUNDRED_PERCENT.intValue()) <= initialInfectedPercent;
    }

    private void runSimulation() {
        while (!iterationEvolution.hasSimulationFinished()) {
            for (Individual individual : population) {
                individual.move();
                diseaseBehavior.updateHealthCondition(individual, iterationEvolution.getCurrentSimulatedDay());
                for (Individual passerby : population) {
                    if (!individual.equals(passerby)) {
                        individual.interactionWith(passerby, iterationEvolution.getCurrentSimulatedDay());
                    }
                }
            }
            iterationEvolution.iterate();
        }
    }

    private void runGraphicalEnvironment() {

        graphicalEnvironment.initialize(population, iterationEvolution);
        graphicalEnvironment.setVisible(true);

        while (!iterationEvolution.hasSimulationFinished()) {
            sleepFor(GRAPHICS_UPDATE_TIME_MS);
            graphicalEnvironment.getImagePanel(population, iterationEvolution).repaint();
        }
    }

    private void sleepFor(Integer timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
