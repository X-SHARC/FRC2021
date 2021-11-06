// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class FollowTrajectory extends CommandBase {
  private HolonomicDriveController controller;
  private Swerve swerve;
  private Timer timer;
  private Trajectory trajectory;
  private String name;
  
  public FollowTrajectory(String name, Swerve swerve, Trajectory traj) {
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



  }

  public FollowTrajectory(String name, Swerve swerve, double[][] traj) {
    this.name = name;
    this.swerve = swerve;
    addRequirements(swerve);

    this.trajectory = calculateTrajectory(traj);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(trajectory.getTotalTimeSeconds());
  }

  public Trajectory calculateTrajectory(double[][] inputTraj) {
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

  public Rotation2d getDesiredRotation(){
    return null;
  }
}
