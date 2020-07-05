package com.leohoc.ets.userinterface;

import com.leohoc.ets.simulation.IterationEvolution;
import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.infrastructure.config.GraphicsProperties;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicalEnvironment {

    private static final String TITLE = "Epidemic Transmission Simulation";

    private JFrame jFrame;
    private JPanel contentPanel;
    private final GraphicsProperties graphicsProperties;
    private final SimulationPanel simulationPanel;

    public GraphicalEnvironment(final GraphicsProperties graphicsProperties,
                                final SimulationPanel simulationPanel) {
        this(null, null, graphicsProperties, simulationPanel);
    }

    public GraphicalEnvironment(final JFrame jFrame,
                                final JPanel contentPanel,
                                final GraphicsProperties graphicsProperties,
                                final SimulationPanel simulationPanel) {
        this.jFrame = jFrame;
        this.contentPanel = contentPanel;
        this.graphicsProperties = graphicsProperties;
        this.simulationPanel = simulationPanel;
        this.initialize();
    }

    private void initialize() {
        if (jFrame == null) {
            jFrame = new JFrame();
            jFrame.setSize(graphicsProperties.getWidth(), graphicsProperties.getHeight());
            jFrame.setContentPane(buildContentPanel());
            jFrame.setTitle(TITLE);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
        }
    }

    private JPanel buildContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(simulationPanel);
        }
        return contentPanel;
    }

    public void repaint(final List<Individual> population,
                        final IterationEvolution iterationEvolution,
                        final EpidemicStatistics epidemicStatistics) {
        simulationPanel.updateSimulationElements(population, iterationEvolution, epidemicStatistics);
        simulationPanel.repaint();
    }
}
