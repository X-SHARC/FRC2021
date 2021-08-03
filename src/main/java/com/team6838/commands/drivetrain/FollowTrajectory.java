// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6838.commands.drivetrain;

import java.util.List;

import com.team6838.Constants;
import com.team6838.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class FollowTrajectory extends CommandBase {

  SwerveDrivetrain drivetrain;

  TrajectoryConfig config = new TrajectoryConfig(
    Constants.Swerve.kMaxSpeed,
    // ! check the constant below / are all my units correct throughout the code? (radiansperseconsq / mtspersecondsq)
    Constants.Swerve.maxAccelerationMetersPerSecondSq)
    .setKinematics(Constants.Swerve.kinematics);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory =
      TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        config);

    ProfiledPIDController thetaController = new ProfiledPIDController(
      Constants.Swerve.kP_Theta,
      0,
      0,
      Constants.Swerve.kThetaControllerConstraints);
          
    SwerveControllerCommand swerveControllerCommand =
      new SwerveControllerCommand(
      exampleTrajectory,
      drivetrain::getPose, // Functional interface to feed supplier
      Constants.Swerve.kinematics,
            
      // Position controllers
      new PIDController(Constants.Swerve.kP_XController, 0, 0),
      new PIDController(Constants.Swerve.kP_YController, 0, 0),
      thetaController,
      drivetrain::setModuleStates,
      drivetrain);

  public FollowTrajectory(SwerveDrivetrain dt) {
    this.drivetrain = dt;
    addRequirements(drivetrain);
    thetaController.enableContinuousInput(-Math.PI , Math.PI);
  }
            
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetOdometry(exampleTrajectory.getInitialPose());

  }

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
}
