// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climb extends SubsystemBase {
  /** Creates a new climb. */
  public WPI_TalonSRX climb = new WPI_TalonSRX (Constants.Climb.climbPort);
  private DoubleSolenoid solenoidClimb = new DoubleSolenoid(Constants.Climb.forward, Constants.Climb.reverse);
  private WPI_TalonSRX intake = new WPI_TalonSRX(Constants.Climb.intakePort);

  private boolean isLocked = false;

  public climb() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void deployClimb() {
    solenoidClimb.set(DoubleSolenoid.Value.kReverse);
  }
     public void retractClimb() {
    solenoidClimb.set(DoubleSolenoid.Value.kForward);
  }
  

  public void climbIn (double scale) { 
    if (!isLocked){
    climb.set(Constants.Climb.climbKForward*scale); // -1
    }
}
  
  public void climbOut(double scale) {
    if (!isLocked) {// out
    climb.set(Constants.Climb.climbKReverse*scale);
    }
    }
   
    public void intakeStop() {
      climb.set(0);
}

}

