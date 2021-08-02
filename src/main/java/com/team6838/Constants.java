package com.team6838;

import java.util.HashMap;

import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

	public static final class Swerve {
		public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
		public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
		public static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared
		

	}
	public static final boolean kGyroReversed = false;
    

}