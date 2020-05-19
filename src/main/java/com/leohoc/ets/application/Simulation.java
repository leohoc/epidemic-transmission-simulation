package com.leohoc.ets.application;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.SimulationProperties;
import com.leohoc.ets.userinterface.GraphicalEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private GraphicalEnvironment graphicalEnvironment;
    private SimulationProperties simulationProperties = new SimulationProperties();
    private List<Individual> population = new ArrayList<>();

    public void startSimulation() {

        generatePopulation();
        initializeGraphicalEnvironment();
    }

    private void generatePopulation() {
        Random random = new Random();
        for (int i = 0; i < simulationProperties.getPopulationSize(); i++) {
            population.add(generateIndividual(random));
        }
    }

    private Individual generateIndividual(Random random) {
        return new Individual(
                random.nextInt(simulationProperties.getMapSize()),
                random.nextInt(simulationProperties.getMapSize()),
                simulationProperties.getIndividualWidth(),
                simulationProperties.getIndividualHeight()
        );
    }

    private void initializeGraphicalEnvironment() {
        graphicalEnvironment = new GraphicalEnvironment(simulationProperties.getMapSize(), population);
        graphicalEnvironment.setVisible(true);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    graphicalEnvironment.getImagePanel(population).repaint();
                }
            }
        }.start();
    }
}
