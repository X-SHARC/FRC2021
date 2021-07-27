package com.team6838;

import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

	public static final class Swerve {
		public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
		public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
	}

	public static final boolean kGyroReversed = false;
	public static final int storagePortRight = 0;
	public static final int storagePortLeft = 0;
	public static final double storageSpeed = 0;
	public static final double storageSpeed2 = 0;
	public static Object motorSpeed;

}
