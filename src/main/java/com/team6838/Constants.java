package com.team6838;

import edu.wpi.first.wpilibj.util.Units;


public final class Constants {
	
	//feeder
		
		
		public static final class Feeder  {
			int k_feederPort = 0; //feeder için motor numarası değiştirelecek
			int feederSpeed = 0; //Feeder'ın hızı değişecek
		}
	//drivetrain	
		public static final class Swerve {
			public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
			public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
		}

		public static final boolean kGyroReversed = false;
		public static double feederSpeed;
        public static int k_feederPort;


}
		
