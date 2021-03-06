// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpiutil.net.PortForwarder;
import frc.robot.commands.Swerve.SwerveDriveCommand;
import frc.robot.commands.Swerve.TurnToAngle;
import frc.robot.lib.drivers.WS2812Driver;
import frc.robot.commands.Swerve.AutoAlign;
import frc.robot.commands.Swerve.DriveForDistance;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Auto.Auto3Ball;
import frc.robot.commands.Auto.BlindAuto;
import frc.robot.commands.ClimbPOV;
import frc.robot.commands.FeedBall;
import frc.robot.commands.FeederBackwards;
import frc.robot.commands.ShootBallSubsystems;
import frc.robot.commands.ShooterPID;
import frc.robot.commands.ShooterSpeeds;
import frc.robot.commands.StorageAxisCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Climb;

public class RobotContainer {
  // ! LEDs
  WS2812Driver statusLED = new WS2812Driver(4, 14);
  LEDSubsystem LED = new LEDSubsystem();
  // !SUBSYSTEMS
  Swerve swerveDrivetrain = new Swerve(true);
  Climb climb = new Climb();
  Shooter shooter = new Shooter();
  static Intake intake = new Intake();
  Storage storage = new Storage();
  Feeder feeder = new Feeder(false);
  Vision vision = new Vision();

  // !JOYSTICKS
  // !fix index
  XboxController driver = new XboxController(0);
  XboxController operator = new XboxController(1);

  // !COMMANDS
  SwerveDriveCommand driveCommand = new SwerveDriveCommand(swerveDrivetrain, driver);
  ShooterPID shooterpid = new ShooterPID(shooter, 2000);
  TurnToAngle turnToAngle = new TurnToAngle(swerveDrivetrain, 45);
  ShootBallSubsystems shootBallSubsystems = new ShootBallSubsystems(shooter, feeder, storage);
  DriveForDistance driveForDistance = new DriveForDistance(swerveDrivetrain, 3);
  FeedBall feedBall = new FeedBall(storage, feeder);
  ClimbPOV climbPOV = new ClimbPOV(operator, climb);
  StorageAxisCommand storageAxisCommand = new StorageAxisCommand(operator, storage);
  FeederBackwards feederBackwards = new FeederBackwards(operator, feeder);
  AutoAlign autoAlign = new AutoAlign(vision, swerveDrivetrain, LED);
  ShooterSpeeds shooterSpeeds = new ShooterSpeeds(shooter, operator);

  // !AUTO COMMANDS
  BlindAuto blindAuto = new BlindAuto(swerveDrivetrain, feeder, storage, shooter, intake);
  Auto3Ball auto3Ball = new Auto3Ball(shooter, feeder, storage, swerveDrivetrain, vision, LED);
  //Auto3BallIntakeDrop auto3BallIntake = new Auto3BallIntakeDrop(swerveDrivetrain, feeder, storage, shooter, autoAlign);
  
  public RobotContainer() {
    PortForwarder.add(5800, "photonvision.local", 5800);

    configureButtonBindings();
  }
  
  private void configureButtonBindings() {
    
    swerveDrivetrain.setDefaultCommand(driveCommand);

    climb.setDefaultCommand(climbPOV);

    
    
    //FEEDER + STORAGE TOGETHER
    JoystickButton feedBallButton = new JoystickButton(operator, 1);
    feedBallButton.whileHeld(feedBall);
    
    //SHOOTER
    /*JoystickButton shooterButtonMid = new JoystickButton(operator, 3);
    //shooterButton.whenPressed(new RunCommand(()-> shooter.masterMotor.set(ControlMode.PercentOutput, 1.0), shooter));
    shooterButtonMid.whenPressed(new RunCommand(()-> shooter.setShooter(9.4), shooter));
    shooterButtonMid.whenReleased(new RunCommand(()-> shooter.stop(), shooter));

    JoystickButton shooterButtonHigh = new JoystickButton(operator, 4);
    shooterButtonHigh.whenPressed(new RunCommand(() ->shooter.setShooter(10.7), shooter));
    shooterButtonHigh.whenReleased(new RunCommand(()-> shooter.stop(), shooter));

    JoystickButton shooterButtonLow = new JoystickButton(operator, 2);
    shooterButtonLow.whenPressed(new RunCommand(()-> shooter.setShooter(8.4), shooter));
    shooterButtonLow.whenReleased(new RunCommand(()-> shooter.stop(), shooter));*/
    shooter.setDefaultCommand(shooterSpeeds);

    /*JoystickButton LEDButton = new JoystickButton(operator, 4);
    LEDButton.whenPressed(
    new RunCommand(
      () -> LED.turnOn(),
      LED)  
    );
    LEDButton.whenReleased(new RunCommand(
      () -> LED.turnOff(),
      LED)  );*/

    //INTAKE
    JoystickButton intakeOutButton = new JoystickButton(operator, 5);
    JoystickButton intakeInButton = new JoystickButton(operator, 6);
    
    intakeInButton.whileHeld(new RunCommand(()-> intake.intakeForward() , intake));
    intakeInButton.whenReleased(new RunCommand(()-> intake.stop() , intake));
  
    intakeOutButton.whileHeld(new RunCommand(()-> intake.intakeBackwards(), intake));
    intakeOutButton.whenReleased(new RunCommand(()-> intake.stop(), intake));

    JoystickButton intakeAheadButton = new JoystickButton(operator, 9);
          intakeAheadButton.whenPressed(new RunCommand(()-> intake.intakeAhead(), intake));
          JoystickButton intakeRetractButton = new JoystickButton(operator, 10);
          intakeRetractButton.whenPressed(new RunCommand(()-> intake.intakeRetract(), intake));

    storage.setDefaultCommand(storageAxisCommand);
    /*JoystickButton storageBackwards = new JoystickButton(operator, 12);
    storageBackwards.whileHeld(new RunCommand(()-> storage.bothBackward(), storage));
    storageBackwards.whenReleased(new RunCommand(()-> storage.stop(), storage));*/

    // FEEDER BACKWARDS 
    new JoystickButton(operator, 4).whileHeld(new RunCommand(()-> feeder.runBackwards(), feeder));

    //new JoystickButton(operator, 10).whileHeld(autoAlign);

    //! DRIVER JOYSTICK
    //STOP SWERVE
    JoystickButton stopSwerve = new JoystickButton(driver, 4);
    stopSwerve.whileHeld(new RunCommand(()-> swerveDrivetrain.drive(0, 0, 0, false), swerveDrivetrain));
    //stopSwerve.whenReleased(new RunCommand(()-> swerveDrivetrain.setDefaultCommand(driveCommand), swerveDrivetrain));

    //AUTO ALIGN
    JoystickButton autoAim = new JoystickButton(driver, 5);
    //autoAim.toggleWhenPressed(autoAlign);
    //autoAim.toggleWhenPressed(new ParallelCommandGroup(autoAlign, new StartEndCommand(()->shooter.setShooter(9.5), ()->shooter.setShooter(0), shooter)));
    autoAim.toggleWhenPressed(autoAlign);

  }
  // AUTO MODES
    // blindly shoot three balls and move forward
    //auto align & shoot & move
    // auto align & shoot & move forward and back 
    
    // TODO 
    // check shift mode 
    // auto align isFinished problem | getYaw nullPointerHandling
    // ? DONE, CHECK IN THE PITS
    /**
     * possible solutions for the null pointer exception:
     * run a sequential command group where you turn on the LED and then wait | ! WORKED ANOTHER WORKAROOUND
     * then run the auto align command in parallel to LED.turnOn() ?
     */

  public Command getAutonomousCommand(int auto) {
    switch (auto) {
      case 1:
        return auto3Ball;
        //return null;
      case 2:
        //return auto3BallIntake;
        return auto3Ball;
      default:
        return blindAuto;
    }
  }
}
