package com.leohoc.ets.userinterface;

import com.leohoc.ets.infrastructure.config.AreaChartProperties;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class AreaChart {

    private static final int ELEMENT_WIDTH = 1;

    private final AreaChartProperties properties;
    private final long totalIterations;
    private final int totalItemsCount;
    private final int boundaryLineCount;

    public AreaChart(final AreaChartProperties areaChartProperties,
                     final long totalIterations,
                     final int totalItemsCount,
                     final int boundaryLineCount) {
        this.properties = areaChartProperties;
        this.totalIterations = totalIterations;
        this.totalItemsCount = totalItemsCount;
        this.boundaryLineCount = boundaryLineCount;
    }

    public void draw(final Graphics graphics, final Map<Integer, List<AreaChartElement>> iterationsContent) {
        for (Integer iteration : iterationsContent.keySet()) {
            drawIterationContent(graphics, iteration, iterationsContent);
        }
        drawBoundaryLine(graphics);
    }

    private void drawIterationContent(final Graphics graphics, final Integer iteration, final Map<Integer, List<AreaChartElement>> iterationsContent) {
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
        final BigDecimal count = new BigDecimal(element.getCount());
        return calculateY(count);
    }

    private int calculateElementYStartPoint(final int elementsAccumulatedY, final int elementHeight) {
        return properties.getY() + (properties.getHeight() - elementsAccumulatedY - elementHeight);
    }

    private int calculateElementXStartPoint(final Integer iteration) {
        return properties.getX() + (int) ((iteration * properties.getWidth()) / totalIterations);
    }

    private void drawBoundaryLine(final Graphics graphics) {
        final int boundaryLineStartX = properties.getX();
        final int boundaryLineEndX = (properties.getX() + properties.getWidth());
        final int boundaryLineY = calculateBoundaryLineY();
        graphics.setColor(Color.YELLOW);
        graphics.drawLine(boundaryLineStartX, boundaryLineY, boundaryLineEndX, boundaryLineY);
    }

    private int calculateBoundaryLineY() {
        final BigDecimal count = new BigDecimal(boundaryLineCount);
        return properties.getHeight() - calculateY(count);
    }

    private int calculateY(BigDecimal count) {
        final BigDecimal height = BigDecimal.valueOf(properties.getHeight());
        final BigDecimal total = new BigDecimal(totalItemsCount);
        return count.multiply(height).divide(total, RoundingMode.UP).intValue();
    }
}
