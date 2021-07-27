// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class intake extends SubsystemBase {
  
  public WPI_TalonSRX intake = new WPI_TalonSRX (Constants.intake);
  /** Creates a new intake. */
  boolean isLocked = false;

  public intake() {}

  private static final int kPDPId = 0;
  private final PowerDistributionPanel m_pdp = new PowerDistributionPanel(kPDPId);

  public void intakeIn (double scale) { 
    if (!isLocked){
    intake.set(Constants.intakeKForward*scale); // -1
 }
}

  public void intakeOut() {intakeOut(1);};
  public void intakeOut(double scale) {
    if (!isLocked) {// out
   intake.set(Constants.intakeKReverse*scale);
  }
 }

  public void intakeStop() {
   intake.set(0);
 }

  public void intakeTrigger(double a, double b) {
  if (Math.abs(a) > Math.abs(b)) {
    intake.set(a*0.57 );
  }
  else {
    intake.set(b*0.85);
  }
}
  @Override
  public void periodic() {
   
	if (m_pdp.getCurrent(Constants.intakePdpPort) >= Constants.intakeCurrentTreshold) {
   isLocked = true;
    }
  }
}
