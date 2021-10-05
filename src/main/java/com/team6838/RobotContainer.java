package com.team6838;

import com.team6838.commands.SwerveDriveCommand;
import com.team6838.lib.drivers.CKIMU;
import com.team6838.lib.drivers.NavX;
import com.team6838.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  
  private final CKIMU gyro = new NavX(SPI.Port.kMXP);
  //or 
  //private final CKIMU gyro = new NavX();
  private final XboxController controller = new XboxController(0);
  private final SwerveDrivetrain drivetrain = new SwerveDrivetrain(gyro , false);

  public RobotContainer() {
    drivetrain.setDefaultCommand(new SwerveDriveCommand(drivetrain, controller));
    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
