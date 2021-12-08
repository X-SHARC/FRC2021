// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotState;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;

public class AutoAlignWithShooterSpeedUp extends CommandBase {
  /** Creates a new AutoAlignWithShooterSpeedUp. */

  Vision vision;
  Swerve swerve;
  LEDSubsystem led;
  double kP = 0.02;
  double i_time;
  Shooter shooter;
  int rpm;
  boolean isFinished = false;

  public AutoAlignWithShooterSpeedUp(Vision v, Swerve d, LEDSubsystem led, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.vision = v; 
    this.swerve = d;    
    this.led = led;
    this.shooter = shooter;

    addRequirements(swerve, vision, led, shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    led.turnOn();
    i_time = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(vision.hasTarget()){
      if (Math.abs(vision.getYaw()) < 1) {
        if((Timer.getFPGATimestamp() - i_time)>= 2){
          isFinished = true;
        }
        shooter.setShooter(0.87);
        Robot.state.update(RobotState.State.SPEEDING_UP);
      }
      
      double rot = Constants.Swerve.kMaxAngularSpeed * (vision.getYaw() * kP);
      swerve.drive(0, 0, rot, false);
      shooter.setShooter(0.87);
      Robot.state.update(RobotState.State.ALIGNING);

      
    }


    else if(!led.getState()) led.turnOn();
    else Robot.state.update(RobotState.State.NO_TARGET);


    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.state.update(RobotState.State.READY);
    swerve.drive(0, 0, 0, false);
    led.turnOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
