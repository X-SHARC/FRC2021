package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team6838.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase{
    public static WPI_TalonSRX masterMotor;
    public static WPI_TalonSRX slaveMotor;

    public Shooter(){
        masterMotor = new WPI_TalonSRX(Constants.Shooter.SHOOTER_MASTER_MOTOR);
        slaveMotor = new WPI_TalonSRX(Constants.Shooter.SHOOTER_SLAVE_MOTOR);

        slaveMotor.follow(masterMotor);
    }

    
    
}
