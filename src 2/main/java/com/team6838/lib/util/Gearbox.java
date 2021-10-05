package com.team6838.lib.util;

public class Gearbox {

    private double gearRatio;
    
    public Gearbox(double drivingGear, double drivenGear){
        this.gearRatio = drivenGear / drivingGear;
    }

    public double getOutput(double rot){
        return rot*gearRatio;
    }

    public double getRatio(){
        return gearRatio;
    }
}
