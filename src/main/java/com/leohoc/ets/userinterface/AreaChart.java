package com.leohoc.ets.userinterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class AreaChart {

    private static final int SIMULATION_TOTAL_TIME_IN_MS = 100000;
    private static final int HUNDRED_PERCENT = 100;
    private static final int ELEMENT_WIDTH = 1;
    private int chartX;
    private int chartY;
    private int chartWidth;
    private int chartHeight;
    private HashMap<Long, List<AreaChartElement>> chartContent;

    public AreaChart(int chartX, int chartY, int chartWidth, int chartHeight, HashMap<Long, List<AreaChartElement>> chartContent) {
        this.chartX = chartX;
        this.chartY = chartY;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.chartContent = chartContent;
    }

    public void draw(Graphics g) {
        for (Long simulationInstant : chartContent.keySet()) {

            List<AreaChartElement> instantElements = chartContent.get(simulationInstant);
            int elementsAccumulatedY = 0;

            for (AreaChartElement element : instantElements) {

                int elementX = (int) (simulationInstant * chartWidth) / SIMULATION_TOTAL_TIME_IN_MS;
                int elementHeight = (element.getPercentage() * chartHeight) / HUNDRED_PERCENT;

                g.setColor(element.getColor());
                g.fillRect(chartX + elementX, chartY + (chartHeight - elementsAccumulatedY - elementHeight), ELEMENT_WIDTH, elementHeight);
                elementsAccumulatedY += elementHeight;
            }
        }
    }
}
