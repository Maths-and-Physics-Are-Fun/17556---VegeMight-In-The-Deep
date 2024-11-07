package org.firstinspires.ftc.teamcode.common.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Claw extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    public Claw() {}

    public void setToPosition(double position) {
        hardware.clawServo.setPosition(position);
    }

    public void openClaw() {
        setToPosition(Globals.CLAW_OPEN);
    }

    public void closeClaw() {
        setToPosition(Globals.CLAW_CLOSED);
    }

}
