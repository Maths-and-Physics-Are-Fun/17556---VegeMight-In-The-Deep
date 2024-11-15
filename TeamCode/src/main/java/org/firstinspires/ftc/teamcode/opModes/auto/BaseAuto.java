package org.firstinspires.ftc.teamcode.opModes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.DriveToConverter;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.RRCommand;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Park;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;

@Autonomous
public class BaseAuto extends LinearOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();
    private MecanumDrive drive;
    Wait wait;
    DriveToConverter converter = new DriveToConverter();

    private void initialize() {
        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        initialize();

        waitForStart();

        // SCORE
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                // Go to scoring position
                new RRCommand(converter.convertTrajectoryToAction(52.3, 52.91, Math.toRadians(45))),
                new Deposit(),
                wait = new Wait(),
                new WaitUntilCommand(wait::isFinished),
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x+2, hardware.autoDrive.pose.position.y+2, hardware.autoDrive.pose.heading.imag)),
                wait = new Wait(),
                new WaitUntilCommand(wait::isFinished),
                new InstantCommand(() -> hardware.claw.clawOpen()),
                wait = new Wait(),
                new WaitUntilCommand(wait::isFinished),
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x-4, hardware.autoDrive.pose.position.y-4, hardware.autoDrive.pose.heading.imag)),
                wait = new Wait(),
                new WaitUntilCommand(wait::isFinished),
                new Idle(),
                wait = new Wait(),
                new WaitUntilCommand(wait::isFinished),
                // Park in ascent zone
                new RRCommand(converter.convertTrajectoryToAction(-0.1, 23.5, Math.toRadians(90))),
                // Touch first rung
                new Park(),
                new InstantCommand(this::requestOpModeStop)
        ));

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            telemetry.update();
        }

        // Make the hardware instance null after one run-through of the auto to stop it from looping
        hardware.nullify();
        this.requestOpModeStop();
    }
}
