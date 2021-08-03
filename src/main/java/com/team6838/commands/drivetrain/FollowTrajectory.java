// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.commands.drivetrain;

import java.util.ArrayList;

import com.team6838.Constants;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FollowTrajectory extends CommandBase {
  /** Creates a new FollowTrajectory. */
  public FollowTrajectory() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
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

      for (int i = 1; i < inputTraj.length; i++) {
        Translation2d point = new Translation2d(
          inputTraj[i][0],
          inputTraj[i][1]);
        path.add(point);
      }

      TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
        Constants.Trajectory.maxVelocityMetersPerSecond,
        Constants.Trajectory.maxAccelerationMetersPerSecondSq);
      
      trajectoryConfig.setKinematics(Constants.Swerve.kinematics);
      
      // ? might wanna add start and end velocity constraints

      generatedTrajectory = TrajectoryGenerator.generateTrajectory(
        startPose,
        path,
        endPose,
        trajectoryConfig);

    } catch (Exception e) {
      //TODO: handle exception
    }

    return generatedTrajectory;
  }
}
