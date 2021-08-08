package com.team6838;

import edu.wpi.first.wpilibj.util.Units;

public final class Constants {
	
	//feeder
		public static final int k_feederPort = 0; //feeder için motor numarası değiştirelecek
		public static final int feederSpeed = 0; //Feeder'ın hızı değişecek
		
		public class feeder extends SubsystemBase {
			/** Creates a new feeder. */
			public feeder() {}
		  // This method will be called once per scheduler run
			private WPI_TalonSRX feeder = new WPI_TalonSRX(Constants.k_feederPort);
		  
			public void feederIn() {feederIn(1);};
			public void feederIn(double scale) {
			  feeder.set(Constants.feederSpeed * scale);
			}
		  
			public void feederOut() {feederOut(1);};
			public void feederOut(double scale) {
			  feeder.set(Constants.feederSpeed * scale);
			}
			public void stopEverything() {
			  feeder.set(0);
			}
		}
	//drivetrain	
		public static final class Swerve {
			public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
			public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
		}

		public static final boolean kGyroReversed = false;


}
		
