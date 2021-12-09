// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.lib.util.LED;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  public LED led = new LED(8);
  public LEDSubsystem() {}

  @Override
  public void periodic() {
  }

  public void turnOn(){
    led.turnOn();
  }

  public void turnOff(){
    led.turnOff();
  }

  public void toggle(){
    led.toggle();
  }

  public boolean getState(){
    return led.get();
  }
}
