// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


// hellos

public class intake extends SubsystemBase {

  private WPI_TalonSRX intake = new WPI_TalonSRX(Constants.intakePort);
  private DoubleSolenoid solenoidIntake = new DoubleSolenoid(Constants.forwardChannel, Constants.reverseChannel);
  private final PowerDistributionPanel m_pdp;
  boolean isLocked = false;
  boolean solenoidLocked = false;
  
  public intake(PowerDistributionPanel PDP){
    this.m_pdp = PDP;
  }

  public void intakeIn (double scale) { 
    if (!isLocked){
    intake.set(Constants.intakeKForward*scale); // -1
    }
}
  
  public void intakeOut(double scale) {
    if (!isLocked) {// out
    intake.set(Constants.intakeKReverse*scale);
    }
}
   public void deployIntake() {
  solenoidIntake.set(DoubleSolenoid.Value.kReverse);
}
   public void retractIntake() {
  solenoidIntake.set(DoubleSolenoid.Value.kForward);
}

  public void setDefenseMode(boolean defense) { 
  if (defense) { 
    if (intake.get()!= 0.0){
      intakeStop();
    }
    if (solenoidIntake.get()!= DoubleSolenoid.Value.kForward ){ 
      retractIntake();
    }
  }
    else if (!defense){
    if (solenoidIntake.get()!= DoubleSolenoid.Value.kReverse ){ 
      deployIntake();
  }
 }
}
  public void intakeStop() {
   intake.set(0);
 }

  public void intakeTrigger(double a, double b) {
  if (Math.abs(a) > Math.abs(b)) {
    intake.set(a * 0.57);
  }
  else {
    intake.set(b * 0.85);
  }
}

  @Override
  public void periodic() {
    if (m_pdp.getCurrent(Constants.intakePdpChannel) >= Constants.intakeCurrentTreshold) {
      isLocked = true;
    }
    else{
      if(isLocked){
        isLocked =  false;
      }
    }
  }
}
