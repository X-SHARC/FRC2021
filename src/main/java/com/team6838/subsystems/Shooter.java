package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team6838.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;


public class Shooter extends SubsystemBase{
    public static WPI_TalonSRX masterMotor;
    public static WPI_VictorSPX slaveMotor;
    private Encoder shooterEncoder;

    private double kP; //proportional
    private double kI; //integral
    private double kD; //derivative
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Shooter.kS, Constants.Shooter.kV, Constants.Shooter.kA);
    private PIDController pid = new PIDController(kP,kI,kD);

    private double ENCODER_EDGES_PER_REV = 4096 / 4.;
    private double encoderConstant = (1 / ENCODER_EDGES_PER_REV);
    

    

    public Shooter(){
        masterMotor = new WPI_TalonSRX(Constants.Shooter.k_SHOOTER_MASTER_MOTOR);
        slaveMotor = new WPI_VictorSPX(Constants.Shooter.k_SHOOTER_SLAVE_MOTOR);
        //constants parametrelerden emin deilim
        shooterEncoder = new Encoder(new DigitalInput(Constants.Shooter.k_SHOOTER_SLAVE_MOTOR),
            new DigitalInput(Constants.Shooter.k_SHOOTER_MASTER_MOTOR), true, EncodingType.k4X);
        
        slaveMotor.setInverted(false);
        masterMotor.setInverted(false);
        slaveMotor.follow(masterMotor);

        shooterEncoder.setDistancePerPulse(encoderConstant);
    }

    public void setPercentage(double percentage){
        masterMotor.set(percentage); // [-1, 1]
    }

    public double getRPM(){
        return shooterEncoder.getRate() * 60.;
    }

    public void stop(){
        masterMotor.set(0);
    }
    public boolean isAtRPM(int RPM){
        if(Math.abs(getRPM() - RPM) <= 100){
            return true;
        }
        return false;
    }

    // ? decide whether we should use PIDcommand or setRPM in the subsystem
    public void setRPM(int rpm){        
        masterMotor.set(pid.calculate(getRPM(),rpm)+feedforward.calculate(getRPM()/60, rpm/60));        
    }

    public void runBackwards(){
        masterMotor.set(-0.2);
    }
    
}
