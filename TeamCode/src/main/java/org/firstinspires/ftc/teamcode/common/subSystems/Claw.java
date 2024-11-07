package org.firstinspires.ftc.teamcode.common.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Claw extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private double clawTargetPosition;

    public Claw() {
        closeClaw();
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

    public void openClaw() {
        setPosition(Globals.CLAW_OPEN);
    }

    public void closeClaw() {
        setPosition(Globals.CLAW_CLOSED);
    }

}
