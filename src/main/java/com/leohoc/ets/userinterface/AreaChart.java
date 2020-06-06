package com.leohoc.ets.userinterface;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class AreaChart {

    private static final int ELEMENT_WIDTH = 1;

    private final Rectangle chartPanel;
    private final long totalIterations;
    private final int totalItemsCount;
    private final HashMap<Integer, List<AreaChartElement>> iterationsContent;
    private final int boundaryLineCount;

    public AreaChart(final Rectangle chartPanel,
                     final long totalIterations,
                     final int totalItemsCount,
                     final HashMap<Integer, List<AreaChartElement>> iterationsContent,
                     final int boundaryLineCount) {
        this.chartPanel = chartPanel;
        this.totalIterations = totalIterations;
        this.totalItemsCount = totalItemsCount;
        this.iterationsContent = iterationsContent;
        this.boundaryLineCount = boundaryLineCount;
    }

    protected Rectangle getChartPanel() {
        return chartPanel;
    }

    protected long getTotalIterations() {
        return totalIterations;
    }

    protected int getTotalItemsCount() {
        return totalItemsCount;
    }

    protected HashMap<Integer, List<AreaChartElement>> getIterationsContent() {
        return iterationsContent;
    }

    public void draw(final Graphics graphics) {
        for (Integer iteration : iterationsContent.keySet()) {
            drawIterationContent(graphics, iteration);
        }
        drawBoundaryLine(graphics);
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
        final BigDecimal count = new BigDecimal(element.getCount());
        return calculateY(count);
    }

    private int calculateElementYStartPoint(final int elementsAccumulatedY, final int elementHeight) {
        return (int) chartPanel.getY() + (int) (chartPanel.getHeight() - elementsAccumulatedY - elementHeight);
    }

    private int calculateElementXStartPoint(final Integer iteration) {
        return (int) chartPanel.getX() + (int) ((iteration * chartPanel.getWidth()) / totalIterations);
    }

    private void drawBoundaryLine(Graphics graphics) {
        final int boundaryLineStartX = (int) chartPanel.getX();
        final int boundaryLineEndX = (int) (chartPanel.getX() + chartPanel.getWidth());
        final int boundaryLineY = calculateBoundaryLineY();
        graphics.setColor(Color.YELLOW);
        graphics.drawLine(boundaryLineStartX, boundaryLineY, boundaryLineEndX, boundaryLineY);
    }

    private int calculateBoundaryLineY() {
        final BigDecimal count = new BigDecimal(boundaryLineCount);
        return (int)getChartPanel().getHeight() - calculateY(count);
    }

    private int calculateY(BigDecimal count) {
        final BigDecimal height = new BigDecimal(chartPanel.getHeight());
        final BigDecimal total = new BigDecimal(totalItemsCount);
        return count.multiply(height).divide(total, RoundingMode.UP).intValue();
    }

}
