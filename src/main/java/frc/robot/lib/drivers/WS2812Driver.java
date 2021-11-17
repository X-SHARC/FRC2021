// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib.drivers;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WS2812Driver extends SubsystemBase {
  private static AddressableLED m_led;
  private static AddressableLEDBuffer m_ledBuffer;

  public WS2812Driver(int dataPort, int ledLength) {
    m_led = new AddressableLED(dataPort);
    m_ledBuffer = new AddressableLEDBuffer(ledLength);
    m_led.setLength(m_ledBuffer.getLength());

    setBufferColor(0, 255, 255);
    m_led.start();
  }

  @Override
  public void periodic() {
    //setBufferColor(0, 255, 255);
  }
  public static void setBufferColor(int r, int g, int b) {

    for (int i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, r, g, b);
    }
    m_led.setData(m_ledBuffer);
}

public void turnOff() {
    setBufferColor(0, 0, 0);
    m_led.setData(m_ledBuffer);
}
}
