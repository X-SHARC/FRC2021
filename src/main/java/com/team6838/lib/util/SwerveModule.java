// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.lib.util;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.team6838.Constants;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpiutil.math.MathUtil;

// ! Need to change the rotation PIDs to RoboRio - !DONE!
// ! handle with caution

public class SwerveModule {
    
  // TODO: Tune these PID values for your robot
  private static final double kDriveP = 0.0;
  private static final double kDriveI = 0.0;
  private static final double kDriveD = 0.0;
  private static final double kDriveS = 0.0;
  private static final double kDriveV = 0.0;

  private static final double kAngleP = 0.0;
  private static final double kAngleI = 0.0;
  private static final double kAngleD = 0.0;
  private static final double kAngleS = 0.0;
  private static final double kAngleV = 0.0;
  private static final double kAngleLoopPeriod = 0.005; //update rate of PID, in seconds, set to 200hz NOT IN USE
  // If you can't tune the period constant, just swap it out for the other PID controller constructer (one without period)
  // In order to use a faster loop, PID.calculate() must also run on a separate thread with the same period rate
  // TODO| look into running the .calculate() on a separeate thread

  // CANCoder & SRXMagEncoder has 4096 ticks/rotation
  // ! CHECK FOR YOUR ENCODER & SETUP
  // ! Not sure if absolute mode CPR is 4096 for MagEncoder
  private static double kEncoderTicksPerRotation = 4096;

  private Rotation2d offset;
  private TalonFX driveMotor;
  private TalonFX angleMotor;
  //private Encoder rotEncoder;
  private DutyCycleEncoder rotEncoder;
  // DutyCycleEncoder is used for absolute values. Switch to normal Encoder class for relative.
  // Using absolute has the advantage of zeroing the modules autonomously.
  // If using relative, find a way to mechanically zero out wheel headings before starting the robot.
  double wheelCircumference = 2 * Math.PI * Units.inchesToMeters(2);

  private ProfiledPIDController anglePID = 
    new ProfiledPIDController(
      kAngleP,
      kAngleI,
      kAngleD,
      new TrapezoidProfile.Constraints(
        Constants.Swerve.kMaxSpeed, Constants.Swerve.kModuleMaxAngularAcceleration));

  private PIDController drivePID = new PIDController(kDriveP, kDriveI, kDriveD);
  // TODO IMPLEMENT FeedForward FOR DRIVE

  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(kDriveS, kDriveV);
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(kAngleS, kAngleV);

  public SwerveModule(TalonFX driveMotor, TalonFX angleMotor, DutyCycleEncoder rotEncoder, Rotation2d offset) {
    this.driveMotor = driveMotor;
    this.angleMotor = angleMotor;
    this.rotEncoder = rotEncoder;
    this.offset = offset;
    
    rotEncoder.setDistancePerRotation(360.0/kEncoderTicksPerRotation);
  }

  public double getDegrees(){
    return Math.IEEEremainder((rotEncoder.getDistance() + offset.getDegrees()), 360.0);
    // ! Offset usage may be wrong
  }

  public double getDriveMotorRate(){
    return ((driveMotor.getSelectedSensorVelocity() * 10) / 2048.0) * wheelCircumference;
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(
      getDriveMotorRate(), 
      new Rotation2d(
        getDegrees() 
      ));
  }

  /**
   * Gets the relative rotational position of the module
   * @return The relative rotational position of the angle motor in degrees
   */
  public Rotation2d getAngle() {
    // This reports degrees, not radians.
    return Rotation2d.fromDegrees(
     getDegrees()
    );
  }

  public void resetRotationEncoder(){
    rotEncoder.reset();
  }
  
  public void resetDriveEncoder(){
    driveMotor.setSelectedSensorPosition(0);    
  }

  public void resetEncoders(){
    resetDriveEncoder();
    resetRotationEncoder();
  }

  // TODO Encoder
  // Encoder init - DONE
  // Encoder offset - DONE 
  // Encoder getPos - DONE

  // TODO PID
  // drivePID init (not sure if this is used here? maybe for path?) - DONE 
  // anglePID init - DONE

  /**
   * Set the speed + rotation of the swerve module from a SwerveModuleState object
   * @param desiredState - A SwerveModuleState representing the desired new state of the module
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    Rotation2d currentRotation = getAngle();
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, currentRotation);

    // Find the difference between our current rotational position + our new rotational position
    Rotation2d rotationDelta = state.angle.minus(currentRotation);

    double desiredRotation = currentRotation.getDegrees() + rotationDelta.getDegrees();

    angleMotor.set(TalonFXControlMode.PercentOutput, 
        MathUtil.clamp( 
          ( anglePID.calculate(
                currentRotation.getDegrees(),
                desiredRotation
                ) +
                m_turnFeedforward.calculate(anglePID.getSetpoint().velocity) ), 
            -1.0, 
            1.0)
    );

    //TODO Current drive motor is not using PID, FIXME
    //https://github.com/wpilibsuite/allwpilib/blob/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/swervebot/SwerveModule.java
    //current setup uses percent mode which should be OK for tele-op
    double feetPerSecond = Units.metersToFeet(state.speedMetersPerSecond);
    driveMotor.set(TalonFXControlMode.PercentOutput, feetPerSecond / Constants.Swerve.kMaxSpeed);
  }

}