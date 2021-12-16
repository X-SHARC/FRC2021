// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
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
  Intake intake;
  
  public BlindAuto(
    Swerve swerve,
    Feeder feeder,
    Storage storage,
    Shooter shooter, Intake intake) {

    this.shooter = shooter;
    this.feeder = feeder;
    this.storage = storage;
    this.swerve = swerve;
    this.intake = intake;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RunCommand(()-> shooter.setShooter(12.1), shooter).withTimeout(3),
      new ParallelCommandGroup(
        new RunCommand(()-> shooter.setShooter(12.1), shooter){
          @Override
          public void end(boolean interrupted) {
              // TODO Auto-generated method stub
              super.end(interrupted);
              shooter.setShooter(0);
          }
        },
        new RunCommand(() -> feeder.runForward(), feeder){
          @Override
          public void end(boolean interrupted) {
              // TODO Auto-generated method stub
              super.end(interrupted);
              feeder.stop();
          }
        },
        new RunCommand(() -> storage.bothForward(), storage){
          @Override
          public void end(boolean interrupted) {
              // TODO Auto-generated method stub
              super.end(interrupted);
              storage.stop();
          }
        },

        new RunCommand(()-> intake.intakeBackwards(), intake){
          @Override
          public void end(boolean interrupted) {
              // TODO Auto-generated method stub
              intake.stop();
          }
        }

      ).withTimeout(2.9),
      
      new RunCommand(()-> swerve.drive(0.4, 0, 0, false), swerve){
        @Override
        public void end(boolean interrupted){
          swerve.drive(0, 0, 0, false);
        }
    
        }.withTimeout(1.5)

        

    );
  }
}
