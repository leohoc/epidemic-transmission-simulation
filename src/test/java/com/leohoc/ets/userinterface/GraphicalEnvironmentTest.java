package com.leohoc.ets.userinterface;

import com.leohoc.ets.application.IterationEvolution;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.builders.PropertiesBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GraphicalEnvironmentTest {

    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int ONE_INVOCATION = 1;
    private static final int POPULATION_SIZE = 10;
    private static final int INITIAL_Y = 0;

    @Test
    public void testDrawHealthyIndividual() {
        // Given
        Individual individual = buildIndividual();
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.LIGHT_GRAY));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawInfectedIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.gotInfected(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.CYAN));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawRecoveredIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.recovered(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.GREEN));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawDeadIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.died(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.RED));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testBuildAreaChartContent() {
        // Given
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());
        EpidemicStatistics epidemicStatistics = buildEpidemicStatistics();

        // When
        List<AreaChartElement> areaChartContent = graphicalEnvironment.buildAreaChartContent(epidemicStatistics);

        // Then
        assertEquals(epidemicStatistics.getHospitalizedCount(), areaChartContent.get(0).getCount());
        assertEquals(Color.BLUE, areaChartContent.get(0).getColor());
        assertEquals(epidemicStatistics.getInfectedCount(), areaChartContent.get(1).getCount());
        assertEquals(Color.CYAN, areaChartContent.get(1).getColor());
        assertEquals(epidemicStatistics.getNormalCount(), areaChartContent.get(2).getCount());
        assertEquals(Color.LIGHT_GRAY, areaChartContent.get(2).getColor());
        assertEquals(epidemicStatistics.getRecoveredCount(), areaChartContent.get(3).getCount());
        assertEquals(Color.GREEN, areaChartContent.get(3).getColor());
        assertEquals(epidemicStatistics.getDeadCount(), areaChartContent.get(4).getCount());
        assertEquals(Color.RED, areaChartContent.get(4).getColor());
    }

    @Test
    public void testDrawChartPanel() {
        // Given
        SimulationGraphicsProperties properties = buildGraphicsProperties();
        GraphicalEnvironment graphicalEnvironment = Mockito.spy(new GraphicalEnvironment(properties));
        IterationEvolution iterationEvolution = new IterationEvolution(buildIterationsProperties());
        EpidemicStatistics epidemicStatistics = buildEpidemicStatistics();

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawChartPanel(graphics, iterationEvolution, epidemicStatistics, POPULATION_SIZE);
        Mockito.when(graphicalEnvironment.buildAreaChart(eq(iterationEvolution.getTotalIterations()), eq(POPULATION_SIZE))).thenReturn(mock(AreaChart.class));

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.DARK_GRAY));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(properties.getMapWidth()), eq(INITIAL_Y), eq(properties.getAreaChartWidth()), eq(properties.getMapHeight()));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.WHITE));
        verify(graphics, times(ONE_INVOCATION)).drawString(any(String.class), eq(properties.getMapWidth()), eq(properties.getInfectedCountY()));
        verify(graphics, times(ONE_INVOCATION)).drawString(any(String.class), eq(properties.getMapWidth()), eq(properties.getRecoveredCountY()));
        verify(graphics, times(ONE_INVOCATION)).drawString(any(String.class), eq(properties.getMapWidth()), eq(properties.getDeadCountY()));
        verify(graphics, times(ONE_INVOCATION)).drawString(any(String.class), eq(properties.getMapWidth()), eq(properties.getEpidemicRunningDaysY()));
    }

    @Test
    public void testBuildAreaChart() {
        // Given
        IterationEvolution iterationEvolution = new IterationEvolution(buildIterationsProperties());
        SimulationGraphicsProperties properties = buildGraphicsProperties();
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(properties);

        // When
        AreaChart areaChart = graphicalEnvironment.buildAreaChart(iterationEvolution.getTotalIterations(), POPULATION_SIZE);

        // Then
        assertEquals(properties.getMapWidth(), areaChart.getChartPanel().getX());
        assertEquals(INITIAL_Y, areaChart.getChartPanel().getY());
        assertEquals(properties.getAreaChartWidth(), areaChart.getChartPanel().getWidth());
        assertEquals(properties.getAreaChartHeight(), areaChart.getChartPanel().getHeight());
        assertEquals(iterationEvolution.getTotalIterations(), areaChart.getTotalIterations());
        assertEquals(POPULATION_SIZE, areaChart.getTotalItemsCount());
        assertEquals(new HashMap<Integer, List<AreaChartElement>>(), areaChart.getIterationsContent());
    }

    private Individual buildIndividual() {
        final int x = 0;
        final int y = 0;
        return new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
    }

    private EpidemicStatistics buildEpidemicStatistics() {
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        epidemicStatistics.updateStatistics(HealthStatus.NORMAL);
        epidemicStatistics.updateStatistics(HealthStatus.INFECTED);
        epidemicStatistics.updateStatistics(HealthStatus.RECOVERED);
        epidemicStatistics.updateStatistics(HealthStatus.DEAD);
        return epidemicStatistics;
    }
}