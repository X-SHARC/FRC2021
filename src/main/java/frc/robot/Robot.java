// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  public static SendableChooser<Integer> autoChooser = new SendableChooser<>();
  private DriverStation ds;
  //private Alliance allianceColor;
  //private int dsLocation;

  public void displayMatchInfo(){
    SmartDashboard.putString("Event Name", ds.getEventName());
    SmartDashboard.putNumber("Match Number", ds.getMatchNumber());
    SmartDashboard.putNumber("Time Remaining", ds.getMatchTime());
  }

  @Override
  public void robotInit() {
    //this.allianceColor = ds.getAlliance();
    //this.dsLocation = ds.getLocation();

    m_robotContainer = new RobotContainer();

    //if(!ds.isFMSAttached()) ds.silenceJoystickConnectionWarning(true);

    autoChooser.setDefaultOption("Blind Auto", 0);
    autoChooser.addOption("3 Ball Auto w/ Vision", 1);
    autoChooser.addOption("3 Ball Auto w/ Vision + Intake", 2);
    SmartDashboard.putData(autoChooser);

    //if(ds.isDSAttached()) displayMatchInfo();
    
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand(autoChooser.getSelected());

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    m_robotContainer.statusLED.setBufferColor(0, 255, 0);
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}