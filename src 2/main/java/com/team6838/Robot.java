<<<<<<< HEAD
=======
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
package com.team6838;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

<<<<<<< HEAD
// TODO change the scheduler to a custom scheduler
// ! CHECK 195 NavX when possible 

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;  

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

=======
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

<<<<<<< HEAD
=======
  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

<<<<<<< HEAD
=======
    // schedule the autonomous command (example)
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

<<<<<<< HEAD
=======
  /** This function is called periodically during autonomous. */
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
<<<<<<< HEAD
=======
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

<<<<<<< HEAD
  @Override
  public void teleopPeriodic() {}

}
=======
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
>>>>>>> 06da5edb09a61cb57c8b4f4c458c06a99d42ac92
