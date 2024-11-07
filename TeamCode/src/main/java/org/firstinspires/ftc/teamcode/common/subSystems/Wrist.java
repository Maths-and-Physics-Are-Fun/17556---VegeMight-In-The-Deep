package org.firstinspires.ftc.teamcode.common.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Wrist extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    public Wrist() {}

    public void setToPosition(double position) {
        hardware.wristServo.setPosition(position);
    }

    public void wristPickUp() {
        setToPosition(Globals.WRIST_PICKUP);
    }

    public void wristIdle() {
        setToPosition(Globals.WRIST_IDLE);
    }

}
