package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 20;

    private final EpidemicStatistics epidemicStatistics = new EpidemicStatistics();;
    private final List<Individual> population = new ArrayList<>();
    private final SimulationProperties simulationProperties;
    private final IterationEvolution iterationEvolution;
    private final DiseaseBehavior diseaseBehavior;
    private final GraphicalEnvironment graphicalEnvironment;

    public Simulation(final SimulationPropertiesLoader simulationPropertiesLoader) {
        this.simulationProperties = simulationPropertiesLoader.loadSimulationProperties();
        this.iterationEvolution = new IterationEvolution(simulationPropertiesLoader.loadIterationsProperties());
        this.diseaseBehavior = new DiseaseBehavior(simulationPropertiesLoader.loadEpidemicProperties());
        this.graphicalEnvironment = new GraphicalEnvironment(simulationPropertiesLoader.loadGraphicsProperties());
        this.generatePopulation(simulationPropertiesLoader.loadIndividualProperties());
    }

    private void generatePopulation(final SimulationIndividualProperties individualProperties) {
        for (int i = 0; i < simulationProperties.getPopulationSize(); i++) {
            Individual individual = Individual.randomIndividual(individualProperties);
            if (shouldGotInfected(simulationProperties.getInitialInfectedPercent())) {
                individual.gotInfected(iterationEvolution.getCurrentSimulatedDay());
            }
            population.add(individual);
        }
    }

    private boolean shouldGotInfected(final int initialInfectedPercent) {
        return RandomUtil.generatePercent() <= initialInfectedPercent;
    }

    public void startSimulation() {
        new Thread(() -> runSimulation()).start();
        new Thread(() -> runGraphicalEnvironment()).start();
    }

    private void runSimulation() {

        while (!iterationEvolution.hasSimulationFinished()) {

            EpidemicStatistics iterationStatistics = new EpidemicStatistics();

            for (Individual individual : population) {

                individual.move();
                diseaseBehavior.updateHealthCondition(individual, iterationEvolution.getCurrentSimulatedDay());

                for (Individual passerby : population) {
                    if (!individual.equals(passerby)) {
                        diseaseBehavior.interactionBetween(individual, passerby, iterationEvolution.getCurrentSimulatedDay());
                    }
                }
                iterationStatistics.updateStatistics(individual.getHealthStatus());
            }
            epidemicStatistics.updateAllStatistics(iterationStatistics.getHealthStatusStatistic());
            iterationEvolution.iterate();
        }
    }

    private void runGraphicalEnvironment() {
        graphicalEnvironment.initialize(population, iterationEvolution, epidemicStatistics);
        graphicalEnvironment.setVisible(true);

        while (!iterationEvolution.hasSimulationFinished()) {
            sleepFor(GRAPHICS_UPDATE_TIME_MS);
            graphicalEnvironment.getImagePanel(population, iterationEvolution, epidemicStatistics).repaint();
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
