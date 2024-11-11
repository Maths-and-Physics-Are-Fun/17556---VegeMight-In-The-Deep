package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class AdjustWristDown extends InstantCommand {
    public AdjustWristDown() {
        super(
                () -> HardwareReference.getInstance().wrist.adjustPosition(-0.01)
        );
    }
}
