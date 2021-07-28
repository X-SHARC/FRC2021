// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class feeder extends SubsystemBase {
  /** Creates a new feeder. */
// This method will be called once per scheduler run
  private WPI_TalonSRX feeder = new WPI_TalonSRX(Constants.k_feederPort);
  
  public void feederIn() {feederIn(1);};
  public void feederIn(double scale) {
    feeder.set(Constants.feederSpeed * scale);
  }

  public void feederOut() {feederOut(1);};
  public void feederOut(double scale) {
    feeder.set(Constants.feederSpeed * scale);
  }
  public void stopEverything() {
    feeder.set(0);
  }



  /*
  void runFeeder(double güç[-1,1])

  void runFeederForward()
  void runFeederBackwards()

  boolean isJammed()
  check if the current is above a number
  return true/false
  */
  public feeder() { }

  @Override
  public void periodic() {
    
  }
}
