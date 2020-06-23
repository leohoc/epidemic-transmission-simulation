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

	    final SecureRandom secureRandom = new SecureRandom();
	    final RandomUtil randomUtil = new RandomUtil(secureRandom);

		SimulationPropertiesLoader propertiesLoader = new SimulationPropertiesLoader();
		final HealthSystemCapacityProperties healthSystemCapacityProperties = propertiesLoader.loadHealthSystemCapacityProperties();
		final GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(propertiesLoader.loadGraphicsProperties(), healthSystemCapacityProperties.getAvailableBeds());
		final MovementBehavior movementBehavior = new MovementBehavior(propertiesLoader.loadMovementProperties(), randomUtil);
		final HealthSystemResources healthSystemResources = new HealthSystemResources(healthSystemCapacityProperties.getAvailableBeds());
		final DiseaseBehavior diseaseBehavior = new DiseaseBehavior(propertiesLoader.loadEpidemicProperties(), healthSystemResources, randomUtil);
		final IterationEvolution iterationEvolution = new IterationEvolution(propertiesLoader.loadIterationsProperties());
		final PopulationDynamics populationDynamics = new PopulationDynamics(diseaseBehavior, movementBehavior);
		final EpidemicStatistics epidemicStatistics = new EpidemicStatistics();

		SimulationCoordinator simulationCoordinator = new SimulationCoordinator(
				propertiesLoader.loadSimulationProperties(),
				propertiesLoader.loadIndividualProperties(),
				iterationEvolution,
				populationDynamics,
				graphicalEnvironment,
				movementBehavior,
				epidemicStatistics,
                randomUtil);

		simulationCoordinator.startSimulation();
	}
}