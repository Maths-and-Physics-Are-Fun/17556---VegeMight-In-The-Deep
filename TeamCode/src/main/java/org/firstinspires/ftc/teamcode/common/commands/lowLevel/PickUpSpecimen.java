package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class PickUpSpecimen extends SequentialCommandGroup {
    public PickUpSpecimen() {
        super(
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawClose()).andThen(new Wait(500)),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawRotSetPosition(0)).andThen(
                new InstantCommand(() -> HardwareReference.getInstance().lift.liftPickup()).andThen(new Wait(500))),
                new Idle()
        );
    }
}
