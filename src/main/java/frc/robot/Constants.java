// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.util.Units;

public final class Constants {
	public static final double CAMERA_HEIGHT_METERS = 0.0;
	public static final double TARGET_HEIGHT_METERS = 0.0;
	public static final double CAMERA_PITCH_RADIANS = 0.0;

	public static final class Swerve {
		public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
		public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
		public static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared
		
		public static final double kLength = 0.5903;
		public static final double kWidth = 0.5953;

		public static final SwerveDriveKinematics kinematics =
			new SwerveDriveKinematics(
				new Translation2d(kLength / 2, kWidth / 2),
				new Translation2d(kLength / 2, -kWidth / 2),
				new Translation2d(-kLength / 2, kWidth / 2),
				new Translation2d(-kLength / 2, -kWidth / 2)
			);
			
		public static final double maxAccelerationMetersPerSecondSq = 0;
		public static final double kP_Theta = 0;
		public static final Constraints kThetaControllerConstraints = null;
		public static final double kP_YController = 0;
		public static final double kP_XController = 0;
	}

	public static final class Trajectory { 
		public static final double kMaxSpeed = 0.0; // ms^-1
		public static final double kMaxAcceleration = 0.; // ms^-2
	}

	public static final boolean kGyroReversed = true;




    public static final double intakeSpeed = 1.0;
    public static final double storageSpeed = 0.65;
	public static final int storageRightPort = 5;
    public static final int storageLeftPort = 1;
	

}