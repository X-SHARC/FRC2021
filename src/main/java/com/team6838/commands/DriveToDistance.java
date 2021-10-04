// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.commands;

import com.team6838.lib.drivers.NavX;
import com.team6838.lib.util.SRXMagEncoder;
import com.team6838.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToDistance extends CommandBase {
  /** Creates a new DriveToDistance. */
  private SwerveDrivetrain swerveDrivetrain;

  //TODO Define encoder
  //TODO: PID values
  private PIDController drivePID = new PIDController(0.0, 0.0, 0.0);
  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.0, 0.0, 0.0);


  public DriveToDistance(SwerveDrivetrain swerveDrivetrain, double drivemeter) {
    this.swerveDrivetrain = swerveDrivetrain;
    addRequirements(swerveDrivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
