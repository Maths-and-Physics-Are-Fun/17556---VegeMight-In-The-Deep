package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class AdjustArm extends InstantCommand {

    public AdjustArm() {
        super(
                () -> HardwareReference.getInstance().arm.adjustPosition((double) HardwareReference.getInstance().gamepad2.right_stick_y*0.01)
        );
    }

}