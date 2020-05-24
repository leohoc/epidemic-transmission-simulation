package com.leohoc.ets.userinterface;

import com.leohoc.ets.application.SimulationTimeEvolution;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthCondition;
import com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties.getMapSize;
import static com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties.*;

public class GraphicalEnvironment extends JFrame {

    private static final String TITLE = "Epidemic Transmission Simulation";
    private static final int HUNDRED_PERCENT = 100;
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

                    for (Individual individual : population) {

                        drawIndividual(g, individual);

                        if (individual.getHealthCondition().equals(HealthCondition.INFECTED)) {
                            infectedCount++;
                        }
                        if (individual.getHealthCondition().equals(HealthCondition.NORMAL)) {
                            normalCount++;
                        }
                    }

                    drawChartPanel(g, infectedCount, normalCount, simulationTimeEvolution);
                }
            };
        }
        return imagePanel;
    }

    private void drawIndividual(Graphics g, Individual individual) {
        g.setColor(individual.getHealthCondition().getColor());
        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
    }

    private void drawChartPanel(final Graphics g, final int infectedCount, final int normalCount, final SimulationTimeEvolution simulationTimeEvolution) {

        drawBackgroundPanel(g);

        List<AreaChartElement> currentPopulationHealthCondition = buildPopulationHealthConditionEvolution(infectedCount, normalCount);
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

    private List<AreaChartElement> buildPopulationHealthConditionEvolution(int infectedCount, int normalCount) {

        final int infectedPercentage = calculatePercentage(infectedCount, SimulationEnvironmentProperties.getPopulationSize());
        final int normalPercentage = calculatePercentage(normalCount, SimulationEnvironmentProperties.getPopulationSize());

        List<AreaChartElement> instantPopulationHealthCondition = new ArrayList<>();
        instantPopulationHealthCondition.add(new AreaChartElement(infectedPercentage, HealthCondition.INFECTED.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(normalPercentage, HealthCondition.NORMAL.getColor()));
        return instantPopulationHealthCondition;
    }

    private int calculatePercentage(int count, int totalCount) {
        return (count * HUNDRED_PERCENT) / totalCount;
    }
}
