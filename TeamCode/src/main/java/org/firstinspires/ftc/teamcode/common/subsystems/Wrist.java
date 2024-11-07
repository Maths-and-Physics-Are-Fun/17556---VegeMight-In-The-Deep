package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Wrist extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private double wristTargetPosition;

    public Wrist() {
        wristIdle();
    }

    @Override
    public void periodic() {
        hardware.wristServo.setPosition(wristTargetPosition);
    }

    private void setPosition(double position) {
        wristTargetPosition = position;
    }

    public void adjustPosition(double adjustment) {
        wristTargetPosition += adjustment;
    }

    public void wristPickUp() {
        setPosition(Globals.WRIST_PICKUP);
    }

    public void wristIdle() {
        setPosition(Globals.WRIST_IDLE);
    }

}
