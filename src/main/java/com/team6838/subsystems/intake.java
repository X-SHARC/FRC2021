// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase {
  
  public WPI_TalonSRX intake_Supporter = new WPI_TalonSRX (Constants.intake_Supporter);
  /** Creates a new intake. */
  public intake() {}
  public void intakeIn() {intakeIn(1);};
  public void intakeIn(double scale) { // in
    intake_Supporter.set(Constants.intakeKForward*scale); // -1
 }

  public void intakeOut() {intakeOut(1);};
  public void intakeOut(double scale) { // out
   intake_Supporter.set(Constants.intakeKReverse*scale);
 }

  public void intakeStop() {
   intake_Supporter.set(0);
 }

  public void intakeTrigger(double a, double b) {
  if (Math.abs(a) > Math.abs(b)) {
    intake_Supporter.set(a*0.57 );
  }
  else {
    intake_Supporter.set(b*0.85);
  }
}
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}