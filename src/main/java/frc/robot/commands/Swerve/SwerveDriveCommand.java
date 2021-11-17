// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class SwerveDriveCommand extends CommandBase {
  private final XboxController joystick;
  private final Swerve swerveSubsystem;
  private boolean fieldOriented;

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  // TODO not using them currently, try out and see if you want to keep them for comp
  private final SlewRateLimiter xSpeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter ySpeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(3);

  double scale = 0.6;
  
    /** Creates a new SwerveDriveCommand. */
    public SwerveDriveCommand(Swerve sw, XboxController joystick) {
      this.swerveSubsystem = sw;
      this.joystick = joystick;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(sw);
      fieldOriented =  false;
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    scale = Math.abs(joystick.getTriggerAxis(Hand.kRight)) > 0.4 ? 1 : 0.4;

    final var xSpeed = xSpeedLimiter.calculate(
      (Math.abs(joystick.getY(Hand.kLeft)) < 0.1) ? 0 : joystick.getY(Hand.kLeft)
      * Constants.Swerve.kMaxSpeed * scale );

    
    final var ySpeed = ySpeedLimiter.calculate(
      (Math.abs(joystick.getX(Hand.kLeft)) <  0.1) ? 0 : joystick.getX(Hand.kLeft)
     * Constants.Swerve.kMaxSpeed * scale );
     
    final var rot = rotLimiter.calculate(
      (Math.abs(joystick.getX(Hand.kRight)) < 0.1) ? 0 : joystick.getX(Hand.kRight)
      * Constants.Swerve.kMaxAngularSpeed * scale ) ;

    if(joystick.getAButton()){
      fieldOriented = fieldOriented ? false : true;
    }

    //swerveSubsystem.drive(xSpeed * 0.6, ySpeed * 0.6, rot * 0.4, fieldRelative);
    swerveSubsystem.drive(xSpeed, ySpeed, rot, fieldOriented);

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
