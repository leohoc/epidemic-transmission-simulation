package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.infrastructure.config.ChartPanelProperties;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ChartPanelTest {

    private static final int X = 0;
    private static final int Y = 0;
    private static final int WIDTH = 4;
    private static final int HEIGHT = 4;

    private static final int CURRENT_INFO_X = 0;
    private static final int CURRENT_NOT_EXPOSED_Y = 0;
    private static final int CURRENT_INFECTED_Y = 1;
    private static final int CURRENT_HOSPITALIZED_Y = 2;
    private static final int CURRENT_SIMULATION_DAY_Y = 3;

    private static final int TOTAL_INFO_X = 2;
    private static final int TOTAL_INFECTED_Y = 0;
    private static final int TOTAL_HOSPITALIZED_Y = 1;
    private static final int TOTAL_RECOVERED_Y = 2;
    private static final int TOTAL_DEAD_Y = 3;

    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int ONE_INVOCATION = 1;

    private final ChartPanelProperties properties = mock(ChartPanelProperties.class);
    private final AreaChart areaChart = mock(AreaChart.class);
    private final Graphics graphics = mock(Graphics.class);
    private final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = mock(HashMap.class);

    @Test
    void testDraw() {
        // Given
        ChartPanel chartPanel = new ChartPanel(properties, areaChart);

        // When
        mockAreaProperties();
        mockCurrentInfoProperties();
        mockTotalInfoProperties();
        chartPanel.draw(graphics, mock(EpidemicStatistics.class), areaChartContentByIteration, CURRENT_SIMULATED_DAY);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.DARK_GRAY));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(X), eq(Y), eq(WIDTH), eq(HEIGHT));
        verify(areaChart, times(ONE_INVOCATION)).draw(eq(graphics), eq(areaChartContentByIteration));
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.WHITE));
        verifyCurrentInfoDrawing();
        verifyTotalInfoDrawing();
    }

    private void mockAreaProperties() {
        when(properties.getX()).thenReturn(X);
        when(properties.getY()).thenReturn(Y);
        when(properties.getWidth()).thenReturn(WIDTH);
        when(properties.getHeight()).thenReturn(HEIGHT);
    }

    private void mockCurrentInfoProperties() {
        when(properties.getCurrentInfoX()).thenReturn(CURRENT_INFO_X);
        when(properties.getCurrentNotExposedY()).thenReturn(CURRENT_NOT_EXPOSED_Y);
        when(properties.getCurrentInfectedY()).thenReturn(CURRENT_INFECTED_Y);
        when(properties.getCurrentHospitalizedY()).thenReturn(CURRENT_HOSPITALIZED_Y);
        when(properties.getCurrentSimulationDayY()).thenReturn(CURRENT_SIMULATION_DAY_Y);
    }

    private void mockTotalInfoProperties() {
        when(properties.getTotalInfoX()).thenReturn(TOTAL_INFO_X);
        when(properties.getTotalInfectedY()).thenReturn(TOTAL_INFECTED_Y);
        when(properties.getTotalHospitalizedY()).thenReturn(TOTAL_HOSPITALIZED_Y);
        when(properties.getTotalRecoveredY()).thenReturn(TOTAL_RECOVERED_Y);
        when(properties.getTotalDeadY()).thenReturn(TOTAL_DEAD_Y);
    }

    private void verifyCurrentInfoDrawing() {
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(CURRENT_INFO_X), eq(CURRENT_NOT_EXPOSED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(CURRENT_INFO_X), eq(CURRENT_INFECTED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(CURRENT_INFO_X), eq(CURRENT_HOSPITALIZED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(CURRENT_INFO_X), eq(CURRENT_SIMULATION_DAY_Y));
    }

    private void verifyTotalInfoDrawing() {
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(TOTAL_INFO_X), eq(TOTAL_INFECTED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(TOTAL_INFO_X), eq(TOTAL_HOSPITALIZED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(TOTAL_INFO_X), eq(TOTAL_RECOVERED_Y));
        verify(graphics, times(ONE_INVOCATION)).drawString(anyString(), eq(TOTAL_INFO_X), eq(TOTAL_DEAD_Y));
    }
}