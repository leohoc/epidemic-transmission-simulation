package com.leohoc.ets.userinterface;

import com.leohoc.ets.simulation.IterationEvolution;
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

public class GraphicalEnvironment {

    private static final int GRAPHICS_Y_AXIS_START_POINT = 0;
    private static final String TITLE = "Epidemic Transmission Simulation";
    private static final String NOT_EXPOSED = "Not exposed: %s";
    private static final String CURRENT_INFECTED = "Current infected: %s";
    private static final String CURRENT_HOSPITALIZED = "Current hospitalized: %s";
    private static final String CURRENT_SIMULATION_DAY = "Current Simulation Day: %s";
    private static final String TOTAL_INFECTED = "Total infected: %s";
    private static final String TOTAL_HOSPITALIZED = "Total hospitalized: %s";
    private static final String TOTAL_RECOVERED = "Total recovered: %s";
    private static final String TOTAL_DEAD = "Total dead: %s";

    private JFrame jFrame = null;
    private JPanel jContentPane = null;
    private JPanel imagePanel = null;
    private final SimulationGraphicsProperties graphicsProperties;
    private final int availableBeds;
    private final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = new HashMap<>();

    public GraphicalEnvironment(final SimulationGraphicsProperties graphicsProperties,
                                final int availableBeds) {
        this.graphicsProperties = graphicsProperties;
        this.availableBeds = availableBeds;
    }

    public void initialize(final List<Individual> population,
                           final IterationEvolution iterationEvolution,
                           final EpidemicStatistics epidemicStatistics) {
        final int totalWidth = graphicsProperties.getMapWidth() + graphicsProperties.getAreaChartWidth();
        jFrame = new JFrame();
        jFrame.setSize(totalWidth, graphicsProperties.getMapHeight());
        jFrame.setContentPane(getJContentPane(population, iterationEvolution, epidemicStatistics));
        jFrame.setTitle(TITLE);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
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
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentHospitalizedCount(), getColorFor(HOSPITALIZED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentInfectedCount(), getColorFor(INFECTED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getCurrentNormalCount(), getColorFor(HealthStatus.NORMAL)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getTotalRecoveredCount(), getColorFor(RECOVERED)));
        areaChartContent.add(new AreaChartElement(epidemicStatistics.getTotalDeadCount(), getColorFor(DEAD)));
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
        graphics.fillRect(graphicsProperties.getMapWidth(), GRAPHICS_Y_AXIS_START_POINT, graphicsProperties.getAreaChartWidth(), graphicsProperties.getMapHeight());
    }

    protected AreaChart buildAreaChart(final Integer totalIterations, final Integer populationSize) {
        return new AreaChart(
                buildAreaChartPanel(),
                totalIterations,
                populationSize,
                areaChartContentByIteration,
                availableBeds);
    }

    private Rectangle buildAreaChartPanel() {
        return new Rectangle(graphicsProperties.getMapWidth(), GRAPHICS_Y_AXIS_START_POINT, graphicsProperties.getAreaChartWidth(), graphicsProperties.getAreaChartHeight());
    }

    private void drawCountInformation(final Graphics graphics, final EpidemicStatistics epidemicStatistics, final Integer currentSimulatedDay) {
        graphics.setColor(Color.WHITE);
        graphics.drawString(format(NOT_EXPOSED, epidemicStatistics.getCurrentNormalCount()), graphicsProperties.getCurrentInfoX(), graphicsProperties.getCurrentNotExposedY());
        graphics.drawString(format(CURRENT_INFECTED, epidemicStatistics.getCurrentInfectedCount()), graphicsProperties.getCurrentInfoX(), graphicsProperties.getCurrentInfectedY());
        graphics.drawString(format(CURRENT_HOSPITALIZED, epidemicStatistics.getCurrentHospitalizedCount()), graphicsProperties.getCurrentInfoX(), graphicsProperties.getCurrentHospitalizedY());
        graphics.drawString(format(CURRENT_SIMULATION_DAY, currentSimulatedDay), graphicsProperties.getCurrentInfoX(), graphicsProperties.getCurrentSimulationDayY());
        graphics.drawString(format(TOTAL_INFECTED, epidemicStatistics.getTotalInfectedCount()), graphicsProperties.getTotalInfoX(), graphicsProperties.getTotalInfectedY());
        graphics.drawString(format(TOTAL_HOSPITALIZED, epidemicStatistics.getTotalHospitalizedCount()), graphicsProperties.getTotalInfoX(), graphicsProperties.getTotalHospitalizedY());
        graphics.drawString(format(TOTAL_RECOVERED, epidemicStatistics.getTotalRecoveredCount()), graphicsProperties.getTotalInfoX(), graphicsProperties.getTotalRecoveredY());
        graphics.drawString(format(TOTAL_DEAD, epidemicStatistics.getTotalDeadCount()), graphicsProperties.getTotalInfoX(), graphicsProperties.getTotalDeadY());
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
