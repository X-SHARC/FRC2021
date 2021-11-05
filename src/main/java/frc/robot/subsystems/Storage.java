// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  /** Creates a new Storage. */
  public WPI_TalonSRX storageRight = new WPI_TalonSRX(Constants.storageRightPort);
  public WPI_TalonSRX storageLeft = new WPI_TalonSRX(Constants.storageLeftPort);
  
  public Storage() {
    storageLeft.setInverted(false);
    storageRight.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void bothForward(){
    storageLeft.set(ControlMode.PercentOutput,Constants. storageSpeed);
    storageRight.set(ControlMode.PercentOutput,Constants. storageSpeed);
  }

  public void bothBackward(){
    storageLeft.set(ControlMode.PercentOutput,-1 * Constants. storageSpeed);
    storageRight.set(ControlMode.PercentOutput, -1 * Constants. storageSpeed);
  }

  public void rightForward(){
    storageRight.set(ControlMode.PercentOutput, Constants. storageSpeed);
    storageLeft.set(ControlMode.PercentOutput, -1 * Constants. storageSpeed);
  }

  public void leftForward(){
    storageRight.set(ControlMode.PercentOutput, -1 * Constants. storageSpeed);
    storageLeft.set(ControlMode.PercentOutput, Constants. storageSpeed);
  }

  public void set(double speed){
    storageRight.set(speed);
    storageLeft.set(speed);
  }

  public void stop(){
    storageLeft.set(0.0);
    storageRight.set(0.0);
  }

  
}
