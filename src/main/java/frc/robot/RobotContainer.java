// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.ClimbPOV;
import frc.robot.commands.DriveForDistance;
import frc.robot.commands.FeedBall;
import frc.robot.commands.FeederBackwards;
import frc.robot.commands.ShootBallSubsystems;
import frc.robot.commands.ShooterPID;
import frc.robot.commands.StorageAxisCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Climb;

public class RobotContainer {

  //SUBSYSTEMS
  Swerve swerveDrivetrain = new Swerve(true);
  LEDSubsystem LED = new LEDSubsystem();
  Climb climb = new Climb();
  Shooter shooter = new Shooter();
  Intake intake = new Intake();
  Storage storage = new Storage();
  Feeder feeder = new Feeder(false);

  //JOYSTICKS
  //! fix index
  XboxController driver = new XboxController(1);
  XboxController operator = new XboxController(3);

  Joystick joystick = new Joystick(0);



  //COMMANDS
  SwerveDriveCommand driveCommand = new SwerveDriveCommand(swerveDrivetrain, driver);
  ShooterPID shooterpid = new ShooterPID(shooter, 2000);
  TurnToAngle turnToAngle = new TurnToAngle(swerveDrivetrain, 45);
  ShootBallSubsystems shootBallSubsystems = new ShootBallSubsystems(shooter, feeder, storage);
  DriveForDistance driveForDistance = new DriveForDistance(swerveDrivetrain, 3);
  FeedBall feedBall = new FeedBall(storage, feeder);
  ClimbPOV climbPOV = new ClimbPOV(operator, climb);
  StorageAxisCommand storageAxisCommand = new StorageAxisCommand(operator, storage);
  FeederBackwards feederBackwards = new FeederBackwards(operator, feeder);
  
  public RobotContainer() {
    configureButtonBindings();
  }
  
  private void configureButtonBindings() {
    
    swerveDrivetrain.setDefaultCommand(driveCommand);
    climb.setDefaultCommand(climbPOV);
    
    //? TURNUVA BUTTON BINDING
    
    //FEEDER + STORAGE TOGETHER
    JoystickButton feedBallButton = new JoystickButton(operator, 1);
    feedBallButton.whileHeld(feedBall);
    
    
    //! FIX TO XBOX CONTROLLER
    /* JoystickButton climbDown = new JoystickButton(operator, 2);
    climbDown.whileHeld(new RunCommand(()-> climb.climbDown(),climb));
    climbDown.whenReleased(new RunCommand(()-> climb.stop(), climb));
    */
    //SHOOTER
    JoystickButton shooterButton = new JoystickButton(operator, 3);
    shooterButton.whileHeld(new RunCommand(()-> shooter.setShooter(1.0), shooter));
    
    //INTAKE
    JoystickButton intakeOutButton = new JoystickButton(operator, 5);
    JoystickButton intakeInButton = new JoystickButton(operator, 6);
    
    intakeInButton.whileHeld(new RunCommand(()-> intake.intakeForward() , intake));
    intakeInButton.whenReleased(new RunCommand(()-> intake.stop() , intake));
  
    intakeOutButton.whileHeld(new RunCommand(()-> intake.intakeBackwards(), intake));
    intakeOutButton.whenReleased(new RunCommand(()-> intake.stop(), intake));

    storage.setDefaultCommand(storageAxisCommand);
    /*JoystickButton storageBackwards = new JoystickButton(operator, 12);
    storageBackwards.whileHeld(new RunCommand(()-> storage.bothBackward(), storage));
    storageBackwards.whenReleased(new RunCommand(()-> storage.stop(), storage));*/

    //FEEDER BACKWARDS
    /*JoystickButton feederBackwards = new JoystickButton(operator, operator.getTriggerAxis(Hand.kRight));
    feederBackwards.whileHeld(new RunCommand(()-> feeder.runBackwards() , feeder));*/
    feeder.setDefaultCommand(feederBackwards);

    //! DRIVER JOYSTICK
    //STOP SWERVE
    JoystickButton stopSwerve = new JoystickButton(driver, 4);
    stopSwerve.whenPressed(new RunCommand(()-> swerveDrivetrain.drive(0, 0, 0, false), swerveDrivetrain));
    stopSwerve.whenReleased(new RunCommand(()-> swerveDrivetrain.setDefaultCommand(driveCommand), swerveDrivetrain));

    //AUTO ALIGN
    JoystickButton autoAim = new JoystickButton(driver, 5);
    autoAim.whenActive(new RunCommand(
      ()-> swerveDrivetrain.drive(0, 0, shooter.calculateTargetAngle(), false), 
      swerveDrivetrain));

    //TODO SLOW (SHIFT)

  }

  public Command getAutonomousCommand() {
    return null;
  }
}