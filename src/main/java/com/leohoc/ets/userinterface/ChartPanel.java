package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.infrastructure.config.ChartPanelProperties;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

public class ChartPanel {

    private static final String NOT_EXPOSED = "Not exposed: %s";
    private static final String CURRENT_INFECTED = "Current infected: %s";
    private static final String CURRENT_HOSPITALIZED = "Current hospitalized: %s";
    private static final String CURRENT_SIMULATION_DAY = "Current Simulation Day: %s";
    private static final String TOTAL_INFECTED = "Total infected: %s";
    private static final String TOTAL_HOSPITALIZED = "Total hospitalized: %s";
    private static final String TOTAL_RECOVERED = "Total recovered: %s";
    private static final String TOTAL_DEAD = "Total dead: %s";

    private final ChartPanelProperties properties;
    private final AreaChart areaChart;

    public ChartPanel(final ChartPanelProperties properties, final AreaChart areaChart) {
        this.properties = properties;
        this.areaChart = areaChart;
    }

    public void draw(final Graphics graphics,
                     final EpidemicStatistics epidemicStatistics,
                     final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration,
                     final Integer currentSimulatedDay) {
        drawBackgroundPanel(graphics);
        drawAreaChart(graphics, areaChartContentByIteration);
        drawCountInformation(graphics, epidemicStatistics, currentSimulatedDay);
    }

    private void drawBackgroundPanel(final Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(properties.getX(), properties.getY(), properties.getWidth(), properties.getHeight());
    }

    private void drawCountInformation(final Graphics graphics, final EpidemicStatistics epidemicStatistics, final Integer currentSimulatedDay) {
        graphics.setColor(Color.WHITE);
        graphics.drawString(format(NOT_EXPOSED, epidemicStatistics.getCurrentNormalCount()), properties.getCurrentInfoX(), properties.getCurrentNotExposedY());
        graphics.drawString(format(CURRENT_INFECTED, epidemicStatistics.getCurrentInfectedCount()), properties.getCurrentInfoX(), properties.getCurrentInfectedY());
        graphics.drawString(format(CURRENT_HOSPITALIZED, epidemicStatistics.getCurrentHospitalizedCount()), properties.getCurrentInfoX(), properties.getCurrentHospitalizedY());
        graphics.drawString(format(CURRENT_SIMULATION_DAY, currentSimulatedDay), properties.getCurrentInfoX(), properties.getCurrentSimulationDayY());
        graphics.drawString(format(TOTAL_INFECTED, epidemicStatistics.getTotalInfectedCount()), properties.getTotalInfoX(), properties.getTotalInfectedY());
        graphics.drawString(format(TOTAL_HOSPITALIZED, epidemicStatistics.getTotalHospitalizedCount()), properties.getTotalInfoX(), properties.getTotalHospitalizedY());
        graphics.drawString(format(TOTAL_RECOVERED, epidemicStatistics.getTotalRecoveredCount()), properties.getTotalInfoX(), properties.getTotalRecoveredY());
        graphics.drawString(format(TOTAL_DEAD, epidemicStatistics.getTotalDeadCount()), properties.getTotalInfoX(), properties.getTotalDeadY());
    }

    private void drawAreaChart(final Graphics graphics, final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration) {
        areaChart.draw(graphics, areaChartContentByIteration);
    }
}
