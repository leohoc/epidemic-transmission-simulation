package com.leohoc.ets.infrastructure.config;

public class TotalInfoLocation {

    private final int infoX;
    private final int infectedY;
    private final int hospitalizedY;
    private final int recoveredY;
    private final int deadY;

    public TotalInfoLocation(final int infoX,
                             final int infectedY,
                             final int hospitalizedY,
                             final int recoveredY,
                             final int deadY) {
        this.infoX = infoX;
        this.infectedY = infectedY;
        this.hospitalizedY = hospitalizedY;
        this.recoveredY = recoveredY;
        this.deadY = deadY;
    }

    public int getInfoX() {
        return infoX;
    }

    public int getInfectedY() {
        return infectedY;
    }

    public int getHospitalizedY() {
        return hospitalizedY;
    }

    public int getRecoveredY() {
        return recoveredY;
    }

    public int getDeadY() {
        return deadY;
    }
}
