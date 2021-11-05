// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ClimbPOV extends CommandBase {
  /** Creates a new ClimbPOV. */
  Climb climb;
  XboxController operator;
  
  public ClimbPOV(XboxController operator,Climb climb) {
    this.climb = climb;
    this.operator = operator;
    addRequirements(climb);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    switch(operator.getPOV()){
      case 0:
        climb.climbUp();
        break;
      case 45:
        climb.rightUp();
        break;
      case 135:
        climb.rightDown();
        break;
      case 180:
        climb.climbDown();
        break;
      case 225:
        climb.leftDown();
        break;
      case 315:
        climb.leftUp();
        break;
      default:
        climb.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    climb.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
