package com.leohoc.ets.userinterface;

import com.leohoc.ets.simulation.IterationEvolution;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.GraphicsProperties;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.leohoc.ets.domain.enums.HealthStatus.*;

public class GraphicalEnvironment {

    private static final String TITLE = "Epidemic Transmission Simulation";

    private JPanel jContentPane = null;
    private JPanel imagePanel = null;
    private final GraphicsProperties graphicsProperties;
    private final HashMap<Integer, List<AreaChartElement>> areaChartContentByIteration = new HashMap<>();
    private final ChartPanel chartPanel;

    public GraphicalEnvironment(final GraphicsProperties graphicsProperties,
                                final ChartPanel chartPanel) {
        this.graphicsProperties = graphicsProperties;
        this.chartPanel = chartPanel;
    }

    public void initialize(final List<Individual> population,
                           final IterationEvolution iterationEvolution,
                           final EpidemicStatistics epidemicStatistics) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(graphicsProperties.getWidth(), graphicsProperties.getHeight());
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

    public void repaint(final List<Individual> population,
                        final IterationEvolution iterationEvolution,
                        final EpidemicStatistics epidemicStatistics) {
        this.getImagePanel(population, iterationEvolution, epidemicStatistics).repaint();
    }

    private JPanel getImagePanel(final List<Individual> population,
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
                    chartPanel.draw(graphics, epidemicStatistics, areaChartContentByIteration, iterationEvolution.getCurrentSimulatedDay());
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
