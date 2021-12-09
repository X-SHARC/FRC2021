// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib.drivers;

import java.util.concurrent.ThreadLocalRandom;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotState;

public class WS2812Driver extends SubsystemBase {
  private static AddressableLED m_led;
  private static AddressableLEDBuffer m_ledBuffer;
  int[] rgb = new int[15];
  boolean isRGB = false;
  int breathe = 255;
  boolean breatheReversed = false;
  int breatheH = 10;
  int blinkCount = 0; 

  public WS2812Driver(int dataPort, int ledLength) {
    m_led = new AddressableLED(dataPort);
    m_ledBuffer = new AddressableLEDBuffer(ledLength);
    m_led.setLength(m_ledBuffer.getLength());

    //setColor(0, 0, 0);
    toggleRGB();
    //breathe();
    //showPercentage(0.5);
    //blink(0, 255, 0);
    m_led.start();
  }

  @Override
  public void periodic() {
    toggleRGB();
    //breathe();
    //blink(0, 255, 0);
  }
  public static void setColor(int r, int g, int b) {

    for (int i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, r, g, b);
    }
    m_led.setData(m_ledBuffer);
}

public void turnOff() {
    setColor(0, 0, 0);
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

public void initialize(){
  // TODO decide if you want to have an init sequence everytime robot boots up
}

public void update(RobotState.State state){
  switch(state){
    case NO_TARGET:
      setColor(255, 0, 0); //show red when theere is no target
    case VALID_TARGET:
      setColor(255, 106, 0); //show orange when there is target within range
    case ALIGNING:
      blink(0, 255, 0); //blink red when aligning
    case SPEEDING_UP:
      blink(0, 0, 255); //blink blue when shooter is speeding up
    case READY:
      toggleRGB();
    case FAIL_ALIGN:
      blink(255, 0, 0); //blink red when auto-align fails
    case SUCCESSFUL_ALIGN:
      setColor(0, 255, 0);
    case TIMER:
      double percentage = (DriverStation.getInstance().getMatchTime() / Robot.totalMatchTime);
      if (!(percentage >= 0)) percentage = 0;
      showPercentage(percentage); //shows the remaining time in the match
    case ERROR:
      blink(98, 0, 255); //blink purple when major error is encountered
    case DISABLED:
      breathe();
    case ENABLED:
      setColor(255, 128, 0);
  }
}

private void blink(int r, int g, int b) {
  int blinkRate = 6;
  if(blinkCount <= blinkRate) setColor(r, g, b);
  else if ( blinkCount <= blinkRate*2) setColor(0, 0, 0);
  blinkCount++;
  if( blinkCount > blinkRate*2 || blinkCount <= 0) blinkCount = 1;
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
  int LEDCount = (int) Math.round(percent * 7.);
  int emptyLEDCount = 7 - LEDCount;
  // x x x x x x
  for (int i = 0; i < m_ledBuffer.getLength(); i++) {
    if(i+1 <= emptyLEDCount || i+1 >= 15-emptyLEDCount) m_ledBuffer.setRGB(i, 153, 0, 0);
    else m_ledBuffer.setRGB(i, 0, 153, 51);
  }
  m_led.setData(m_ledBuffer);
}
}
