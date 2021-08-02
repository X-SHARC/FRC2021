// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.team6838.Constants;
import com.team6838.lib.drivers.CKIMU;
import com.team6838.lib.util.SwerveModule;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrivetrain extends SubsystemBase {

  private boolean isCalibrating;
  private boolean offsetCalibration = true;
  private boolean driveCalibration = true;
  private boolean rotCalibration = true;
  
  /**
   * TODO: These are example values and will need to be adjusted for your robot!
   * Modules are in the order of -
   * Front Left
   * Front Right
   * Back Left
   * Back Right
   * 
   * Positive x values represent moving toward the front of the robot whereas
   * positive y values represent moving toward the left of the robot
   * https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/swerve-drive-kinematics.html#constructing-the-kinematics-object
   */
  private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
    new Translation2d(
      Units.inchesToMeters(10),
      Units.inchesToMeters(10)
    ),
    new Translation2d(
      Units.inchesToMeters(10),
      Units.inchesToMeters(-10)
    ),
    new Translation2d(
      Units.inchesToMeters(-10),
      Units.inchesToMeters(10)
    ),
    new Translation2d(
      Units.inchesToMeters(-10),
      Units.inchesToMeters(-10)
    )
  );

  private final CKIMU gyro;

  // TODO: Update these CAN device IDs to match your TalonFX + CANCoder device IDs
  // TODO: Update module offsets to match your CANCoder offsets

  private SwerveModule[] modules = new SwerveModule[] {
    new SwerveModule(new TalonFX(1), new TalonFX(2), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), // Front Left
    new SwerveModule(new TalonFX(3), new TalonFX(4), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), // Front Right
    new SwerveModule(new TalonFX(5), new TalonFX(6), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), // Back Left
    new SwerveModule(new TalonFX(7), new TalonFX(8), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0))  // Back Right
  };
  
  /** Creates a new SwerveDrivetrain. */
  public SwerveDrivetrain(CKIMU gyro, boolean isCalibrating) {
    this.isCalibrating = isCalibrating;
    this.gyro = gyro;
    gyro.reset();
  }

  public Rotation2d getHeading(){
    return Rotation2d.fromDegrees(
      Math.IEEEremainder(gyro.getRawYawDegrees(), 360) * (Constants.kGyroReversed ? -1.0 : 1.0)
    );
    //return Math.IEEEremainder(- gyro.getAngle(),360); 
  }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    SwerveModuleState[] states =
      kinematics.toSwerveModuleStates(
        fieldRelative
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getHeading())
          : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.normalizeWheelSpeeds(states, Constants.Swerve.kMaxSpeed);
    for (int i = 0; i < states.length; i++) {
      SwerveModule module = modules[i];
      SwerveModuleState state = states[i];
      module.setDesiredState(state);
    }
  }

  @Override
  public void periodic() {
    if(isCalibrating){
      modules[0].calibrate("Front Left", offsetCalibration, driveCalibration, rotCalibration);
      modules[1].calibrate("Front Right", offsetCalibration, driveCalibration, rotCalibration);
      modules[2].calibrate("Back Left", offsetCalibration, driveCalibration, rotCalibration);
      modules[3].calibrate("Back Right", offsetCalibration, driveCalibration, rotCalibration);
    }

  }
}