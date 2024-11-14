package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class SlowDownDriveTrain extends InstantCommand {
    public SlowDownDriveTrain() {
        super(
                () -> HardwareReference.getInstance().velocityAdjuster = 1
        );
    }
}
