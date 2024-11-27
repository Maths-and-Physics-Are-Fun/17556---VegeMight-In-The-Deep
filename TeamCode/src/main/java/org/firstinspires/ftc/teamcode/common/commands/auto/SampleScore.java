package org.firstinspires.ftc.teamcode.common.commands.auto;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.DriveToConverter;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.RRCommand;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;

public class SampleScore extends SequentialCommandGroup {

    public SampleScore(double xDest) {
        super(
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 45, Math.toRadians(270), DriveToConverter.MovementType.LINE_TO_Y)).alongWith(
                    new Hover()
            ),
            new Wait(300),
            new RRCommand(DriveToConverter.convertTrajectoryToAction(xDest, 45, Math.toRadians(270), DriveToConverter.MovementType.STRAFE_TO)),
            new Wait(300),
            new Grab(),
            new Idle(),
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
            new Deposit()
            ),
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.LINE_TO_Y)),
            new Wait (700),
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.LINE_TO_Y)),
            new Idle().alongWith(
                    new RRCommand(DriveToConverter.convertTrajectoryToAction(50,50,Math.toRadians(270), DriveToConverter.MovementType.TURN))
            )
        );
    }

}
