package com.leohoc.ets.domain.entity;

import com.leohoc.ets.domain.enums.DirectionMovement;
import com.leohoc.ets.domain.enums.HealthStatus;
import com.leohoc.ets.infrastructure.config.IndividualProperties;

import static com.leohoc.ets.domain.enums.DirectionMovement.*;
import static com.leohoc.ets.domain.enums.HealthStatus.*;

public class Individual {

    private int x;
    private int y;
    private final int width;
    private final int height;
    private DirectionMovement directionMovement;
    private HealthCondition healthCondition;

    public Individual(final int x, final int y, final DirectionMovement directionMovement, final IndividualProperties individualProperties) {
        this.x = x;
        this.y = y;
        this.width = individualProperties.getIndividualWidth();
        this.height = individualProperties.getIndividualHeight();
        this.directionMovement = directionMovement;
        this.healthCondition = new HealthCondition(NORMAL);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DirectionMovement getDirectionMovement() {
        return this.directionMovement;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public HealthStatus getHealthStatus() {
        return healthCondition.getHealthStatus();
    }

    public void moveTo(final DirectionMovement newDirectionMovement) {
        if (healthCondition.getHealthStatus().allowedToMove()) {
            this.directionMovement = newDirectionMovement;
            x += directionMovement.xAxisMovement();
            y += directionMovement.yAxisMovement();
        }
    }

    public boolean isInfected() {
        return healthCondition.getHealthStatus().infected();
    }

    public boolean isHospitalized() {
        return healthCondition.getHealthStatus().hospitalized();
    }

    public void gotInfected(final int currentDay) {
        healthCondition = new HealthCondition(INFECTED, currentDay);
    }

    public void gotHospitalized(final int infectionStartDay) {
        healthCondition = new HealthCondition(HOSPITALIZED, infectionStartDay);
        directionMovement = STANDING;
    }

    public void died(final int deathDay) {
        healthCondition = new HealthCondition(DEAD, deathDay);
        directionMovement = STANDING;
    }

    public void recovered(final int recoveryDay) {
        healthCondition = new HealthCondition(RECOVERED, recoveryDay);
    }

    public boolean crossedWayWith(final Individual passerby) {
        return (passerby.getX() >= x) && (passerby.getX() < x + getWidth()) && (passerby.getY() >= y) && (passerby.getY() < y + getHeight());
    }
}
