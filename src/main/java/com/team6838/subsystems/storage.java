/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team6838.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.team6838.Constants;

public class storage extends SubsystemBase {

  private WPI_TalonSRX storageOne = new WPI_TalonSRX (Constants.Storage.storagePortRight);

  private WPI_TalonSRX storagetwo = new WPI_TalonSRX (Constants.Storage.storagePortLeft);

  boolean isLocked = false;

  private final PowerDistributionPanel m_pdp;

  public storage(PowerDistributionPanel PDP) {
    this.m_pdp = PDP;
  }


public void runStorageForward() {
  if (!isLocked) {
    storageOne.set(Constants.Storage.storageSpeed);
    storagetwo.set(Constants.Storage.storageSpeed);  
  }
}
    

public void runStorageBackwards() {
  if (!isLocked) {
    storageOne.set(-Constants.Storage.storageSpeed);
    storagetwo.set(-Constants.Storage.storageSpeed);
  }
  
}


public void runStorage(double speed) {
  if (!isLocked) {
    storageOne.set(speed);
    storagetwo.set(speed);
  }
}


public void stopStorage() {
  storageOne.set(0);
  storagetwo.set(0);
}


public void storageInverseFix() {
  if (!isLocked) {
  storageOne.set(Constants.Storage.storageSpeed);
  storagetwo.set(Constants.Storage.storageSpeed * -1);
 }
}


  @Override
  public void periodic() {
    if (m_pdp.getCurrent(Constants.storagePDPChannel) >= Constants.storageCurrentThreshold) {
      isLocked = true;
    }
    else {
      if(isLocked){
        isLocked = false;
      }
    }
  }
}
