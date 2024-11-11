package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;

public class Grab extends SequentialCommandGroup {

    private static Wait wait;

    public Grab() {
        super(
            new InstantCommand(() -> HardwareReference.getInstance().wrist.wristPickUp()),
            new InstantCommand(() -> HardwareReference.getInstance().arm.armPickup()),
            new InstantCommand(() -> HardwareReference.getInstance().lift.liftLow()),
            wait = new Wait(),
            new WaitUntilCommand(wait::isFinished),
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawClose()),
            new InstantCommand(() -> HardwareReference.getInstance().currentStatus = ScoreSystem.GRAB)
        );
    }

}