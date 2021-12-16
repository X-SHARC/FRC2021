// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterSpeeds extends CommandBase {
  /** Creates a new ShooterSpeeds. */
  Shooter shooter;
  XboxController operator;

  public ShooterSpeeds(Shooter shooter, XboxController operator) {
    this.operator = operator;
    this.shooter = shooter;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(operator.getTriggerAxis(Hand.kRight)>0.2){
      shooter.setShooter(11.4);
    }
    else if(operator.getTriggerAxis(Hand.kLeft)>0.2){
      shooter.setShooter(8.4);
    }
    else if(operator.getXButton()){
      shooter.setShooter(9.6);
    }
    else{
      shooter.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
