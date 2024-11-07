package org.firstinspires.ftc.teamcode.common.subSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Arm extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private double armTargetPosition = 0;

    public Arm() {
        armIdle();
    }

    @Override
    public void periodic() {
        hardware.leftArm.setPosition(armTargetPosition);
        hardware.rightArm.setPosition(armTargetPosition);
    }

    private void setPosition(double position) {
        armTargetPosition = position;
    }

    public void adjustPosition(double adjustment) {
        armTargetPosition += adjustment;
    }

    public void armIdle() {
        setPosition(Globals.ARM_IDLE);
    }

    public void armHover() {
        setPosition(Globals.ARM_HOVER);
    }

    public void armPickup() {
        setPosition(Globals.ARM_PICKUP);
    }

    public void armDeposit() {
        setPosition(Globals.ARM_DEPOSIT);
    }

}
