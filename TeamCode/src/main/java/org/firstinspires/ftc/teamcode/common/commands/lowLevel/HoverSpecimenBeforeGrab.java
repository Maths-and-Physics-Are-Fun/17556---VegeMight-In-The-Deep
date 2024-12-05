package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class HoverSpecimenBeforeGrab extends CommandBase {
    HardwareReference hardware;

    public HoverSpecimenBeforeGrab() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() { //Make specimen deposit

        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> hardware.wrist.wristSpecimenPickUp()
        ).alongWith(
                new InstantCommand(()-> hardware.arm.armSpecimenPickUp())
        ).alongWith(
                new InstantCommand(() -> hardware.lift.liftLow())
        ).alongWith(
                new InstantCommand(() -> hardware.currentStatus = ScoreSystem.HOVER_SPECIMEN_BEFORE_GRAB)
        ).alongWith(
                new InstantCommand(()-> hardware.claw.clawRotSetPosition(0))
        ).andThen(
                new SequentialCommandGroup(
                        new Wait(300),
                        new InstantCommand(()-> hardware.claw.clawOpen())
                )
        ));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
