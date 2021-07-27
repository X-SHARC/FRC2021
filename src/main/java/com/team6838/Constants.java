package com.team6838;

import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

	public static final class Swerve {
		public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
		public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
	}

	public static final class Shooter{
		//ports will be change
		public static final int k_SHOOTER_MASTER_MOTOR = 0;
		public static final int k_SHOOTER_SLAVE_MOTOR = 1;

		/*
		public static final double kP = 0.0;
		public static final double kI = 0.0;
		public static final double kD = 0.0;*/

		
		
	}

	public static final boolean kGyroReversed = false;

}
