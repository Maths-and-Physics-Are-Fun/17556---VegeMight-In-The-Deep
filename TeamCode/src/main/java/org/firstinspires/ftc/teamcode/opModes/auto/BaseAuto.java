package org.firstinspires.ftc.teamcode.opModes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.Command;
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
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDeposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Park;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Specimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.Alliance;

@Autonomous
@Disabled
public class BaseAuto extends LinearOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();
    private MecanumDrive drive;
    Wait wait;
    DriveToConverter converter = new DriveToConverter();
    String fancytitle = " ";
    int letter = 0;
    public void initialize() {
        // Initialize the singleton hardware reference
        Alliance alliance = this.getClass().getSimpleName().contains("Blue") ? Alliance.BLUE : Alliance.RED;
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        initialize();

        waitForStart();

        // SCORE
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new InstantCommand(() -> letter=0),
                wait = new Wait(3000),
                new InstantCommand(() -> letter=1),
                wait = new Wait(3000),
                new InstantCommand(()->letter=2),
                wait = new Wait(3000),
                new InstantCommand(() -> letter=3),
                wait = new Wait(3000),
                new InstantCommand(() -> letter=4),
                wait = new Wait(3000),
                new InstantCommand(()->letter=5),
                wait = new Wait(3000),
                new InstantCommand(() -> letter=6),
                wait = new Wait(3000),
                new InstantCommand(() -> letter=7),
                wait = new Wait(3000),
                new InstantCommand(()->letter=8),
                wait = new Wait(3000)
        ));
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                // Go to specimen scoring position
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x, 30,  Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //new HoverSpecimenBeforeDeposit(),
                //wait = new Wait(500),
                //new WaitUntilCommand(wait::isFinished),
                new RRCommand((converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x, 24, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-34, 40, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-50, 7, Math.toRadians(90), DriveToConverter.MovementType.STRAFE_TO))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-50, -50, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-50, 7, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-60, 7, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_X))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-60, -50, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-60, -50, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-6, -24, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-60, -50, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                new RRCommand((converter.convertTrajectoryToAction(-6, -24, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO))),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),
                //new Specimen(),
                //wait = new Wait(300),
                //new WaitUntilCommand(wait::isFinished),
                //Go to sample scoring area
                /*new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x+10, hardware.autoDrive.pose.position.y+10, hardware.autoDrive.pose.heading.imag,DriveToConverter.MovementType.STRAFE_TO_HEADING)),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                new Idle(),
                wait = new Wait(500), */
                /*
                //Hover over sample
                new Hover(),
                wait=new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //Pickup sample
                new Grab(),
                wait=new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //Move to new location
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x, hardware.autoDrive.pose.position.y, hardware.autoDrive.pose.heading.imag,DriveToConverter.MovementType.STRAFE_TO)),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //Repeat twice
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x, hardware.autoDrive.pose.position.y, hardware.autoDrive.pose.heading.imag,DriveToConverter.MovementType.STRAFE_TO)),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //Repeated again
                new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x, hardware.autoDrive.pose.position.y, hardware.autoDrive.pose.heading.imag,DriveToConverter.MovementType.STRAFE_TO)),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                //Go to Idle
                new Idle(),
                wait = new Wait(500),
                new WaitUntilCommand(wait::isFinished),
                // Park in ascent zone
                new RRCommand(converter.convertTrajectoryToAction(-0.1, 23.5, Math.toRadians(90), DriveToConverter.MovementType.STRAFE_TO)),
                // Touch first rung
                new Park(),
                 */
                new InstantCommand(this::requestOpModeStop)
        ));
        //Cool VegeMight V :D
        if (letter==0) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::     :", fancytitle);
            telemetry.addData("::     :::::::::::::::::::::     ::", fancytitle);
            telemetry.addData(":::     :::::::::::::::::::     :::", fancytitle);
            telemetry.addData("::::     :::::::::::::::::     ::::", fancytitle);
            telemetry.addData(":::::     :::::::::::::::     :::::", fancytitle);
            telemetry.addData("::::::     :::::::::::::     ::::::", fancytitle);
            telemetry.addData(":::::::     :::::::::::     :::::::", fancytitle);
            telemetry.addData("::::::::     :::::::::     ::::::::", fancytitle);
            telemetry.addData(":::::::::     :::::::     :::::::::", fancytitle);
            telemetry.addData("::::::::::     :::::     ::::::::::", fancytitle);
            telemetry.addData(":::::::::::     :::     :::::::::::", fancytitle);
            telemetry.addData("::::::::::::     :     ::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData("::::::::::::::       ::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 1|| letter == 3) {
            //Cool VegeMight E
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter==2) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::::::                       ::::::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::      ::::::::::::::::::::     :", fancytitle);
            telemetry.addData("::      :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::      :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::         :::::", fancytitle);
            telemetry.addData("::      :::::::::::::           :::", fancytitle);
            telemetry.addData("::      ::::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::      :::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::       ::", fancytitle);
            telemetry.addData("::::::                       ::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 4) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::     :", fancytitle);
            telemetry.addData(":      :::::::::::::::::::::      :", fancytitle);
            telemetry.addData(":       :::::::::::::::::::       :", fancytitle);
            telemetry.addData(":        :::::::::::::::::        :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":          :::::::::::::          :", fancytitle);
            telemetry.addData(":     :     :::::::::::     :     :", fancytitle);
            telemetry.addData(":     ::     :::::::::     ::     :", fancytitle);
            telemetry.addData(":     :::     :::::::     :::     :", fancytitle);
            telemetry.addData(":     ::::     :::::     ::::     :", fancytitle);
            telemetry.addData(":     :::::     :::     :::::     :", fancytitle);
            telemetry.addData(":     ::::::     :     ::::::     :", fancytitle);
            telemetry.addData(":     :::::::         :::::::     :", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::     :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 5) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        }
        //

        // Move to the right
        //CommandScheduler.getInstance().schedule(new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x-65, hardware.autoDrive.pose.position.y, hardware.autoDrive.pose.heading.imag, DriveToConverter.MovementType.STRAFE_TO)));

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            telemetry.update();
        }
        // Make the hardware instance null after one run-through of the auto to stop it from looping
        hardware.nullify();
        this.requestOpModeStop();
    }
}
