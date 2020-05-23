package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties;
import com.leohoc.ets.infrastructure.config.SimulationEpidemicProperties;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 5;
    private static final Integer SIMULATION_UPDATE_TIME_MS = 10;
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);

    private GraphicalEnvironment graphicalEnvironment;
    private List<Individual> population = new ArrayList<>();
    private Integer iteration = 0;

    public void startSimulation() {
        generatePopulation();
        setInitialInfectedPopulation();
        new Thread() {
            @Override
            public void run() {
                runSimulation();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                runGraphicalEnvironment();
            }
        }.start();
    }

    private void generatePopulation() {
        for (int i = 0; i < SimulationEnvironmentProperties.getPopulationSize(); i++) {
            population.add(Individual.randomIndividual());
        }
    }

    private void setInitialInfectedPopulation() {
        BigDecimal initialInfectedPercent = BigDecimal.valueOf(SimulationEpidemicProperties.getInitialInfectedPercent());
        BigDecimal decimalInfectedRatio = initialInfectedPercent.divide(HUNDRED_PERCENT);
        BigDecimal populationSize = BigDecimal.valueOf(SimulationEnvironmentProperties.getPopulationSize());
        final int infectedIndividualsCount =  decimalInfectedRatio.multiply(populationSize).intValue();
        for (int i = 0; i < infectedIndividualsCount; i++) {
            population.get(RandomUtil.generateIntLessThan(SimulationEnvironmentProperties.getPopulationSize())).gotInfected();
        }
    }

    private void runGraphicalEnvironment() {

        graphicalEnvironment = new GraphicalEnvironment(SimulationEnvironmentProperties.getMapSize(), population, iteration);
        graphicalEnvironment.setVisible(true);

        while (true) {
            try {
                Thread.sleep(GRAPHICS_UPDATE_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            graphicalEnvironment.getImagePanel(population, iteration).repaint();
        }
    }

    private void runSimulation() {
        while (true) {
            try {
                Thread.sleep(SIMULATION_UPDATE_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Individual individual : population) {
                individual.move();
            }
            for (Individual individual : population) {
                for (Individual passerby : population) {
                    if (!individual.equals(passerby)) {
                        individual.interactionWith(passerby);
                    }
                }
            }
            iteration = iteration + 1;
        }
    }
}
