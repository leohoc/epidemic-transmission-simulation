package com.leohoc.ets;

import com.leohoc.ets.application.Simulation;
import com.leohoc.ets.infrastructure.config.SimulationPropertiesLoader;

public class ETSApplication {

	public static void main(String[] args) {
		SimulationPropertiesLoader.loadProperties();
		Simulation simulation = new Simulation();
		simulation.startSimulation();
	}

}
