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
    private static final int WIDTH = 400;
    private static final int HEIGHT = 1000;
    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int ONE_INVOCATION = 1;

    private final ChartPanelProperties properties = mock(ChartPanelProperties.class);
    private final AreaChart areaChart = mock(AreaChart.class);
    private final Graphics2D graphics = mock(Graphics2D.class);
    private final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = mock(HashMap.class);

    @Test
    void testDraw() {
        // Given
        ChartPanel chartPanel = new ChartPanel(properties, areaChart);

        // When
        mockAreaProperties();
        chartPanel.draw(graphics, mock(EpidemicStatistics.class), areaChartContentByIteration, CURRENT_SIMULATED_DAY);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(new Color(25, 25, 35)));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(X), eq(Y), eq(WIDTH), eq(HEIGHT));
        verify(areaChart, times(ONE_INVOCATION)).draw(eq(graphics), eq(areaChartContentByIteration));
        verify(graphics, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }

    private void mockAreaProperties() {
        when(properties.getX()).thenReturn(X);
        when(properties.getY()).thenReturn(Y);
        when(properties.getWidth()).thenReturn(WIDTH);
        when(properties.getHeight()).thenReturn(HEIGHT);
    }
}