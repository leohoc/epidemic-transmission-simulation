package com.leohoc.ets.userinterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class AreaChart {

    private static final int ELEMENT_WIDTH = 1;

    private final Rectangle chartPanel;
    private final long totalIterations;
    private final int totalItemsCount;
    private final HashMap<Integer, List<AreaChartElement>> iterationsContent;

    public AreaChart(final Rectangle chartPanel,
                     final long totalIterations,
                     final int totalItemsCount,
                     final HashMap<Integer, List<AreaChartElement>> iterationsContent) {
        this.chartPanel = chartPanel;
        this.totalIterations = totalIterations;
        this.totalItemsCount = totalItemsCount;
        this.iterationsContent = iterationsContent;
    }

    public void draw(final Graphics graphics) {
        for (Integer iteration : iterationsContent.keySet()) {
            drawIterationContent(graphics, iteration);
        }
    }

    private void drawIterationContent(Graphics graphics, Integer iteration) {
        List<AreaChartElement> iterationContent = iterationsContent.get(iteration);
        int elementsAccumulatedY = 0;

        for (AreaChartElement element : iterationContent) {
            int elementHeight = calculateElementHeight(element);
            int elementX = calculateElementXStartPoint(iteration);
            int elementY = calculateElementYStartPoint(elementsAccumulatedY, elementHeight);

            graphics.setColor(element.getColor());
            graphics.fillRect(elementX, elementY, ELEMENT_WIDTH, elementHeight);
            elementsAccumulatedY += elementHeight;
        }
    }

    private int calculateElementHeight(final AreaChartElement element) {
        return (int) (element.getCount() * chartPanel.getHeight()) / totalItemsCount;
    }

    private int calculateElementYStartPoint(final int elementsAccumulatedY, final int elementHeight) {
        return (int) chartPanel.getY() + (int) (chartPanel.getHeight() - elementsAccumulatedY - elementHeight);
    }

    private int calculateElementXStartPoint(final Integer iteration) {
        return (int) chartPanel.getX() + (int) ((iteration * chartPanel.getWidth()) / totalIterations);
    }
}
