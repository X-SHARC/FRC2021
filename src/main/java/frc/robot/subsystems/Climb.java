// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {

  public Victor leftClimb = new Victor(0);
  public Victor rightClimb = new Victor(1);

  /** Creates a new Climb. */
  public Climb() {
    leftClimb.setInverted(false);
    rightClimb.setInverted(false);
  }

  public void climbUp(){
    leftClimb.set(0.94);
    rightClimb.set(0.94);
  }

  public void climbDown(){
    leftClimb.set(-0.94);
    rightClimb.set(-0.94);
  }

  public void rightUp(){
    rightClimb.set(0.94);
  }

  public void rightDown(){
    rightClimb.set(-0.94);
  }

  public void leftUp(){
    leftClimb.set(0.94);
  }

  public void leftDown(){
    leftClimb.set(-0.94);
  }

  public void leftDownSlow(){
    leftClimb.set(-0.42);
  }
  public void rightDownSlow(){
    rightClimb.set(-0.42);
  }

  public void stop(){
    leftClimb.set(0);
    rightClimb.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
