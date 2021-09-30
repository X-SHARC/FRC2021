package com.team6838.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.team6838.Constants;
import com.team6838.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToAngleArcade extends CommandBase {
  /** Creates a new DriveToAngleArcade. */
  //TODO ARRANGE DEVICE NUMBERS
  
  private WPI_TalonFX front_right = new WPI_TalonFX(0);
  private WPI_TalonFX rear_right = new WPI_TalonFX(1);
  private WPI_TalonFX rear_left = new WPI_TalonFX(2);
  private WPI_TalonFX front_left = new WPI_TalonFX(3);
  
  private SpeedControllerGroup rightMotors, leftMotors;

  private SwerveDrivetrain swerveDt;


  //TODO: TUNE PID
  private PIDController turnController = new PIDController(0.0, 0.0, 0.0);

  private DifferentialDrive drive;
  double angle;
  double currentAngle;
  double error;

  public DriveToAngleArcade(double angle) {
    this.angle = angle;
    error = angle - currentAngle;
    rightMotors = new SpeedControllerGroup(front_right,rear_right);
    leftMotors = new SpeedControllerGroup(front_left, rear_left);

    drive = new DifferentialDrive(rightMotors,leftMotors);
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = swerveDt.getHeading().getDegrees();
    turnController.setSetpoint(angle);
    turnController.calculate(error);
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
