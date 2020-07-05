package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.GraphicsProperties;
import com.leohoc.ets.simulation.IterationEvolution;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GraphicalEnvironmentTest {

    private static final int ONE_INVOCATION = 1;

    @Test
    void testRepaint() {
        // Given
        List<Individual> population = mock(List.class);
        IterationEvolution iterationEvolution = mock(IterationEvolution.class);
        EpidemicStatistics epidemicStatistics = mock(EpidemicStatistics.class);
        JFrame jFrame = mock(JFrame.class);
        JPanel contentPanel = mock(JPanel.class);
        GraphicsProperties graphicsProperties = mock(GraphicsProperties.class);
        SimulationPanel simulationPanel = mock(SimulationPanel.class);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(jFrame, contentPanel, graphicsProperties, simulationPanel);

        // When
        graphicalEnvironment.repaint(population, iterationEvolution, epidemicStatistics);

        // Then
        verify(simulationPanel, times(ONE_INVOCATION)).updateSimulationElements(eq(population), eq(iterationEvolution), eq(epidemicStatistics));
        verify(simulationPanel, times(ONE_INVOCATION)).repaint();
    }
}