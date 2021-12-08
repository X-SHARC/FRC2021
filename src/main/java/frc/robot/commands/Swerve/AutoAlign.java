// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

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
  double kP = 0.02;

  public AutoAlign(Vision vis, Swerve b, LEDSubsystem led) {
    this.vision = vis; 
    this.swerve = b;    
    this.led = led;
    
    addRequirements(swerve, vision, led);
   // addRequirements(swerve, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    led.turnOn();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(vision.hasTarget()){
      double rot = Constants.Swerve.kMaxAngularSpeed * (
      vision.getYaw() * kP
      );
      swerve.drive(0, 0, rot, false);
    }
    else if(!led.getState()) led.turnOn();
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
    if (vision.hasTarget()) return (Math.abs(vision.getYaw()) < 1);
    else return false;
  }
}
