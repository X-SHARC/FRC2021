// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.commands;

import com.kauailabs.navx.frc.AHRS;
import com.team6838.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToAngle extends CommandBase {
  /** Creates a new DriveToAngle. */
  private AHRS gyro = new AHRS();
  private SwerveDrivetrain drivetrain = new SwerveDrivetrain(gyro);

  //PIDController decleration
  //TODO p, i, d values will be chnged
  public PIDController pid = new PIDController(0, 0, 0);

  public DriveToAngle(SwerveDrivetrain dt) {
    //not sure
    this.drivetrain = dt;

    addRequirements();
  }

  public void driveToAngle(double angle){
    pid.setSetpoint(angle);
    double output = pid.calculate(pid.getSetpoint());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
