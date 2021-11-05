// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class DriveForDistance extends CommandBase {
  Swerve swerve;
  PIDController distancePID = new PIDController(0.04, 0, 0);
  PIDController rotPID = new PIDController(0.0075, 0, 0);

  int meters;
  //kayra judd amk salağı

  public DriveForDistance(Swerve swerve, int meters) {
    this.swerve = swerve;
    this.meters = meters;
    addRequirements(swerve);
    distancePID.setTolerance(0.1);
    rotPID.setTolerance(5);
    SmartDashboard.putData("distnace pıd", distancePID);
    SmartDashboard.putData("auto rot pıd", rotPID);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // ! FALSE 
    swerve.drive(
      -0.3 * Constants.Swerve.kMaxSpeed,
     //MathUtil.clamp(-distancePID.calculate(swerve.getAverageDistance(), meters), -1, 1) * Constants.Swerve.kMaxSpeed,
     0,
     MathUtil.clamp(
     rotPID.calculate(swerve.getHeadingDouble(), 0),
      -1,
      1) * Constants.Swerve.kMaxAngularSpeed, 
     false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return distancePID.atSetpoint();
  }
}
