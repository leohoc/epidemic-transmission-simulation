package com.leohoc.ets.userinterface;

import com.leohoc.ets.domain.entity.EpidemicStatistics;
import com.leohoc.ets.domain.entity.Individual;
import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.GraphicsProperties;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static com.leohoc.ets.generators.PropertiesGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GraphicalEnvironmentTest {

    private static final int CURRENT_SIMULATED_DAY = 1;
    private static final int ONE_INVOCATION = 1;

    private final GraphicsProperties graphicsProperties = mock(GraphicsProperties.class);
    private final ChartPanel chartPanel = mock(ChartPanel.class);

    @Test
    void testDrawHealthyIndividual() {
        // Given
        Individual individual = buildIndividual();
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.LIGHT_GRAY));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    void testDrawInfectedIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.gotInfected(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.CYAN));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    void testDrawHospitalizedIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.gotHospitalized(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.BLUE));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    void testDrawRecoveredIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.recovered(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.GREEN));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    void testDrawDeadIndividual() {
        // Given
        Individual individual = buildIndividual();
        individual.died(CURRENT_SIMULATED_DAY);
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);

        // When
        Graphics graphics = spy(Graphics.class);
        graphicalEnvironment.drawIndividual(graphics, individual);

        // Then
        verify(graphics, times(ONE_INVOCATION)).setColor(eq(Color.RED));
        verify(graphics, times(ONE_INVOCATION)).fillRect(eq(individual.getX()), eq(individual.getY()), eq(individual.getWidth()), eq(individual.getHeight()));
    }

    @Test
    void testBuildAreaChartContent() {
        // Given
        GraphicalEnvironment graphicalEnvironment = new GraphicalEnvironment(graphicsProperties, chartPanel);
        EpidemicStatistics epidemicStatistics = buildEpidemicStatistics();

        // When
        List<AreaChartElement> areaChartContent = graphicalEnvironment.buildAreaChartContent(epidemicStatistics);

        // Then
        assertEquals(epidemicStatistics.getCurrentHospitalizedCount(), areaChartContent.get(0).getCount());
        assertEquals(Color.BLUE, areaChartContent.get(0).getColor());
        assertEquals(epidemicStatistics.getCurrentInfectedCount(), areaChartContent.get(1).getCount());
        assertEquals(Color.CYAN, areaChartContent.get(1).getColor());
        assertEquals(epidemicStatistics.getCurrentNormalCount(), areaChartContent.get(2).getCount());
        assertEquals(Color.LIGHT_GRAY, areaChartContent.get(2).getColor());
        assertEquals(epidemicStatistics.getTotalRecoveredCount(), areaChartContent.get(3).getCount());
        assertEquals(Color.GREEN, areaChartContent.get(3).getColor());
        assertEquals(epidemicStatistics.getTotalDeadCount(), areaChartContent.get(4).getCount());
        assertEquals(Color.RED, areaChartContent.get(4).getColor());
    }

    private Individual buildIndividual() {
        final int x = 0;
        final int y = 0;
        return new Individual(x, y, DirectionMovement.STANDING, generateIndividualProperties());
    }

    private EpidemicStatistics buildEpidemicStatistics() {
        EpidemicStatistics epidemicStatistics = new EpidemicStatistics();
        epidemicStatistics.updateStatistics(HealthStatus.NORMAL);
        epidemicStatistics.updateStatistics(HealthStatus.INFECTED);
        epidemicStatistics.updateStatistics(HealthStatus.HOSPITALIZED);
        epidemicStatistics.updateStatistics(HealthStatus.RECOVERED);
        epidemicStatistics.updateStatistics(HealthStatus.DEAD);
        return epidemicStatistics;
    }
}