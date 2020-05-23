package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthCondition;
import com.leohoc.ets.infrastructure.config.SimulationEnvironmentProperties;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GraphicalEnvironment extends JFrame {

    public static final String TITLE = "Epidemic Transmission Simulation";
    private static final int CHART_PANEL_WIDTH = 400;
    public static final int HUNDRED_PERCENT = 100;
    private JPanel jContentPane = null;
    private JPanel imagePanel = null;
    private Integer mapSize = null;
    private long startTime = Calendar.getInstance().getTimeInMillis();
    HashMap<Long, List<AreaChartElement>> populationHealthConditionEvolution = new HashMap<>();

    public GraphicalEnvironment(final Integer mapSize, final List<Individual> population, final Integer iteration) {
        super();
        this.mapSize = mapSize;
        initialize(population, iteration);
    }

    private void initialize(final List<Individual> population, final Integer iteration) {
        this.setSize(mapSize + CHART_PANEL_WIDTH, mapSize);
        this.setContentPane(getJContentPane(population, iteration));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JPanel getJContentPane(final List<Individual> population, final Integer iteration) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getImagePanel(population, iteration), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public JPanel getImagePanel(final List<Individual> population, final int iteration) {
        if (imagePanel == null) {
            imagePanel = new JPanel() {

                @Override
                public void paint(Graphics g) {

                    super.paint(g);

                    int infectedCount = 0;
                    int normalCount = 0;

                    for (Individual individual : population) {

                        g.setColor(individual.getHealthCondition().getColor());
                        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());

                        if (individual.getHealthCondition().equals(HealthCondition.INFECTED)) {
                            infectedCount++;
                        }
                        if (individual.getHealthCondition().equals(HealthCondition.NORMAL)) {
                            normalCount++;
                        }
                    }

                    drawChartPanel(g, infectedCount, normalCount);
                }
            };
        }
        return imagePanel;
    }

    private void drawChartPanel(Graphics g, int infectedCount, int normalCount) {

        g.setColor(Color.DARK_GRAY);
        g.fillRect(mapSize, 0, CHART_PANEL_WIDTH, mapSize);

        int infectedPercentage = calculatePercentage(infectedCount, SimulationEnvironmentProperties.getPopulationSize());
        int normalPercentage = calculatePercentage(normalCount, SimulationEnvironmentProperties.getPopulationSize());

        List<AreaChartElement> instantPopulationHealthCondition = new ArrayList<>();
        instantPopulationHealthCondition.add(new AreaChartElement(infectedPercentage, HealthCondition.INFECTED.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(normalPercentage, HealthCondition.NORMAL.getColor()));

        long currentTime = Calendar.getInstance().getTimeInMillis();
        populationHealthConditionEvolution.put(currentTime - startTime, instantPopulationHealthCondition);

        AreaChart areaChart = new AreaChart(mapSize, 0, CHART_PANEL_WIDTH, CHART_PANEL_WIDTH, populationHealthConditionEvolution);
        areaChart.draw(g);
    }

    private int calculatePercentage(int count, int totalCount) {
        return (count * HUNDRED_PERCENT) / totalCount;
    }
}
