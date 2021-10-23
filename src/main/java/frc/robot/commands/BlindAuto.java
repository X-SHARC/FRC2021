// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Swerve;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class BlindAuto extends SequentialCommandGroup {
  Shooter shooter; 
  Feeder feeder;
  Storage storage;
  Swerve swerve;
  
  public BlindAuto(
    Swerve swerve,
    Feeder feeder,
    Storage storage,
    Shooter shooter) {

    this.shooter = shooter;
    this.feeder = feeder;
    this.storage = storage;
    this.swerve = swerve;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RunCommand(()-> shooter.setShooter(1.0), shooter).withTimeout(3),
      new ParallelCommandGroup(
        new RunCommand(()-> shooter.setShooter(1.0), shooter),
        new RunCommand(() -> feeder.runForward(), feeder),
        new RunCommand(() -> storage.bothForward(), storage)
      ).withTimeout(6),
      
      new RunCommand(()-> swerve.drive(0, 0.4, 0, false), swerve){
        @Override
        public void end(boolean interrupted){
          swerve.drive(0, 0, 0, false);
        }
    
        }.withTimeout(3)

    );
  }
}
