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
import static com.leohoc.ets.domain.enums.HealthStatus.DEAD;

public class SimulationPanel extends JPanel {

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
            for (Individual individual : population) {
                drawIndividual(graphics, individual);
            }
            areaChartContentByIteration.put(iterationEvolution.getCurrentIteration(), buildAreaChartContent(epidemicStatistics));
            chartPanel.draw(graphics, epidemicStatistics, areaChartContentByIteration, iterationEvolution.getCurrentSimulatedDay());
        }
    }

    private boolean simulationElementsInitialized() {
        return population != null && iterationEvolution != null && epidemicStatistics != null;
    }

    private void drawIndividual(final Graphics g, final Individual individual) {
        g.setColor(getColorFor(individual.getHealthCondition().getHealthStatus()));
        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
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

    private Color getColorFor(final HealthStatus healthStatus) {
        if (healthStatus.equals(HOSPITALIZED)) {
            return Color.BLUE;
        }
        if (healthStatus.equals(INFECTED)) {
            return Color.CYAN;
        }
        if (healthStatus.equals(RECOVERED)) {
            return Color.GREEN;
        }
        if (healthStatus.equals(DEAD)) {
            return Color.RED;
        }
        return Color.LIGHT_GRAY;
    }
}
