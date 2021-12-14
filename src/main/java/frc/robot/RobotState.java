// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class RobotState {
    public enum State {
        NO_TARGET,
        VALID_TARGET,
        ALIGNING,
        SPEEDING_UP,
        READY,
        FAIL_ALIGN,
        TIMER,
        ERROR,
        DISABLED,
        ENABLED, 
        SUCCESSFUL_ALIGN
    }

    private State state;

    public RobotState() {}

    public RobotState.State getState() { 
        if (state != null){return state;}
        else {return State.DISABLED;}
    }
    public void update(RobotState.State updatedState){
        state = updatedState;
    }




}
