package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties.getBigDecimalPopulationSize;
import static com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties.getPopulationSize;
import static com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties.*;
import static java.math.RoundingMode.HALF_UP;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 5;
    private static final Integer SIMULATION_UPDATE_TIME_MS = 10;
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);
    private static final int SCALE = 5;

    private final List<Individual> population = new ArrayList<>();
    private final SimulationTimeEvolution simulationTimeEvolution = new SimulationTimeEvolution(getTotalTimeInMs(), getSimulatedDayDurationInMs());

    public void startSimulation() {
        generatePopulation();
        setInitialInfectedPopulation();
        new Thread(this::runSimulation).start();
        new Thread(this::runGraphicalEnvironment).start();
    }

    private void generatePopulation() {
        for (int i = 0; i < getPopulationSize(); i++) {
            population.add(Individual.randomIndividual());
        }
    }

    private void setInitialInfectedPopulation() {
        final int infectedIndividualsCount = calculateInfectedIndividualsCount();
        for (int i = 0; i < infectedIndividualsCount; i++) {
            population.get(RandomUtil.generateIntLessThan(getPopulationSize())).gotInfected();
        }
    }

    private int calculateInfectedIndividualsCount() {
        BigDecimal decimalInfectedRatio = getInitialInfectedPercent().divide(HUNDRED_PERCENT, SCALE, HALF_UP);
        return decimalInfectedRatio.multiply(getBigDecimalPopulationSize()).intValue();
    }

    private void runSimulation() {

        while (!simulationTimeEvolution.hasSimulationFinished()) {

            sleepFor(SIMULATION_UPDATE_TIME_MS);

            for (Individual individual : population) {
                individual.move();
                for (Individual passerby : population) {
                    if (!individual.equals(passerby)) {
                        individual.interactionWith(passerby);
                    }
                }
            }
        }
    }

    private void runGraphicalEnvironment() {

        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(population, simulationTimeEvolution);
        graphicalEnvironment.setVisible(true);

        while (!simulationTimeEvolution.hasSimulationFinished()) {
            sleepFor(GRAPHICS_UPDATE_TIME_MS);
            graphicalEnvironment.getImagePanel(population, simulationTimeEvolution).repaint();
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
