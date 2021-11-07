// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class FollowTrajectory extends CommandBase {
  private HolonomicDriveController controller;
  private Swerve swerve;
  private Timer timer;
  private Trajectory trajectory;
  private String name;
  private enum TrajectoryType {WPILIB,CUSTOM}
  TrajectoryType type;
  private Trajectory.State state = new Trajectory.State();
  private Pose2d odometryPose = new Pose2d();
  private ChassisSpeeds speeds = new ChassisSpeeds();
  private double endAngle;
  
  public FollowTrajectory(String name, Swerve swerve, Trajectory traj, double endAngle) {
    this.endAngle = endAngle;
    this.type = TrajectoryType.WPILIB;
    this.name = name;
    this.swerve = swerve;
    addRequirements(swerve);
    this.trajectory  = traj;

    TrajectoryConfig config = 
      new TrajectoryConfig(
        Constants.Trajectory.kMaxSpeed,
        Constants.Trajectory.kMaxAcceleration
      );

    config.setKinematics(Constants.Swerve.kinematics);

    timer.start();
  }

  public FollowTrajectory(String name, Swerve swerve, Trajectory traj){
    this(name, swerve, traj, 180);
  }

  public FollowTrajectory(String name, Swerve swerve, double[][] traj) {
    this.type = TrajectoryType.CUSTOM;
    this.name = name;
    this.swerve = swerve;
    addRequirements(swerve);

    /*using Pose2d waypoints for decoupled rotational movement, 
     * might wanna change to Translation2d if necessary
     */
    this.trajectory = calculatePose2dTrajectory(traj);
    timer.start();
  }

  @Override
  public void initialize() {
    var p = 0.0;
    var d = p / 100.0;

    ProfiledPIDController thetaController =
        new ProfiledPIDController(
            2.5,
            0,
            0,
            new TrapezoidProfile.Constraints(Constants.Swerve.kMaxAngularSpeed / 2.0, Constants.Swerve.kModuleMaxAngularAcceleration / 2.0));
    thetaController.enableContinuousInput(Math.toRadians(-180), Math.toRadians(180));
    controller =
        new HolonomicDriveController(
            new PIDController(p, 0, d), new PIDController(p, 0, d), thetaController);
    controller.setEnabled(true);

    swerve.resetOdometry(trajectory.getInitialPose());
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    state = trajectory.sample(timer.get());
    odometryPose = swerve.getPose();
    
    if(type == null) type = TrajectoryType.WPILIB;
    else if(type == TrajectoryType.WPILIB){
      speeds = controller.calculate(odometryPose, state, Rotation2d.fromDegrees(endAngle));

      swerve.drive(
        speeds.vxMetersPerSecond,
        speeds.vyMetersPerSecond,
        speeds.omegaRadiansPerSecond,
        false);
    }
    else if(type == TrajectoryType.CUSTOM){
      speeds = controller.calculate(
        odometryPose, 
        state.poseMeters,
        state.velocityMetersPerSecond,
        state.poseMeters.getRotation()
        );

      swerve.drive(
        speeds.vxMetersPerSecond,
        speeds.vyMetersPerSecond,
        speeds.omegaRadiansPerSecond,
        false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(0, 0, 0, false);
    System.out.println("Finished " + name + " Path in " + timer.get() + " seconds");
    System.out.println("Trajectory Type: " + type);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(trajectory.getTotalTimeSeconds());
  }

  public TrajectoryType getType() {
    return type;
  }
  
  public Trajectory calculateTranslation2dTrajectory(double[][] inputTraj) {
    Trajectory generatedTrajectory = new Trajectory();
    try {

      Pose2d startPose = new Pose2d(
        inputTraj[0][0],
        inputTraj[0][1],
        Rotation2d.fromDegrees(
          inputTraj[0][2])
      );

      Pose2d endPose = new Pose2d(
        inputTraj[inputTraj.length-1][0],
        inputTraj[inputTraj.length-1][1],
        Rotation2d.fromDegrees(
          inputTraj[inputTraj.length-1][2]
        )
      );

      ArrayList<Translation2d> path = new ArrayList<Translation2d>();

      for (int i = 1; i < inputTraj.length-1; i++) {
        Translation2d point = new Translation2d(
          inputTraj[i][0],
          inputTraj[i][1]);
        path.add(point);
      }
      
      // ? might wanna add start and end velocity constraints

      TrajectoryConfig config = 
        new TrajectoryConfig(
          Constants.Trajectory.kMaxSpeed,
          Constants.Trajectory.kMaxAcceleration
        );

      config.setKinematics(Constants.Swerve.kinematics);

      generatedTrajectory = TrajectoryGenerator.generateTrajectory(
        startPose,
        path,
        endPose,
        config);

    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Error generating trajectory!");
      return null;
    }

    return generatedTrajectory;
  }

  public Trajectory calculatePose2dTrajectory(double[][] inputTraj) {
    Trajectory generatedTrajectory = new Trajectory();
    try {

      ArrayList<Pose2d> path = new ArrayList<Pose2d>();

      for (int i = 0; i <= inputTraj.length-1; i++) {
        Pose2d point = new Pose2d(
          inputTraj[i][0],
          inputTraj[i][1],
          Rotation2d.fromDegrees(inputTraj[i][2])
          );
        path.add(point);
      }
      
      // ? might wanna add start and end velocity constraints

      TrajectoryConfig config = 
        new TrajectoryConfig(
          Constants.Trajectory.kMaxSpeed,
          Constants.Trajectory.kMaxAcceleration
        );

      config.setKinematics(Constants.Swerve.kinematics);

      generatedTrajectory = TrajectoryGenerator.generateTrajectory(
        path,
        config);

    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Error generating trajectory!");
      return null;
    }
    return generatedTrajectory;
  }
}
