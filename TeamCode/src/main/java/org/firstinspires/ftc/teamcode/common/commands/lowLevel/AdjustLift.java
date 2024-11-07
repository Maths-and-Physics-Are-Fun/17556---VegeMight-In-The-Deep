package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class AdjustLift extends InstantCommand {

    public AdjustLift() {
        super(
                () -> HardwareReference.getInstance().lift.adjustPosition((int) HardwareReference.getInstance().gamepad2.left_stick_y*20)
        );
    }

}