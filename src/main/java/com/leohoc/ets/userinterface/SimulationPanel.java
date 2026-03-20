package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.simulation.IterationEvolution;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.domain.enums.HealthStatus.*;

public class SimulationPanel extends JPanel {

    static final Color COLOR_NORMAL      = new Color(150, 180, 210);
    static final Color COLOR_INFECTED    = new Color(255, 140,   0);
    static final Color COLOR_HOSPITALIZED = new Color(220,  20,  60);
    static final Color COLOR_RECOVERED   = new Color( 50, 205,  50);
    static final Color COLOR_DEAD        = new Color( 90,  90,  90);

    private final transient HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = new HashMap<>();
    private final transient ChartPanel chartPanel;
    private transient List<Individual> population;
    private transient IterationEvolution iterationEvolution;
    private transient EpidemicStatistics epidemicStatistics;

    public SimulationPanel(final ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public void updateSimulationElements(final List<Individual> population,
                                         final IterationEvolution iterationEvolution,
                                         final EpidemicStatistics epidemicStatistics) {
        this.population = population;
        this.iterationEvolution = iterationEvolution;
        this.epidemicStatistics = epidemicStatistics;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        if (simulationElementsInitialized()) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (Individual individual : population) {
                drawIndividual(g2d, individual);
            }
            areaChartContentByIteration.put(iterationEvolution.getCurrentIteration(), buildAreaChartContent(epidemicStatistics));
            chartPanel.draw(graphics, epidemicStatistics, areaChartContentByIteration, iterationEvolution.getCurrentSimulatedDay());
        }
    }

    private boolean simulationElementsInitialized() {
        return population != null && iterationEvolution != null && epidemicStatistics != null;
    }

    private void drawIndividual(final Graphics2D g2d, final Individual individual) {
        g2d.setColor(getColorFor(individual.getHealthCondition().getHealthStatus()));
        int x = individual.getX();
        int y = individual.getY();
        if (individual.getHealthStatus() == DEAD) {
            drawDeadIndividual(g2d, x, y);
        } else {
            drawStandingIndividual(g2d, x, y);
        }
    }

    private void drawStandingIndividual(final Graphics2D g2d, final int x, final int y) {
        g2d.fillOval(x, y, 4, 4);                         // head
        g2d.drawLine(x + 2, y + 4, x + 2, y + 9);        // body
        g2d.drawLine(x - 1, y + 5, x + 5, y + 5);        // arms
        g2d.drawLine(x + 2, y + 9, x - 1, y + 13);       // left leg
        g2d.drawLine(x + 2, y + 9, x + 5, y + 13);       // right leg
    }

    private void drawDeadIndividual(final Graphics2D g2d, final int x, final int y) {
        g2d.fillOval(x - 3, y + 1, 4, 4);                // head (to the left)
        g2d.drawLine(x + 1, y + 3, x + 9, y + 3);        // horizontal body
    }

    private List<AreaChartElement> buildAreaChartContent(final EpidemicStatistics epidemicStatistics) {
        List<AreaChartElement> areaChartContent = new ArrayList<>();
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentHospitalizedCount(), getColorFor(HOSPITALIZED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentInfectedCount(), getColorFor(INFECTED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentNormalCount(), getColorFor(HealthStatus.NORMAL)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getTotalRecoveredCount(), getColorFor(RECOVERED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getTotalDeadCount(), getColorFor(DEAD)));
        return areaChartContent;
    }

    static Color getColorFor(final HealthStatus healthStatus) {
        if (healthStatus.equals(HOSPITALIZED)) return COLOR_HOSPITALIZED;
        if (healthStatus.equals(INFECTED))     return COLOR_INFECTED;
        if (healthStatus.equals(RECOVERED))    return COLOR_RECOVERED;
        if (healthStatus.equals(DEAD))         return COLOR_DEAD;
        return COLOR_NORMAL;
    }
}
