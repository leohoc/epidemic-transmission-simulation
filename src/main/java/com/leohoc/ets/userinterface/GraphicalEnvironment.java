package com.leohoc.ets.userinterface;

import com.leohoc.ets.application.SimulationTimeEvolution;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties.getMapSize;
import static com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties.*;

public class GraphicalEnvironment extends JFrame {

    private static final String TITLE = "Epidemic Transmission Simulation";
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);
    private static final int GRAPHICS_Y_AXIS_START_POINT = 0;

    private JPanel jContentPane = null;
    private JPanel imagePanel = null;

    HashMap<Long, List<AreaChartElement>> populationHealthConditionEvolution = new HashMap<>();

    public GraphicalEnvironment(final List<Individual> population, final SimulationTimeEvolution simulationTimeEvolution) {
        super();
        initialize(population, simulationTimeEvolution);
    }

    private void initialize(final List<Individual> population, final SimulationTimeEvolution simulationTimeEvolution) {
        final int totalWidth = getMapSize() + getAreaChartWidth();
        this.setSize(totalWidth, getMapSize());
        this.setContentPane(getJContentPane(population, simulationTimeEvolution));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JPanel getJContentPane(final List<Individual> population, final SimulationTimeEvolution simulationTimeEvolution) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getImagePanel(population, simulationTimeEvolution), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public JPanel getImagePanel(final List<Individual> population, final SimulationTimeEvolution simulationTimeEvolution) {
        if (imagePanel == null) {
            imagePanel = new JPanel() {

                @Override
                public void paint(Graphics g) {

                    super.paint(g);

                    int infectedCount = 0;
                    int normalCount = 0;
                    int recoveredCount = 0;

                    for (Individual individual : population) {

                        drawIndividual(g, individual);

                        if (individual.getHealthStatus().equals(HealthStatus.INFECTED)) {
                            infectedCount++;
                        }
                        if (individual.getHealthStatus().equals(HealthStatus.NORMAL)) {
                            normalCount++;
                        }
                        if (individual.getHealthStatus().equals(HealthStatus.RECOVERED)) {
                            recoveredCount++;
                        }
                    }

                    drawChartPanel(g, infectedCount, normalCount, recoveredCount, simulationTimeEvolution);
                }
            };
        }
        return imagePanel;
    }

    private void drawIndividual(Graphics g, Individual individual) {
        g.setColor(individual.getHealthCondition().getHealthStatus().getColor());
        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
    }

    private void drawChartPanel(final Graphics g, final int infectedCount, final int normalCount, final int recoveredCount, final SimulationTimeEvolution simulationTimeEvolution) {

        drawBackgroundPanel(g);

        List<AreaChartElement> currentPopulationHealthCondition = buildPopulationHealthConditionEvolution(infectedCount, normalCount, recoveredCount);
        populationHealthConditionEvolution.put(simulationTimeEvolution.getInstantInSimulatedDays(), currentPopulationHealthCondition);

        AreaChart areaChart = new AreaChart(
                                    getMapSize(),
                                    GRAPHICS_Y_AXIS_START_POINT,
                                    getAreaChartWidth(),
                                    getAreaChartHeight(),
                                    populationHealthConditionEvolution,
                                    simulationTimeEvolution.getSimulatedTotalDays(),
                                    getAreaChartElementWidth());
        areaChart.draw(g);
    }

    private void drawBackgroundPanel(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(getMapSize(), GRAPHICS_Y_AXIS_START_POINT, getAreaChartWidth(), getMapSize());
    }

    private List<AreaChartElement> buildPopulationHealthConditionEvolution(int infectedCount, int normalCount, int recoveredCount) {

        final int infectedPercentage = calculatePercentage(infectedCount, SimulationEnvironmentProperties.getPopulationSize());
        final int normalPercentage = calculatePercentage(normalCount, SimulationEnvironmentProperties.getPopulationSize());
        final int recoveredPercentage = calculatePercentage(recoveredCount, SimulationEnvironmentProperties.getPopulationSize());

        List<AreaChartElement> instantPopulationHealthCondition = new ArrayList<>();
        instantPopulationHealthCondition.add(new AreaChartElement(infectedPercentage, HealthStatus.INFECTED.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(normalPercentage, HealthStatus.NORMAL.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(recoveredPercentage, HealthStatus.RECOVERED.getColor()));
        return instantPopulationHealthCondition;
    }

    private int calculatePercentage(int count, int totalCount) {
        return BigDecimal.valueOf(count).multiply(HUNDRED_PERCENT).divide(BigDecimal.valueOf(totalCount), RoundingMode.HALF_UP).intValue();
    }
}
