package com.leohoc.ets.userinterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class AreaChart {

    private static final int HUNDRED_PERCENT = 100;
    private final int chartX;
    private final int chartY;
    private final int chartWidth;
    private final int chartHeight;
    private final HashMap<Long, List<AreaChartElement>> chartContent;
    private final int simulationTotalTimeInMs;
    private final int elementWidth;

    public AreaChart(final int chartX,
                     final int chartY,
                     final int chartWidth,
                     final int chartHeight,
                     final HashMap<Long, List<AreaChartElement>> chartContent,
                     final int simulationTotalTimeInMs,
                     final int elementWidth) {
        this.chartX = chartX;
        this.chartY = chartY;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.chartContent = chartContent;
        this.simulationTotalTimeInMs = simulationTotalTimeInMs;
        this.elementWidth = elementWidth;
    }

    public void draw(Graphics g) {
        for (Long simulationInstant : chartContent.keySet()) {

            List<AreaChartElement> instantElements = chartContent.get(simulationInstant);
            int elementsAccumulatedY = 0;

            for (AreaChartElement element : instantElements) {

                int elementHeight = calculateElementHeight(element);
                int elementX = calculateElementXStartPoint(simulationInstant);
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
        return chartX + (int) ((simulationInstant * chartWidth) / simulationTotalTimeInMs);
    }

    private int calculateElementHeight(AreaChartElement element) {
        return (element.getPercentage() * chartHeight) / HUNDRED_PERCENT;
    }
}
