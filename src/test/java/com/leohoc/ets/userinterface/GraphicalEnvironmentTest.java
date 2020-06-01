package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;

import static com.leohoc.ets.builders.PropertiesBuilder.buildGraphicsProperties;
import static com.leohoc.ets.builders.PropertiesBuilder.buildIndividualProperties;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class GraphicalEnvironmentTest {

    @Test
    public void testDrawHealthyIndividual() {
        // Given
        final int x = 0;
        final int y = 0;
        Individual individual = new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = Mockito.spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, Mockito.times(1)).setColor(eq(Color.LIGHT_GRAY));
        verify(graphics, Mockito.times(1)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawInfectedIndividual() {
        // Given
        final int x = 0;
        final int y = 0;
        final int currentSimulationDay = 1;
        Individual individual = new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
        individual.gotInfected(currentSimulationDay);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = Mockito.spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, Mockito.times(1)).setColor(eq(Color.BLUE));
        verify(graphics, Mockito.times(1)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawRecoveredIndividual() {
        // Given
        final int x = 0;
        final int y = 0;
        final int currentSimulationDay = 1;
        Individual individual = new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
        individual.recovered(currentSimulationDay);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = Mockito.spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, Mockito.times(1)).setColor(eq(Color.GREEN));
        verify(graphics, Mockito.times(1)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    public void testDrawDeadIndividual() {
        // Given
        final int x = 0;
        final int y = 0;
        final int currentSimulationDay = 1;
        Individual individual = new Individual(x, y, DirectionMovement.STANDING, buildIndividualProperties());
        individual.died(currentSimulationDay);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(buildGraphicsProperties());

        // When
        Graphics graphics = Mockito.spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, Mockito.times(1)).setColor(eq(Color.RED));
        verify(graphics, Mockito.times(1)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }


}