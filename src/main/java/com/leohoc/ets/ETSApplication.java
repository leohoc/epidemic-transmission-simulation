package com.leohoc.ets;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.infrastructure.config.*;
import com.leohoc.ets.simulation.*;
import com.leohoc.ets.userinterface.AreaChart;
import com.leohoc.ets.userinterface.ChartPanel;
import com.leohoc.ets.userinterface.GraphicalEnvironment;
import com.leohoc.ets.userinterface.SimulationPanel;
import com.leohoc.ets.util.RandomUtil;

import java.security.SecureRandom;

public class ETSApplication {

	public static void main(String[] args) {

        SimulationPropertiesLoader propertiesLoader = new SimulationPropertiesLoader();
        final SimulationProperties simulationProperties = propertiesLoader.loadSimulationProperties();
	    final RandomUtil randomUtil = new RandomUtil(new SecureRandom());
		final HealthSystemCapacityProperties healthSystemCapacityProperties = propertiesLoader.loadHealthSystemCapacityProperties();
		final HealthSystemResources healthSystemResources = new HealthSystemResources(healthSystemCapacityProperties.getAvailableBeds());
		final DiseaseBehavior diseaseBehavior = new DiseaseBehavior(propertiesLoader.loadEpidemicProperties(), healthSystemResources, randomUtil);
        final MovementBehavior movementBehavior = new MovementBehavior(propertiesLoader.loadMovementProperties(), randomUtil);
		final IterationEvolution iterationEvolution = new IterationEvolution(propertiesLoader.loadIterationsProperties());

        final PopulationDynamics populationDynamics = new PopulationDynamics(
                diseaseBehavior,
                movementBehavior,
                propertiesLoader.loadIndividualProperties(),
                randomUtil);

        final AreaChart areaChart = new AreaChart(
                propertiesLoader.loadAreaChartProperties(),
                iterationEvolution.getTotalIterations(),
                simulationProperties.getPopulationSize(),
                healthSystemCapacityProperties.getAvailableBeds());

        final ChartPanel chartPanel = new ChartPanel(
                propertiesLoader.loadChartPanelProperties(),
                areaChart);

        final SimulationPanel simulationPanel = new SimulationPanel(chartPanel);

        final GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(
                propertiesLoader.loadGraphicsProperties(),
                simulationPanel);

		final SimulationCoordinator simulationCoordinator = new SimulationCoordinator(
                simulationProperties,
				iterationEvolution,
				populationDynamics,
				graphicalEnvironment,
                new EpidemicStatistics());

		simulationCoordinator.startSimulation();
	}
}