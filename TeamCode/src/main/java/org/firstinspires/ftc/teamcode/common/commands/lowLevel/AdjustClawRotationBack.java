package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class AdjustClawRotationBack extends InstantCommand {

    public AdjustClawRotationBack() {
        super(
                () -> HardwareReference.getInstance().claw.clawRotate(-1)
        );
    }

}

