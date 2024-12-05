package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class Hover extends CommandBase {

    HardwareReference hardware;

    public Hover() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        CommandScheduler.getInstance().schedule(
                new InstantCommand(
                        () -> hardware.wrist.wristPickUp()
                        ).alongWith(
                        new InstantCommand (() -> hardware.arm.armHover())
                        ).alongWith(
                        new InstantCommand(() -> hardware.lift.liftLow())
                        ).alongWith(
                        new InstantCommand(() -> hardware.currentStatus = ScoreSystem.HOVER)
                        ).andThen(
                            new SequentialCommandGroup(
                                new Wait(300),
                                new InstantCommand(()-> hardware.claw.clawOpen())
                            )
                        )
        );
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
