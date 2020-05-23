package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties;
import com.leohoc.ets.userinterface.GraphicalEnvironment;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private static final Integer GRAPHICS_UPDATE_TIME_MS = 50;

    private GraphicalEnvironment graphicalEnvironment;
    private List<Individual> population = new ArrayList<>();

    public void startSimulation() {
        generatePopulation();
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

    private void runGraphicalEnvironment() {

        graphicalEnvironment = new GraphicalEnvironment(SimulationEnvironmentProperties.getMapSize(), population);
        graphicalEnvironment.setVisible(true);

        while (true) {
            try {
                Thread.sleep(GRAPHICS_UPDATE_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            graphicalEnvironment.getImagePanel(population).repaint();
        }
    }

    private void runSimulation() {
        while (true) {
            try {
                Thread.sleep(GRAPHICS_UPDATE_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Individual individual : population) {
                individual.move();
            }
        }
    }
}
