/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team6838.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.team6838.Constants;




public class storage extends SubsystemBase {
 
  int storagePortRight = 0;
  int storagePortLeft = 0;
  
  int storageSpeed = 0;
  int storageSpeed2 = 0;

  private WPI_TalonSRX storageOne = new WPI_TalonSRX (Constants.storagePortRight);
  
  private WPI_TalonSRX storagetwo = new WPI_TalonSRX (Constants.storagePortLeft);

  

public void runStorageForward() {
  storageOne.set(Constants.storageSpeed);
  storagetwo.set(Constants.storageSpeed);  
}
    
public void runStorageBackward() {
  storageOne.set(Constants.storageSpeed2);
  storagetwo.set(Constants.storageSpeed2);
}

public void runStorage() {
  storageOne.set(Constants.storageSpeed);
  storagetwo.set(Constants.storageSpeed);
}

public void storageStopper() {
  storageOne.set(0);
  storagetwo.set(0);
}

// Topun sıkışması durumunda 

public void storageInverseFix() {
  storageOne.set(storageSpeed);
  storagetwo.set(storageSpeed * -1);



}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
