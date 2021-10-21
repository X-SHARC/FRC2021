// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

public class AutoAlign extends CommandBase {
  /** Creates a new AutoAlign. */
  Vision vision;
  Swerve swerve;
  LEDSubsystem led;
  double kP = 0.0095;

  public AutoAlign(Vision vis, Swerve b, LEDSubsystem led) {
    this.vision = vis; 
    this.swerve = b;    
    this.led = led;
    
    addRequirements(swerve, vision, led);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    led.turnOn();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rot = Constants.Swerve.kMaxAngularSpeed * (
    vision.getYaw() * kP
    );
    swerve.drive(0, 0, rot, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(0, 0, 0, false);
    led.turnOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(vision.getYaw()) < 2;
  }
}
