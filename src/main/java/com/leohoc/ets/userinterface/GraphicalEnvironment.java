package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.Individual;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicalEnvironment extends JFrame {

    public static final String TITLE = "Epidemic Transmission Simulation";
    private JPanel jContentPane = null;
    private JPanel imagePanel = null;

    public GraphicalEnvironment(final Integer mapSize, final List<Individual> population) {
        super();
        initialize(mapSize, population);
    }

    private void initialize(final Integer mapSize, final List<Individual> population) {
        this.setSize(mapSize, mapSize);
        this.setContentPane(getJContentPane(population));
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public JPanel getJContentPane(final List<Individual> population) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getImagePanel(population), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public JPanel getImagePanel(final List<Individual> population) {
        if (imagePanel == null) {
            imagePanel = new JPanel() {

                @Override
                public void paint(Graphics g) {

                super.paint(g);

                for (Individual individual : population) {
                    g.setColor(individual.getColor());
                    g.fillRect(individual.getX(), individual.getY(), individual.getWidth(), individual.getHeight());
                }
                }
            };
        }
        return imagePanel;
    }
}
