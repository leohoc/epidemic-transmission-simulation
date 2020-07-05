package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.IndividualProperties;
import com.leohoc.ets.simulation.IterationEvolution;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leohoc.ets.domain.enums.DirectionMovement.STANDING;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SimulationPanelTest {

    private static final int CURRENT_ITERATION = 5;
    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int FIVE_INVOCATIONS = 5;
    private static final int ONE_INVOCATION = 1;
    private static final int INDIVIDUAL_X = 1;
    private static final int INDIVIDUAL_Y = 2;
    private static final int ONE_INDIVIDUAL = 1;

    @Test
    void testPaintWithSimulationElementsNotInitialized() {
        // Given
        Graphics graphics = Mockito.spy(Graphics.class);
        ChartPanel chartPanel = mock(ChartPanel.class);
        SimulationPanel simulationPanel = new SimulationPanel(chartPanel);

        // When
        simulationPanel.paint(graphics);

        // Then
        verify(graphics, never()).setColor(any(Color.class));
        verify(graphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(chartPanel, never()).draw(eq(graphics), any(EpidemicStatistics.class), any(HashMap.class), anyInt());
    }

    @Test
    void testPaint() {
        // Given
        ArgumentCaptor<Map<Integer, List<AreaChartElement>>> areaChartContentByIteration = ArgumentCaptor.forClass(Map.class);
        IterationEvolution iterationEvolution = mock(IterationEvolution.class);
        EpidemicStatistics epidemicStatistics = mock(EpidemicStatistics.class);
        Graphics graphics = Mockito.spy(Graphics.class);
        ChartPanel chartPanel = mock(ChartPanel.class);
        SimulationPanel simulationPanel = new SimulationPanel(chartPanel);

        // When
        mockMethodsOf(iterationEvolution);
        mockMethodsOf(epidemicStatistics);
        simulationPanel.updateSimulationElements(buildPopulation(), iterationEvolution, epidemicStatistics);
        simulationPanel.paint(graphics);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.LIGHT_GRAY));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.CYAN));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.BLUE));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.GREEN));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.RED));
        verify(graphics, times(FIVE_INVOCATIONS)).fillRect(eq(INDIVIDUAL_X), eq(INDIVIDUAL_Y), anyInt(), anyInt());
        verify(chartPanel, times(ONE_INVOCATION)).draw(eq(graphics), eq(epidemicStatistics), areaChartContentByIteration.capture(), eq(CURRENT_SIMULATED_DAY));
        assertContentOf(areaChartContentByIteration);
    }

    private void mockMethodsOf(IterationEvolution iterationEvolution) {
        when(iterationEvolution.getCurrentIteration()).thenReturn(CURRENT_ITERATION);
        when(iterationEvolution.getCurrentSimulatedDay()).thenReturn(CURRENT_SIMULATED_DAY);
    }

    private void mockMethodsOf(EpidemicStatistics epidemicStatistics) {
        when(epidemicStatistics.getCurrentNormalCount()).thenReturn(ONE_INDIVIDUAL);
        when(epidemicStatistics.getCurrentInfectedCount()).thenReturn(ONE_INDIVIDUAL);
        when(epidemicStatistics.getCurrentHospitalizedCount()).thenReturn(ONE_INDIVIDUAL);
        when(epidemicStatistics.getTotalRecoveredCount()).thenReturn(ONE_INDIVIDUAL);
        when(epidemicStatistics.getTotalDeadCount()).thenReturn(ONE_INDIVIDUAL);
    }

    private void assertContentOf(ArgumentCaptor<Map<Integer, List<AreaChartElement>>> areaChartContentByIteration) {
        List<AreaChartElement> areaChartElements = areaChartContentByIteration.getValue().get(CURRENT_ITERATION);
        assertTrue(areaChartElements.contains(new AreaChartElement(ONE_INDIVIDUAL, Color.LIGHT_GRAY)));
        assertTrue(areaChartElements.contains(new AreaChartElement(ONE_INDIVIDUAL, Color.CYAN)));
        assertTrue(areaChartElements.contains(new AreaChartElement(ONE_INDIVIDUAL, Color.BLUE)));
        assertTrue(areaChartElements.contains(new AreaChartElement(ONE_INDIVIDUAL, Color.GREEN)));
        assertTrue(areaChartElements.contains(new AreaChartElement(ONE_INDIVIDUAL, Color.RED)));
    }

    private List<Individual> buildPopulation() {

        Individual healthyIndividual = buildIndividual();

        Individual infectedIndividual = buildIndividual();
        infectedIndividual.gotInfected(CURRENT_SIMULATED_DAY);

        Individual hospitalizedIndividual = buildIndividual();
        hospitalizedIndividual.gotHospitalized(CURRENT_SIMULATED_DAY);

        Individual recoveredIndividual = buildIndividual();
        recoveredIndividual.recovered(CURRENT_SIMULATED_DAY);

        Individual deadIndividual = buildIndividual();
        deadIndividual.died(CURRENT_SIMULATED_DAY);

        return Arrays.asList(healthyIndividual, infectedIndividual, hospitalizedIndividual, recoveredIndividual, deadIndividual);
    }

    private Individual buildIndividual() {
        return new Individual(INDIVIDUAL_X, INDIVIDUAL_Y, STANDING, mock(IndividualProperties.class));
    }

}