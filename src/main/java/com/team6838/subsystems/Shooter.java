package com.team6838.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team6838.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.wpilibj.controller.PIDController;


public class Shooter extends SubsystemBase{
    public static WPI_TalonSRX masterMotor;
    public static WPI_VictorSPX slaveMotor;
    private Encoder shooterEncoder;
    protected abstract double getMeasurement();

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Shooter.kS, Constants.Shooter.kV, Constants.Shooter.kA);


    private double kP; //proportional
    private double kI; //integral
    private double kD; //derivative

    private double sP; //setpoint
    private double pV; //Present/process value
    private double error = sP - pV;


    public Shooter(){
        masterMotor = new WPI_TalonSRX(Constants.Shooter.k_SHOOTER_MASTER_MOTOR);
        slaveMotor = new WPI_VictorSPX(Constants.Shooter.k_SHOOTER_SLAVE_MOTOR);
        shooterEncoder = = new Encoder(new DigitalInput(Constants.shooterEncoderA),
            new DigitalInput(Constants.shooterEncoderB), true, EncodingType.k4X);

        slaveMotor.follow(masterMotor);
    }

    public void setpower(double speed){
        masterMotor.set(speed);
    }

    public void getRPM(){
        return shooterEncoder.getRate() * 60.;
    }

    public void stop(){
        masterMotor.set(0);
    }
    //denemegjklerjgkljkljdfgdfgdf
    public boolean isAtRPM(int RPM){
        if(RPM == Shooter.getRPM()){
            return true;
        }
        return false;
    }

    public void shooterFeedForward(){

    }

    public void setRPM(int rpm){

        PIDController pid = new PIDController(kP,kI,kD);
        masterMotor.set(pid.calculate(shooterEncoder.getRPM(),rpm));
        
    }

    public void setMotorOppositeWay(){
        masterMotor.set(-0.2);
    }



    //setRPM(rpm) (PID algoritması falan)
    //getDistanceForRPM() return int distance


    
    
}
