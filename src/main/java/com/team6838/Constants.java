package com.team6838;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;

import edu.wpi.first.wpilibj.util.Units;

//TODO: EDIT ALL CONSTANTS
public final class Constants {
	public static final class Storage {
		public static final int storagePortRight = 0;
		public static final int storagePortLeft = 0;
		public static final double storageSpeed = 0;
        public static int storagePDPChannel;
        public static double storageCurrentThreshold;
	}

		public static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared
		
		public static final int kWheelBase = 0;
		public static final int kTrackWidth = 0;

		public static final SwerveDriveKinematics kinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
			new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
			
		public static final double maxAccelerationMetersPerSecondSq = 0;
		public static final double kP_Theta = 0;
		public static final Constraints kThetaControllerConstraints = null;
		public static final double kP_YController = 0;
		public static final double kP_XController = 0;

	public static final class Feeder  {
		public static final int k_feederPort = 0; //feeder için motor numarası değiştirelecek
		public static final int feederSpeed = 0; //Feeder'ın hızı değişecek
        public static double runFeederBackward = 0; //edit
		public static double runFeederForward = 0; //edit
	}

	public static final class Shooter{ 
		//ports will be change
		public static final int k_SHOOTER_MASTER_MOTOR = 0;
		public static final int k_SHOOTER_SLAVE_MOTOR = 1;

		public static final double kS = 0.0;
		public static final double kV = 0.0;
        public static final double kA = 0.0;
	}
	public static final class Trajectory{

		public static final double maxVelocityMetersPerSecond = 0;
		public static final double maxAccelerationMetersPerSecondSq = 0;
	}	
	public static final class Climb{ //TODO EDİT 
		public static final int climbPort = 0;
		public static final int forwardChannel = 0;
		public static final int forward = 0;
		public static final int reverse = 0;
        public static int intakePort;
        public static double climbKForward;
		public static double climbKReverse;
}

	public static final boolean kGyroReversed = false;
	public static final int intakePort = 1;
	public static final double intakeKForward = -1;
	public static final double intakeKReverse = 1;
	public static final double intakeStop = 0;
	public static final double timestamp_Current_0 = 0;
	public static final int intakePdpChannel = 0;
	public static final int intakeCurrentTreshold = 3;
	public static final int forwardChannel = 0;
	public static final int reverseChannel = 0;
}
