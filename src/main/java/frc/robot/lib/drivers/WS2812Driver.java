// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib.drivers;

import java.util.concurrent.ThreadLocalRandom;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WS2812Driver extends SubsystemBase {
  private static AddressableLED m_led;
  private static AddressableLEDBuffer m_ledBuffer;
  int[] rgb = new int[15];
  boolean isRGB = false;
  int breathe = 255;
  boolean breatheReversed = false;
  int breatheH = 10;

  public WS2812Driver(int dataPort, int ledLength) {
    m_led = new AddressableLED(dataPort);
    m_ledBuffer = new AddressableLEDBuffer(ledLength);
    m_led.setLength(m_ledBuffer.getLength());

    setBufferColor(0, 0, 0);
    //toggleRGB();
    //toggleRGB();
    m_led.start();
  }

  @Override
  public void periodic() {
    //toggleRGB();
    //breathe();
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

public void toggleRGB(){
  if(!isRGB){
    for (int i = 0; i < m_ledBuffer.getLength(); i++) {
      rgb[i] = i * 180/m_ledBuffer.getLength();
    }
    isRGB = true;
  }
  shiftArray(rgb);

  for (int i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setHSV(i, rgb[i], 255, 255); 
  }

  //length -> 180 degrees
  //1 -> 180/length
  //
  m_led.setData(m_ledBuffer);
}

public int[] shiftArray(int[] array){
  int last = array[array.length-1];
  for(int i = array.length-1; i > 0; i--){
    array[i] = array[i-1];
  }
  array[0] = last;
  return array;
}

public void setRandom(){
  for (int i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setHSV(i, (int) (Math.random()*254+1), 255, 255); 
  }
  m_led.setData(m_ledBuffer);

}

public void breathe(){

  for (int i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setHSV(i, breatheH, 255, breathe); 
  }
  if(!breatheReversed){
    breathe -= 3;
    if(breathe == 0){
      breatheReversed = true;
      //if(breatheH > 255 || breatheH < 0) breatheH = 5;
      breatheH =  ThreadLocalRandom.current().nextInt(1, 256);
    }
  }
  if(breatheReversed){
    breathe += 3;
    if(breathe == 255) breatheReversed = false;
  }
  
  m_led.setData(m_ledBuffer);

}

public void symetricLaser(){
  /** [x,x,x,x,x,x,x,x,x]
   * [0,0,0,0,x,0,0,0,0]
   * [0,0,0,x,0,x,0,0,0]
   * [0,0,x,0,0,0,x,0,0]
   * ...
   * [x,0,0,0,0,0,0,0,x]
   * 
   */
}

public void showPercentage(double percent) {
  int LEDCount = (int) Math.round(percent * 8.);
  int emptyLEDCount = 8 - LEDCount;

  for (int i = 0; i < m_ledBuffer.getLength(); i++) {
    if(i+1 <= emptyLEDCount || i+1 >= 15-emptyLEDCount) {
      m_ledBuffer.setRGB(i, 153, 0, 0);
    }
    else m_ledBuffer.setRGB(i, 0, 153, 51);
  }
  m_led.setData(m_ledBuffer);
}
}
