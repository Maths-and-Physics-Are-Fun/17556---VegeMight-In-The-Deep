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
            //SCRIMMAGE
            //Robot is at POINT A (50,50)
            //Go to position of sample
            new Hover().alongWith(
                    new Wait(400).andThen(
                    new RRCommand(DriveToConverter.convertTrajectoryToAction(xDest,45, Math.toRadians(270),DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING))
                    )),
            new Wait(100),
            //Grab Sample
            new Grab(),
            new Wait(200),
            new Idle(),
            //Go to Bucket
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
                new Deposit()
            ),
            new Wait(100),
            //Move Forwards
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
            new Wait (700),
            //Deposit
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
            new Wait(300),
            //Go to Point A
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
            //Turn
            new Wait(300),
            new Idle().alongWith(
                new RRCommand(DriveToConverter.convertTrajectoryToAction(50,50,Math.toRadians(270), DriveToConverter.MovementType.TURN))
            ))
            //End


            //OLD CODE
            /*
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 45, Math.toRadians(270), DriveToConverter.MovementType.LINE_TO_Y)).alongWith(
                    new Hover()
            ),
            new Wait(300),
            new RRCommand(DriveToConverter.convertTrajectoryToAction(xDest, 45, Math.toRadians(270), DriveToConverter.MovementType.STRAFE_TO)),
            new Wait(400),
            //Grab Sample 2
            new Grab(),
            new Idle(),
            //Go to Bucket
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
            new Deposit()
            ),
            new Wait(100),
            //Move back
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
            new Wait (700),
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
            //Go to Point A
            new RRCommand(DriveToConverter.convertTrajectoryToAction(46, 50,  Math.toRadians(45), DriveToConverter.MovementType.STRAFE_TO)),
            //Turn
            new Idle().alongWith(
                    new RRCommand(DriveToConverter.convertTrajectoryToAction(50,50,Math.toRadians(270), DriveToConverter.MovementType.TURN))
            )

             */
        );
    }

}
