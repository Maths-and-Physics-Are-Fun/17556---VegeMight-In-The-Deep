package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;
import org.firstinspires.ftc.teamcode.common.statuses.ClawStatus;

public class Claw extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private double clawTargetPosition;
    private double clawRotationTargetPosition;
    private double clawRotationState;

    public Claw() {
        clawClose();
    }

    @Override
    public void periodic() {
        hardware.clawServo.setPosition(clawTargetPosition);
    }

    private void setPosition(double position) {
        clawTargetPosition = position;
    }

    public void adjustPosition(double adjustment) {
        clawTargetPosition += adjustment;
    }

    public void clawOpen() {
        setPosition(Globals.CLAW_OPEN);
        hardware.clawStatus = ClawStatus.OPEN;
    }

    public void clawClose() {
        setPosition(Globals.CLAW_CLOSED);
        hardware.clawStatus = ClawStatus.CLOSED;
    }

    public void clawRotate(double stage) {
        if (clawRotationState+stage>5 || clawRotationState+stage<0) {
            clawRotationState = clawRotationState+0;
        }
        clawRotationState=clawRotationState+stage;

        clawRotationTargetPosition = clawRotationState*0.25;
        hardware.clawrotServo.setPosition(clawTargetPosition);
    }
}
