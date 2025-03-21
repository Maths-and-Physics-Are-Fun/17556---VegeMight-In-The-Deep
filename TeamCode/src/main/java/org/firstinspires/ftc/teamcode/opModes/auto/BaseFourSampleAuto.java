package org.firstinspires.ftc.teamcode.opModes.auto;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.DriveToConverter;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.RRCommand;
import org.firstinspires.ftc.teamcode.common.commands.auto.SampleScore;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.Alliance;

@Autonomous
@Disabled

public class BaseFourSampleAuto extends LinearOpMode {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private MecanumDrive drive;
    Wait wait;
    DriveToConverter converter = new DriveToConverter();
    public void initialize() {
        // Reset the command scheduler singleton
        CommandScheduler.getInstance().reset();
        // Initialize the singleton hardware reference
        // Alliance alliance = this.getClass().getSimpleName().contains("Blue") ? Alliance.BLUE : Alliance.RED;
        hardware.initHardware(hardwareMap, gamepad1, gamepad2, 37, 64, Math.toRadians(270));
        CommandScheduler.getInstance().schedule(new Idle());
    }
    @Override
    public void runOpMode(){
        initialize();

        waitForStart();
        //Add Telemetry

        //Score
        //Update Pose
        //Update Pose without going into hardware???????
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
            //Go to Sample Deposit Area
            new RRCommand(converter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
                new SequentialCommandGroup(
                    new Wait(200),
                    //Preload
                    new Deposit()
                )
            ),
            //Score 1
            new Wait(300),
            new RRCommand(converter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
            new Wait (200),
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
            new Wait(230),
            new RRCommand(converter.convertTrajectoryToAction(46, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
            new Wait (200).alongWith(
                new Idle()
            ),
            new RRCommand(converter.convertTrajectoryToAction(50,50,Math.toRadians(270), DriveToConverter.MovementType.TURN)),
            //Score 2
            new SampleScore(46),
            //Score 3
            new SampleScore(60),

            //Score 4
            new RRCommand(converter.convertTrajectoryToAction(50,41.5,Math.toRadians(315),DriveToConverter.MovementType.TURN)).alongWith(
                    new Hover().alongWith(new InstantCommand(() -> HardwareReference.getInstance().claw.clawRotSetPositionExact(0.2)))
            ),
            new RRCommand(converter.convertTrajectoryToAction(51 /*46*/, 38 /*42*/, Math.toRadians(315),DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)).alongWith(                ),
            new Wait(200),
            //Grab Sample
            new Grab(),
            new Wait(200),
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawRotSetPositionExact(0)).alongWith(
                    new Wait(200).alongWith(
                            new Idle()
                    )
            ),

            //Go to Bucket
            new RRCommand(DriveToConverter.convertTrajectoryToAction(50, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
                    new Deposit()
            ),
            new Wait(100),
            //Move Forwards
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
            new Wait (300),
            //Deposit
            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
            new Wait(200),
            //Go to Point A
            new RRCommand(DriveToConverter.convertTrajectoryToAction(55, 48,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)).alongWith(
                    new Wait(200)
            ),
            new Idle(),
        new InstantCommand(this::requestOpModeStop))

        );

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            //vegeMightAnimation();
            telemetry.addData("Arm Stage", HardwareReference.getInstance().leftArm.getPosition());
            telemetry.update();
        }
        // Make the hardware instance null after one run-through of the auto to stop it from looping
        hardware.nullify();
        this.requestOpModeStop();

    }


}
