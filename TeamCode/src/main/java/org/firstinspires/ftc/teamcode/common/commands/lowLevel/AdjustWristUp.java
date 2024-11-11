package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class AdjustWristUp extends InstantCommand {
    public AdjustWristUp() {
        super(
                () -> HardwareReference.getInstance().wrist.adjustPosition(0.01)
        );
    }
}