package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class Park extends SequentialCommandGroup {
    public Park() {
        super(
                new InstantCommand(() -> HardwareReference.getInstance().arm.armPark()),
                new InstantCommand(() -> HardwareReference.getInstance().wrist.wristPark())
        );
    }
}
