// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class TurnToAngle extends CommandBase {
  /** Creates a new TurnToAngle. */
  public Swerve swerve;
  double angleSetpoint;
  PIDController turnPID =  new PIDController(0.0075, 0, 0);

  public TurnToAngle(Swerve swerve, double angleSetpoint) {
    this.swerve = swerve;
    this.angleSetpoint = angleSetpoint;
    addRequirements(swerve);
    turnPID.setTolerance(2);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // ! NOT FIELD RELATIVE
    swerve.drive(0, 0, 
    turnPID.calculate(swerve.getHeadingDouble(), angleSetpoint) * Constants.Swerve.kMaxAngularSpeed
    , false);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return turnPID.atSetpoint();
  }
}
