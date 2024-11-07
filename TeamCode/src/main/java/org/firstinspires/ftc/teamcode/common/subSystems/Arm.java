package org.firstinspires.ftc.teamcode.common.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Arm extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    public Arm() {}

    @Override
    public void periodic() {
        hardware.leftArm.setPosition(hardware.armTargetPosition);
        hardware.rightArm.setPosition(hardware.armTargetPosition);
    }

    public void setPosition(double position) {
        hardware.armTargetPosition = position;
    }

    public void adjustPosition(double adjustment) {
        hardware.armTargetPosition += adjustment;
    }

    public void armIdle() {
        setToPosition(Globals.ARM_IDLE);
    }

}
