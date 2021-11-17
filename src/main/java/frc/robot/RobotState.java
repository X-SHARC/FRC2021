// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class RobotState {
    private enum State {
        NO_TARGET,
        ALIGNING,
        READY,
        SPEEDINGUP,
        SHOOTING,
        FAIL_ALIGN,
        TIMER,
        ERROR
    }
    private enum MatchPeriod {
        AUTONOMOUS,
        TELEOP,
        ENDGAME
    }

    MatchPeriod matchPeriod;
    State state;

    public RobotState(State state) {

    }

    public void setPeriod(MatchPeriod matchPeriod) {
        this.matchPeriod = matchPeriod;
    }

}
