package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.ChartPanelProperties;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static com.leohoc.ets.domain.enums.HealthStatus.*;
import static com.leohoc.ets.userinterface.SimulationPanel.getColorFor;

public class ChartPanel {

    private static final int CHART_HEIGHT = 850;
    private static final int STATS_PADDING = 12;
    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 11);
    private static final Font STAT_FONT   = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font DAY_FONT    = new Font("SansSerif", Font.BOLD, 13);

    private final ChartPanelProperties properties;
    private final AreaChart areaChart;

    public ChartPanel(final ChartPanelProperties properties, final AreaChart areaChart) {
        this.properties = properties;
        this.areaChart = areaChart;
    }

    public void draw(final Graphics graphics,
                     final EpidemicStatistics epidemicStatistics,
                     final Map<Integer, List<AreaChartElement>> areaChartContentByIteration,
                     final Integer currentSimulatedDay) {
        drawBackgroundPanel(graphics);
        drawAreaChart(graphics, areaChartContentByIteration);
        drawCountInformation(graphics, epidemicStatistics, currentSimulatedDay);
    }

    private void drawBackgroundPanel(final Graphics graphics) {
        graphics.setColor(new Color(25, 25, 35));
        graphics.fillRect(properties.getX(), properties.getY(), properties.getWidth(), properties.getHeight());
    }

    private void drawCountInformation(final Graphics graphics, final EpidemicStatistics epidemicStatistics, final Integer currentSimulatedDay) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelX = properties.getX();
        int statsY  = CHART_HEIGHT + 5;
        int halfW   = properties.getWidth() / 2;
        int leftX   = panelX + STATS_PADDING;
        int rightX  = panelX + halfW + STATS_PADDING;

        // Separator line
        g2d.setColor(new Color(70, 70, 95));
        g2d.drawLine(panelX + 5, statsY, panelX + properties.getWidth() - 5, statsY);

        // Vertical divider between columns
        g2d.drawLine(panelX + halfW, statsY + 5, panelX + halfW, properties.getY() + properties.getHeight() - 5);

        // Day indicator
        g2d.setFont(DAY_FONT);
        g2d.setColor(new Color(210, 210, 230));
        g2d.drawString("Day " + currentSimulatedDay, leftX + 70, statsY + 20);

        // ── Left column: CURRENT ──────────────────────────────────────────
        g2d.setFont(HEADER_FONT);
        g2d.setColor(new Color(170, 170, 200));
        g2d.drawString("CURRENT", leftX, statsY + 38);

        drawStatLine(g2d, leftX, statsY + 56, getColorFor(NORMAL),       "Healthy",      epidemicStatistics.getCurrentNormalCount());
        drawStatLine(g2d, leftX, statsY + 73, getColorFor(INFECTED),     "Infected",     epidemicStatistics.getCurrentInfectedCount());
        drawStatLine(g2d, leftX, statsY + 90, getColorFor(HOSPITALIZED), "Hospitalized", epidemicStatistics.getCurrentHospitalizedCount());

        // ── Right column: TOTAL ───────────────────────────────────────────
        g2d.setFont(HEADER_FONT);
        g2d.setColor(new Color(170, 170, 200));
        g2d.drawString("TOTAL", rightX, statsY + 38);

        drawStatLine(g2d, rightX, statsY + 56, getColorFor(INFECTED),     "Infected",     epidemicStatistics.getTotalInfectedCount());
        drawStatLine(g2d, rightX, statsY + 73, getColorFor(HOSPITALIZED), "Hospitalized", epidemicStatistics.getTotalHospitalizedCount());
        drawStatLine(g2d, rightX, statsY + 90, getColorFor(RECOVERED),    "Recovered",    epidemicStatistics.getTotalRecoveredCount());
        drawStatLine(g2d, rightX, statsY + 107, getColorFor(DEAD),        "Dead",         epidemicStatistics.getTotalDeadCount());
    }

    private void drawStatLine(final Graphics2D g2d, final int x, final int y,
                               final Color indicatorColor, final String label, final int value) {
        g2d.setColor(indicatorColor);
        g2d.fillRoundRect(x, y - 9, 10, 10, 3, 3);
        g2d.setFont(STAT_FONT);
        g2d.setColor(Color.WHITE);
        g2d.drawString(label + ": " + value, x + 14, y);
    }

    private void drawAreaChart(final Graphics graphics, final Map<Integer, List<AreaChartElement>> areaChartContentByIteration) {
        areaChart.draw(graphics, areaChartContentByIteration);
    }
}
