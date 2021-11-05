// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
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
import frc.robot.Constants;

public class Swerve extends SubsystemBase {

  /*
  TODO realtime PID tuning - look into docs
  TODO look into feedforward tuning

  */

  private boolean isCalibrating;
  private boolean offsetCalibration = true;
  private boolean driveCalibration = false;
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

  //private final CKIMU gyro;
  private AHRS gyroAhrs = new AHRS();

  // TODO: Update these CAN device IDs to match your TalonFX + CANCoder device IDs | Done
  // TODO: Update module offsets to match your CANCoder offsets | Done

  private SwerveModule[] modules = new SwerveModule[] {
    new SwerveModule(new TalonFX(11), new TalonFX(16), new DutyCycleEncoder( new DigitalInput(3)), Rotation2d.fromDegrees(143)), //! Front Left
    new SwerveModule(new TalonFX(10), new TalonFX(12), new DutyCycleEncoder( new DigitalInput(4)), Rotation2d.fromDegrees(-15)), //! Front Right
    new SwerveModule(new TalonFX(0), new TalonFX(13), new DutyCycleEncoder(new DigitalInput(2)), Rotation2d.fromDegrees(57)), //! Back Left
    new SwerveModule(new TalonFX(14), new TalonFX(15), new DutyCycleEncoder( new DigitalInput(5) ), Rotation2d.fromDegrees(145))  //! Back Right
  };

  public Swerve(boolean isCalibrating) {
    this.isCalibrating = isCalibrating;
    resetAllEncoders();
    gyroAhrs.reset();
    //when setpoint goes back and forth between -0 and 0, the oscillation happens
    
    SmartDashboard.putData("Field", field2D);
    //initializeAutoPIDs();
  }
  
  public Rotation2d getHeading(){
    return Rotation2d.fromDegrees(
        getHeadingDouble()    
        );
  }

  public AHRS getGyroAhrs() {
      return gyroAhrs;
  }

  public double getHeadingDouble(){
    //return gyroAhrs.getAngle();
    return Math.IEEEremainder(gyroAhrs.getAngle(), 360.0) * (Constants.kGyroReversed ? -1.0 : 1.0);
  }

  SwerveDriveOdometry odometry = new SwerveDriveOdometry(
    Constants.Swerve.kinematics,
    getHeading()
  );
  

  public void resetAllEncoders(){
    for (int i = 0; i < modules.length; i++) {
      SwerveModule module = modules[i];
      //module.resetRotationEncoder();
      module.resetDriveEncoder();
    }
  }

  public double getAverageDistance(){
    /*
    double sum = 0;
    for (int i = 0; i < modules.length; i++) {
      SwerveModule module = modules[i];
      sum += module.getPosition();
    }
    return -sum / 4.0; */
    return modules[0].getPosition();
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
    SmartDashboard.putNumber("groAngle", getHeadingDouble());

    SmartDashboard.putNumber("0. modül", modules[0].getPosition());
    SmartDashboard.putNumber("1. modül", modules[1].getPosition());
    SmartDashboard.putNumber("2. modül", modules[2].getPosition());
    SmartDashboard.putNumber("3. modül", modules[3].getPosition());
    SmartDashboard.putNumber("average Distance", getAverageDistance());
    
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