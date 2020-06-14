package com.leohoc.ets.userinterface;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AreaChartTest {

    private static final int TOTAL_ITERATIONS = 4;
    private static final int TOTAL_ITEMS_COUNT = 4;
    private static final int ONE_INVOCATION = 1;
    private static final int BOUNDARY_LINE_COUNT = 2;

    @Test
    void testDraw() {
        // Given
        Rectangle chartPanel = buildChartPanel();
        HashMap<Integer, List<AreaChartElement>> iterationsContent = buildIterationsContent();
        AreaChart areaChart = new AreaChart(chartPanel, TOTAL_ITERATIONS, TOTAL_ITEMS_COUNT, iterationsContent, BOUNDARY_LINE_COUNT);

        // When
        Graphics graphics = Mockito.spy(Graphics.class);
        areaChart.draw(graphics);

        // Then
        Mockito.verify(graphics, Mockito.times(TOTAL_ITERATIONS)).setColor(Color.GREEN);
        Mockito.verify(graphics, Mockito.times(TOTAL_ITERATIONS)).setColor(Color.WHITE);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(0, 0, 1, 4);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(0, 0, 1, 0);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(1, 1, 1, 3);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(1, 0, 1, 1);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(2, 2, 1, 2);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(2, 0, 1, 2);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(3, 3, 1, 1);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).fillRect(3, 0, 1, 3);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).setColor(Color.YELLOW);
        Mockito.verify(graphics, Mockito.times(ONE_INVOCATION)).drawLine(0, 2, 4, 2);

    }

    private Rectangle buildChartPanel() {
        final int x = 0;
        final int y = 0;
        final int width = 4;
        final int height = 4;
        return new Rectangle(x, y, width, height);
    }

    private HashMap<Integer, List<AreaChartElement>> buildIterationsContent() {

        final HashMap<Integer, List<AreaChartElement>> iterationsContent = new HashMap<>();

        List<AreaChartElement> firstIterationElements = new ArrayList<>();
        firstIterationElements.add(new AreaChartElement(4, Color.GREEN));
        firstIterationElements.add(new AreaChartElement(0, Color.WHITE));
        iterationsContent.put(0, firstIterationElements);

        List<AreaChartElement> secondIterationElements = new ArrayList<>();
        secondIterationElements.add(new AreaChartElement(3, Color.GREEN));
        secondIterationElements.add(new AreaChartElement(1, Color.WHITE));
        iterationsContent.put(1, secondIterationElements);

        List<AreaChartElement> thirdIterationElements = new ArrayList<>();
        thirdIterationElements.add(new AreaChartElement(2, Color.GREEN));
        thirdIterationElements.add(new AreaChartElement(2, Color.WHITE));
        iterationsContent.put(2, thirdIterationElements);

        List<AreaChartElement> fourthIterationElements = new ArrayList<>();
        fourthIterationElements.add(new AreaChartElement(1, Color.GREEN));
        fourthIterationElements.add(new AreaChartElement(3, Color.WHITE));
        iterationsContent.put(3, fourthIterationElements);

        return iterationsContent;
    }
}