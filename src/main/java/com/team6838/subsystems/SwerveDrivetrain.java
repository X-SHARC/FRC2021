// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.team6838.Constants;
import com.team6838.lib.drivers.CKIMU;
import com.team6838.lib.util.SwerveModule;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrivetrain extends SubsystemBase {

  private boolean isCalibrating;
  private boolean offsetCalibration = true;
  private boolean driveCalibration = true;
  private boolean rotCalibration = true;

  private final Field2d field2D = new Field2d();
  
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

  private final CKIMU gyro;

  // TODO: Update these CAN device IDs to match your TalonFX + CANCoder device IDs
  // TODO: Update module offsets to match your CANCoder offsets

  private SwerveModule[] modules = new SwerveModule[] {
    new SwerveModule(new TalonFX(1), new TalonFX(2), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), //! Front Left
    new SwerveModule(new TalonFX(3), new TalonFX(4), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), //! Front Right
    new SwerveModule(new TalonFX(5), new TalonFX(6), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0)), //! Back Left
    new SwerveModule(new TalonFX(7), new TalonFX(8), new DutyCycleEncoder(0), Rotation2d.fromDegrees(0))  //! Back Right
  };

  SwerveDriveOdometry odometry = new SwerveDriveOdometry(
    Constants.Swerve.kinematics,
    getHeading()
  );
  
  public SwerveDrivetrain(CKIMU gyro, boolean isCalibrating) {
    this.isCalibrating = isCalibrating;
    this.gyro = gyro;
    gyro.reset();
    resetAllEncoders();
    SmartDashboard.putData("Field", field2D);
  }

  public void resetAllEncoders(){
    for (int i = 0; i < modules.length; i++) {
      SwerveModule module = modules[i];
      module.resetBothEncoders();
    }
  }

  public Rotation2d getHeading(){
    return Rotation2d.fromDegrees(
      Math.IEEEremainder(gyro.getRawYawDegrees(), 360) * (Constants.kGyroReversed ? -1.0 : 1.0)
    );
  }

  public Pose2d getPose(){
    return odometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d pose) {
    odometry.resetPosition(
      pose, 
      getHeading());
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    SwerveModuleState[] states =
    Constants.Swerve.kinematics.toSwerveModuleStates(
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

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.normalizeWheelSpeeds(
        desiredStates, Constants.Swerve.kMaxSpeed);
    modules[0].setDesiredState(desiredStates[0]);
    modules[1].setDesiredState(desiredStates[1]);
    modules[2].setDesiredState(desiredStates[2]);
    modules[3].setDesiredState(desiredStates[3]);
  }

  @Override
  public void periodic() {
    odometry.update(
      getHeading(),
      modules[0].getState(), 
      modules[1].getState(), 
      modules[2].getState(), 
      modules[3].getState() 
      );

    field2D.setRobotPose(getPose());

    if(isCalibrating){
      modules[0].calibrate("Front Left", offsetCalibration, driveCalibration, rotCalibration);
      modules[1].calibrate("Front Right", offsetCalibration, driveCalibration, rotCalibration);
      modules[2].calibrate("Back Left", offsetCalibration, driveCalibration, rotCalibration);
      modules[3].calibrate("Back Right", offsetCalibration, driveCalibration, rotCalibration);
    }

  }
}