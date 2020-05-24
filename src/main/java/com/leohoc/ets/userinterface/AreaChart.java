package com.leohoc.ets.userinterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class AreaChart {

    private final int chartX;
    private final int chartY;
    private final int chartWidth;
    private final int chartHeight;
    private final HashMap<Long, List<AreaChartElement>> chartContent;
    private final long simulationTotalIterations;
    private final int elementWidth;
    private final int totalItemsCount;

    public AreaChart(final int chartX,
                     final int chartY,
                     final int chartWidth,
                     final int chartHeight,
                     final HashMap<Long, List<AreaChartElement>> chartContent,
                     final long simulationTotalIterations,
                     final int elementWidth,
                     final int totalItemsCount) {
        this.chartX = chartX;
        this.chartY = chartY;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.chartContent = chartContent;
        this.simulationTotalIterations = simulationTotalIterations;
        this.elementWidth = elementWidth;
        this.totalItemsCount = totalItemsCount;
    }

    public void draw(Graphics g) {
        for (Long simulationIteration : chartContent.keySet()) {

            List<AreaChartElement> instantElements = chartContent.get(simulationIteration);
            int elementsAccumulatedY = 0;

            for (AreaChartElement element : instantElements) {

                int elementHeight = calculateElementHeight(element);
                int elementX = calculateElementXStartPoint(simulationIteration);
                int elementY = calculateElementYStartPoint(elementsAccumulatedY, elementHeight);

                g.setColor(element.getColor());
                g.fillRect(elementX, elementY, elementWidth, elementHeight);
                elementsAccumulatedY += elementHeight;
            }
        }
    }

    private int calculateElementYStartPoint(int elementsAccumulatedY, int elementHeight) {
        return chartY + (chartHeight - elementsAccumulatedY - elementHeight);
    }

    private int calculateElementXStartPoint(Long simulationInstant) {
        return chartX + (int) ((simulationInstant * chartWidth) / simulationTotalIterations);
    }

    private int calculateElementHeight(AreaChartElement element) {
        return (element.getCount() * chartHeight) / totalItemsCount;
    }
}
