package com.team6838;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

	public static final class Swerve {
		public static final double kMaxSpeed = Units.feetToMeters(16.2); // 16.2 feet per second
		public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
	}

	public static final boolean kGyroReversed = false;

	//TODO Create intake class - yiğite bak örnek olarak
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
