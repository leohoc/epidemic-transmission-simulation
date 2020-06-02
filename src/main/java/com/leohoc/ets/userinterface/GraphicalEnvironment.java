package com.leohoc.ets.userinterface;

import com.leohoc.ets.application.IterationEvolution;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.SimulationGraphicsProperties;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.domain.enums.HealthStatus.*;
import static java.lang.String.format;

public class GraphicalEnvironment extends JFrame {

    private static final int GRAPHICS_Y_AXIS_START_POINT = 0;
    private static final String TITLE = "Epidemic Transmission Simulation";
    private static final String TOTAL_INFECTED = "Total infected: %s";
    private static final String TOTAL_RECOVERED = "Total recovered: %s";
    private static final String TOTAL_DEAD = "Total dead: %s";
    private static final String EPIDEMIC_RUNNING_DAYS = "Epidemic running days: %s";

    private JPanel jContentPane = null;
    private JPanel imagePanel = null;
    private final SimulationGraphicsProperties properties;
    private final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = new HashMap<>();

    public GraphicalEnvironment(final SimulationGraphicsProperties properties) {
        super();
        this.properties = properties;
    }

    public void initialize(final List<Individual> population,
                           final IterationEvolution iterationEvolution,
                           final EpidemicStatistics epidemicStatistics) {
        final int totalWidth = properties.getMapWidth() + properties.getAreaChartWidth();
        this.setSize(totalWidth, properties.getMapHeight());
        this.setContentPane(getJContentPane(population, iterationEvolution, epidemicStatistics));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JPanel getJContentPane(final List<Individual> population,
                                   final IterationEvolution iterationEvolution,
                                   final EpidemicStatistics epidemicStatistics) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getImagePanel(population, iterationEvolution, epidemicStatistics), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public JPanel getImagePanel(final List<Individual> population,
                                final IterationEvolution iterationEvolution,
                                final EpidemicStatistics epidemicStatistics) {

        if (imagePanel == null) {
            imagePanel = new JPanel() {

                @Override
                public void paint(Graphics graphics) {

                    super.paint(graphics);

                    for (Individual individual : population) {
                        drawIndividual(graphics, individual);
                    }
                    areaChartContentByIteration.put(iterationEvolution.getCurrentIteration(), buildAreaChartContent(epidemicStatistics));
                    drawChartPanel(graphics, iterationEvolution, epidemicStatistics, population.size());
                }
            };
        }
        return imagePanel;
    }

    protected void drawIndividual(final Graphics g, final Individual individual) {
        g.setColor(getColorFor(individual.getHealthCondition().getHealthStatus()));
        g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
    }

    protected List<AreaChartElement> buildAreaChartContent(final EpidemicStatistics epidemicStatistics) {
        List<AreaChartElement> areaChartContent = new ArrayList<>();
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getInfectedCount(), getColorFor(INFECTED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getNormalCount(), getColorFor(HealthStatus.NORMAL)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getRecoveredCount(), getColorFor(RECOVERED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getDeadCount(), getColorFor(DEAD)));
        return areaChartContent;
    }

    protected void drawChartPanel(final Graphics graphics,
                                final IterationEvolution iterationEvolution,
                                final EpidemicStatistics epidemicStatistics,
                                final Integer populationSize) {
        drawBackgroundPanel(graphics);
        buildAreaChart(iterationEvolution.getTotalIterations(), populationSize).draw(graphics);
        drawCountInformation(graphics, epidemicStatistics, iterationEvolution.getCurrentSimulatedDay());
    }

    private void drawBackgroundPanel(final Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(properties.getMapWidth(), GRAPHICS_Y_AXIS_START_POINT, properties.getAreaChartWidth(), properties.getMapHeight());
    }

    protected AreaChart buildAreaChart(final Integer totalIterations, final Integer populationSize) {
        return new AreaChart(
                buildAreaChartPanel(),
                totalIterations,
                populationSize,
                areaChartContentByIteration);
    }

    private Rectangle buildAreaChartPanel() {
        return new Rectangle(properties.getMapWidth(), GRAPHICS_Y_AXIS_START_POINT, properties.getAreaChartWidth(), properties.getAreaChartHeight());
    }

    private void drawCountInformation(final Graphics graphics, final EpidemicStatistics epidemicStatistics, final Integer currentSimulatedDay) {
        graphics.setColor(Color.WHITE);
        graphics.drawString(format(TOTAL_INFECTED, epidemicStatistics.getInfectedCount()), properties.getMapWidth(), properties.getInfectedCountY());
        graphics.drawString(format(TOTAL_RECOVERED, epidemicStatistics.getRecoveredCount()), properties.getMapWidth(), properties.getRecoveredCountY());
        graphics.drawString(format(TOTAL_DEAD, epidemicStatistics.getDeadCount()), properties.getMapWidth(), properties.getDeadCountY());
        graphics.drawString(format(EPIDEMIC_RUNNING_DAYS, currentSimulatedDay), properties.getMapWidth(), properties.getEpidemicRunningDaysY());
    }

    private Color getColorFor(final HealthStatus healthStatus) {
        if (healthStatus.equals(INFECTED)) {
            return Color.BLUE;
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
