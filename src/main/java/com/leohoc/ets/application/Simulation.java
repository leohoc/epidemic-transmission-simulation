package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 25;

    private final EpidemicStatistics epidemicStatistics = new EpidemicStatistics();;
    private final List<Individual> population = new ArrayList<>();
    private final SimulationProperties simulationProperties;
    private final SimulationIndividualProperties individualProperties;
    private final IterationEvolution iterationEvolution;
    private final PopulationDynamics populationDynamics;
    private final GraphicalEnvironment graphicalEnvironment;

    public Simulation(final SimulationPropertiesLoader propertiesLoader) {
        final SimulationHealthSystemCapacityProperties healthSystemCapacityProperties = propertiesLoader.loadHealthSystemCapacityProperties();
        this.simulationProperties = propertiesLoader.loadSimulationProperties();
        this.individualProperties = propertiesLoader.loadIndividualProperties();
        this.iterationEvolution = new IterationEvolution(propertiesLoader.loadIterationsProperties());
        this.populationDynamics = new PopulationDynamics(new DiseaseBehavior(propertiesLoader.loadEpidemicProperties(), healthSystemCapacityProperties.getAvailableBeds()));
        this.graphicalEnvironment = new GraphicalEnvironment(propertiesLoader.loadGraphicsProperties(), healthSystemCapacityProperties.getAvailableBeds());
    }

    public void startSimulation() {
        this.population.addAll(generatePopulation());
        new Thread(() -> runSimulation()).start();
        new Thread(() -> runGraphicalEnvironment()).start();
    }

    protected List<Individual> generatePopulation() {
        List<Individual> initialPopulation = new ArrayList<>();
        for (int i = 0; i < simulationProperties.getPopulationSize(); i++) {
            Individual individual = Individual.randomIndividual(individualProperties);
            if (shouldGotInfected(simulationProperties.getInitialInfectedPercent())) {
                individual.gotInfected(iterationEvolution.getCurrentSimulatedDay());
            }
            initialPopulation.add(individual);
        }
        return initialPopulation;
    }

    protected boolean shouldGotInfected(final double initialInfectedPercent) {
        return RandomUtil.generatePercentWithTwoDigitsScale() < initialInfectedPercent;
    }

    protected void runSimulation() {
        do {
            runDynamicsIteration();
            iterationEvolution.iterate();
        } while (!iterationEvolution.hasSimulationFinished());
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
}
