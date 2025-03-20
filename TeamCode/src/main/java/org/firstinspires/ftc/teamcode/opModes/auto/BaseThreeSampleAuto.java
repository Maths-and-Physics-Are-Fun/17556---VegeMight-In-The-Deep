package org.firstinspires.ftc.teamcode.opModes.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.onbotjava.handlers.objbuild.WaitForBuild;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.DriveToConverter;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.RRCommand;
import org.firstinspires.ftc.teamcode.common.commands.auto.SampleScore;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Park;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.Alliance;

@Autonomous
@Disabled

public class BaseThreeSampleAuto extends LinearOpMode {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private MecanumDrive drive;
    Wait wait;
    DriveToConverter converter = new DriveToConverter();
    public void initialize() {
        // Reset the command scheduler singleton
        CommandScheduler.getInstance().reset();
        // Initialize the singleton hardware reference
        Alliance alliance = this.getClass().getSimpleName().contains("Blue") ? Alliance.BLUE : Alliance.RED;
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
                                new Wait(500),
                                new Deposit()
                        )
                ),
                new Wait(500),
                new RRCommand(converter.convertTrajectoryToAction(55, 55, Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
                new Wait (400),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                new Wait(100),
                new RRCommand(converter.convertTrajectoryToAction(46, 50,  Math.toRadians(45), DriveToConverter.MovementType.SPLINE_TO_CONSTANT_HEADING)),
                new Idle().alongWith(
                        new RRCommand(converter.convertTrajectoryToAction(50,50,Math.toRadians(270), DriveToConverter.MovementType.TURN))
                ),
                new SampleScore(45),
                new SampleScore(60),

                new RRCommand(converter.convertTrajectoryToAction(18, 18,  Math.toRadians(180), DriveToConverter.MovementType.SPLINE_TO)).alongWith(
                        //Park
                        new Park()
                ),
                new InstantCommand(this::requestOpModeStop)

        ));

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