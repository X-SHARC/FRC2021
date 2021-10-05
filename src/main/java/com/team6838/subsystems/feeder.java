// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class feeder extends SubsystemBase {
  boolean locked = false;
  /** Creates a new feeder. */
// This method will be called once per scheduler run
  private WPI_TalonSRX feeder = new WPI_TalonSRX(Constants.Feeder.k_feederPort);
  private final PowerDistributionPanel m_pdp;
  
  public feeder(PowerDistributionPanel PDP){
    this. m_pdp =PDP;
  }
  public void feederIn() {feederIn(1);};
  public void feederIn(double scale) {
    feeder.set(Constants.Feeder.feederSpeed * scale);
  }

  public void feederOut() {feederOut(1);};
  public void feederOut(double scale) {
    feeder.set(Constants.Feeder.feederSpeed * scale);
  }

  public void runFeeder(){
    if(!locked){
    feeder.set(Constants.Feeder.runFeederForward);
      }
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
}
