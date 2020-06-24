package com.leohoc.ets;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.infrastructure.config.HealthSystemCapacityProperties;
import com.leohoc.ets.simulation.*;
import com.leohoc.ets.infrastructure.config.SimulationPropertiesLoader;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.util.RandomUtil;

import java.security.SecureRandom;

public class ETSApplication {

	public static void main(String[] args) {

        SimulationPropertiesLoader propertiesLoader = new SimulationPropertiesLoader();
	    final RandomUtil randomUtil = new RandomUtil(new SecureRandom());
		final HealthSystemCapacityProperties healthSystemCapacityProperties = propertiesLoader.loadHealthSystemCapacityProperties();
		final HealthSystemResources healthSystemResources = new HealthSystemResources(healthSystemCapacityProperties.getAvailableBeds());
		final DiseaseBehavior diseaseBehavior = new DiseaseBehavior(propertiesLoader.loadEpidemicProperties(), healthSystemResources, randomUtil);
        final MovementBehavior movementBehavior = new MovementBehavior(propertiesLoader.loadMovementProperties(), randomUtil);
		final IterationEvolution iterationEvolution = new IterationEvolution(propertiesLoader.loadIterationsProperties());
        final EpidemicStatistics epidemicStatistics = new EpidemicStatistics();

        final PopulationDynamics populationDynamics = new PopulationDynamics(
                diseaseBehavior,
                movementBehavior,
                propertiesLoader.loadIndividualProperties(),
                randomUtil);

        final GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(
                propertiesLoader.loadGraphicsProperties(),
                healthSystemCapacityProperties.getAvailableBeds());

		final SimulationCoordinator simulationCoordinator = new SimulationCoordinator(
				propertiesLoader.loadSimulationProperties(),
				iterationEvolution,
				populationDynamics,
				graphicalEnvironment,
				epidemicStatistics);

		simulationCoordinator.startSimulation();
	}
}