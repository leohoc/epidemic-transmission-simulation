package com.leohoc.ets.userinterface;

import com.leohoc.ets.application.IterationEvolution;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphicalEnvironment extends JFrame {

    private static final String TITLE = "Epidemic Transmission Simulation";
    private static final int GRAPHICS_Y_AXIS_START_POINT = 0;
    private static final String TOTAL_INFECTED = "Total infected:";
    private static final String TOTAL_RECOVERED = "Total recovered:";
    private static final String TOTAL_DEAD = "Total dead:";

    private JPanel jContentPane = null;
    private JPanel imagePanel = null;
    private final SimulationGraphicsProperties properties;
    private final HashMap<Integer, List<AreaChartElement>> populationHealthConditionEvolution = new HashMap<>();

    public GraphicalEnvironment(final SimulationGraphicsProperties properties) {
        super();
        this.properties = properties;
    }

    public void initialize(final List<Individual> population, final IterationEvolution simulationTimeEvolution) {
        final int totalWidth = properties.getMapWidth() + properties.getAreaChartWidth();
        this.setSize(totalWidth, properties.getMapHeight());
        this.setContentPane(getJContentPane(population, simulationTimeEvolution));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JPanel getJContentPane(final List<Individual> population, final IterationEvolution simulationTimeEvolution) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getImagePanel(population, simulationTimeEvolution), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public JPanel getImagePanel(final List<Individual> population, final IterationEvolution simulationTimeEvolution) {
        if (imagePanel == null) {
            imagePanel = new JPanel() {

                @Override
                public void paint(Graphics g) {

                    super.paint(g);

                    int infectedCount = 0;
                    int normalCount = 0;
                    int recoveredCount = 0;
                    int deadCount = 0;

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
                        if (individual.getHealthStatus().equals(HealthStatus.DEAD)) {
                            deadCount++;
                        }
                    }

                    drawChartPanel(g, infectedCount, normalCount, recoveredCount, deadCount, simulationTimeEvolution, population.size());
                }
            };
        }
        return imagePanel;
    }

    private void drawIndividual(Graphics g, Individual individual) {
        g.setColor(individual.getHealthCondition().getHealthStatus().getColor());
        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
    }

    private void drawChartPanel(final Graphics g,
                                final int infectedCount,
                                final int normalCount,
                                final int recoveredCount,
                                final int deadCount,
                                final IterationEvolution simulationTimeEvolution,
                                final int populationCount) {

        drawBackgroundPanel(g);
        drawAreaChart(g, infectedCount, normalCount, recoveredCount, deadCount, simulationTimeEvolution, populationCount);
        drawCountInformation(g, infectedCount, recoveredCount, deadCount);
    }

    private void drawBackgroundPanel(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(properties.getMapWidth(), GRAPHICS_Y_AXIS_START_POINT, properties.getAreaChartWidth(), properties.getMapHeight());
    }


    private void drawAreaChart(final Graphics g,
                               final int infectedCount,
                               final int normalCount,
                               final int recoveredCount,
                               final int deadCount,
                               final IterationEvolution simulationTimeEvolution,
                               final int populationCount) {
        List<AreaChartElement> currentPopulationHealthCondition = buildPopulationHealthConditionEvolution(infectedCount, normalCount, recoveredCount, deadCount);
        populationHealthConditionEvolution.put(simulationTimeEvolution.getCurrentSimulatedDay(), currentPopulationHealthCondition);

        AreaChart areaChart = new AreaChart(
                properties.getMapWidth(),
                GRAPHICS_Y_AXIS_START_POINT,
                properties.getAreaChartWidth(),
                properties.getAreaChartHeight(),
                populationHealthConditionEvolution,
                simulationTimeEvolution.getSimulatedTotalDays(),
                properties.getAreaChartElementWidth(),
                populationCount);
        areaChart.draw(g);
    }

    private void drawCountInformation(Graphics g, int infectedCount, int recoveredCount, int deadCount) {
        g.setColor(Color.WHITE);
        g.drawString(TOTAL_INFECTED + infectedCount, properties.getMapWidth(), properties.getInfectedCountY());
        g.drawString(TOTAL_RECOVERED + recoveredCount, properties.getMapWidth(), properties.getRecoveredCountY());
        g.drawString(TOTAL_DEAD + deadCount, properties.getMapWidth(), properties.getDeadCountY());
    }

    private List<AreaChartElement> buildPopulationHealthConditionEvolution(final int infectedCount,
                                                                           final int normalCount,
                                                                           final int recoveredCount,
                                                                           final int deadCount) {

        List<AreaChartElement> instantPopulationHealthCondition = new ArrayList<>();
        instantPopulationHealthCondition.add(new AreaChartElement(infectedCount, HealthStatus.INFECTED.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(normalCount, HealthStatus.NORMAL.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(recoveredCount, HealthStatus.RECOVERED.getColor()));
        instantPopulationHealthCondition.add(new AreaChartElement(deadCount, HealthStatus.DEAD.getColor()));
        return instantPopulationHealthCondition;
    }
}
