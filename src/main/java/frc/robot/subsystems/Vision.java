// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
public class Vision extends SubsystemBase {
  
  private PhotonCamera camera;
  private PhotonPipelineResult result;
  private NetworkTableEntry yawResult;
  private NetworkTableInstance NTmain;
  private NetworkTable nt;
  
  public Vision() {
     this.camera = new PhotonCamera("compcam");
     this.NTmain = NetworkTableInstance.getDefault();
     this.nt = NTmain.getTable("photonvision").getSubTable("compcam");

  }

  public double getYaw(){
    return nt.getEntry("targetYaw").getDouble(Double.NaN);
  }

  public boolean hasTarget(){
    // ! FIXME
    return nt.getEntry("hasTarget").getBoolean(false);
  }

  @Override
  public void periodic() {
    result = camera.getLatestResult();
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("yawSD", getYaw());
  }
}
