package com.leohoc.ets;

import com.leohoc.ets.simulation.SimulationCoordinator;
import com.leohoc.ets.infrastructure.config.SimulationPropertiesLoader;

public class ETSApplication {

	public static void main(String[] args) {
		SimulationPropertiesLoader propertiesLoader = new SimulationPropertiesLoader();
		SimulationCoordinator simulationCoordinator = new SimulationCoordinator(propertiesLoader);
		simulationCoordinator.startSimulation();
	}

}
