package com.leohoc.ets.application;

import java.util.Calendar;

public class SimulationTimeEvolution {

    private final long simulationTotalTimeInMs;
    private final long simulatedDayDurationInMs;
    private final long startTimeInMs;

    public SimulationTimeEvolution(final long simulationTotalTimeInMs, final long simulatedDayDurationInMs) {
        this.startTimeInMs = Calendar.getInstance().getTimeInMillis();
        this.simulationTotalTimeInMs = simulationTotalTimeInMs;
        this.simulatedDayDurationInMs = simulatedDayDurationInMs;
    }

    public long getSimulatedTotalDays() {
        return simulationTotalTimeInMs / simulatedDayDurationInMs;
    }

    public long getInstantInSimulatedDays() {
        return getInstantInMs() / simulatedDayDurationInMs;
    }

    public boolean hasSimulationFinished() {
        return getInstantInMs() > simulationTotalTimeInMs;
    }

    private long getInstantInMs() {
        return Calendar.getInstance().getTimeInMillis() - startTimeInMs;
    }
}
