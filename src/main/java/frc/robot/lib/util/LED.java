// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib.util;

import edu.wpi.first.wpilibj.Solenoid;

/** Add your docs here. */
public class LED {

    private Solenoid led;

    public LED(int SolenoidPort){
        led = new Solenoid(SolenoidPort);
    }

    public void toggle() {
        if (led.get())
            led.set(false);
        else led.set(true);
    }

    public boolean getState() {
        return led.get();
    }

    public Solenoid get(){
        return led;
    }

    public void turnOn(){
        led.set(true);
    }

    public void turnOff(){
        led.set(false);
    }

    
}
