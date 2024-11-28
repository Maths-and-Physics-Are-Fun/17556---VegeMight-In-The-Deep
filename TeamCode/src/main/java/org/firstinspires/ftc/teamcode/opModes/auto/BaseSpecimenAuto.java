package org.firstinspires.ftc.teamcode.opModes.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.DriveToConverter;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.RRCommand;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDeposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDepositTeleOp;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeGrab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.PickUpSpecimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Specimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.Alliance;

@Autonomous
@Disabled
public class BaseSpecimenAuto extends LinearOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();
    private MecanumDrive drive;
    Wait wait;
    DriveToConverter converter = new DriveToConverter();
    String fancytitle = " ";
    int letter = 0;
    public void initialize() {
        // Reset the command scheduler singleton
        CommandScheduler.getInstance().reset();
                // Initialize the singleton hardware reference
        Alliance alliance = this.getClass().getSimpleName().contains("Blue") ? Alliance.BLUE : Alliance.RED;
        hardware.initHardware(hardwareMap, gamepad1, gamepad2, -9, 65, Math.toRadians(270));
        CommandScheduler.getInstance().schedule(new Idle());
    }

    @Override
    public void runOpMode() {
       initialize();

        waitForStart();

        // SCORE
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new InstantCommand(() -> letter=0),
                new Wait(3000),
                new InstantCommand(() -> letter=1),
                new Wait(3000),
                new InstantCommand(()->letter=2),
                new Wait(3000),
                new InstantCommand(() -> letter=3),
                new Wait(3000),
                new InstantCommand(() -> letter=4),
                new Wait(3000),
                new InstantCommand(()->letter=5),
                new Wait(3000),
                new InstantCommand(() -> letter=6),
                new Wait(3000),
                new InstantCommand(() -> letter=7),
                new Wait(3000),
                new InstantCommand(()->letter=8),
                new Wait(3000),
                new InstantCommand(()->letter=9),
                new Wait(3000)
        ));
        TrajectoryActionBuilder trajActionBuilder;
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                // Go to specimen scoring position
                new RRCommand(converter.convertTrajectoryToAction(hardware.currentPose.position.x, 37.5,  Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)).alongWith(
                // Score
                    new SequentialCommandGroup(
                        new Wait(500),
                        new HoverSpecimenBeforeDeposit()
                    )
                ),

                new Wait(500),
                new Specimen(),
                new Wait(500),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                new Wait(1),
                new Idle().alongWith(
                // Go backwards
                new RRCommand(converter.convertTrajectoryToAction(HardwareReference.getInstance().currentPose.position.x, 45, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)),
                wait = new Wait(0),
                new WaitUntilCommand(wait::isFinished)),

                new RRCommand(HardwareReference.getInstance().autoDrive.actionBuilder(HardwareReference.getInstance().currentPose)
                        // Go to intermitent position
                        .splineTo(new Vector2d( -36, 40), Math.toRadians(90))
                        // Go to sample 1
                        .lineToY(10)
                        // Strafe in front of the sample
                        .strafeTo(new Vector2d(-52, 10))
                        // Push Sample 1 into the observation zone
                        .splineToLinearHeading(new Pose2d(-52, 64, Math.toRadians(90)), Math.toRadians(90))
                        // Go backwards slightly
                        .lineToY(48)
                        .build()
                ),

                // Go to intermitent position
                //new RRCommand(converter.convertTrajectoryToAction(-36, 40, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO)),

                // Go to sample 1
                //new RRCommand(converter.convertTrajectoryToAction(-36, 10, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Strafe in front of the sample
                //new RRCommand(converter.convertTrajectoryToAction(-52, 10, Math.toRadians(90), DriveToConverter.MovementType.STRAFE_TO)),

                // Push Sample 1 into the observation zone
                //new RRCommand(converter.convertTrajectoryToAction(-52, 64, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),

                // Go backwards slightly
                //new RRCommand(converter.convertTrajectoryToAction(-52, 48, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),
                new HoverSpecimenBeforeGrab(),
                new Wait(800),
                new PickUpSpecimen(),

                // Take our arm
                // Pick up sample

                // Go back to submersible
                new RRCommand(converter.convertTrajectoryToAction(-4, 31, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)).alongWith(
                  new HoverSpecimenBeforeDeposit()
                ),
                new Specimen(),
                new Wait(500),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                new Wait(1),
                new Idle().alongWith(
                                new RRCommand(converter.convertTrajectoryToAction(HardwareReference.getInstance().currentPose.position.x, 45, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y))),

                // Go to observation zone
                new RRCommand(converter.convertTrajectoryToAction(-52, 44, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),

                // Take our arm
                // Pick up sample
                new HoverSpecimenBeforeGrab(),
                new Wait(800),
                new PickUpSpecimen(),
                // Go to submersible
                new RRCommand(converter.convertTrajectoryToAction(0, 27, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)).alongWith(
                        new HoverSpecimenBeforeDeposit()
                ),


                // Score

                // Go backwards
                new RRCommand(converter.convertTrajectoryToAction(HardwareReference.getInstance().currentPose.position.x, 45, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)),
                new Specimen(),
                new Wait(500),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                new Wait(1),
                new Idle(),

                // Park in observation
                new RRCommand(converter.convertTrajectoryToAction(-62, 55, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),
                new InstantCommand(() -> HardwareReference.getInstance().flag.FlagUp()),
                new InstantCommand(this::requestOpModeStop)
        ));

        // Move to the right
        //CommandScheduler.getInstance().schedule(new RRCommand(converter.convertTrajectoryToAction(hardware.autoDrive.pose.position.x-65, hardware.autoDrive.pose.position.y, hardware.autoDrive.pose.heading.imag, DriveToConverter.MovementType.STRAFE_TO)));

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            vegeMightAnimation();
            telemetry.addData("Arm Stage", HardwareReference.getInstance().leftArm.getPosition());
            telemetry.update();
        }
        // Make the hardware instance null after one run-through of the auto to stop it from looping
        hardware.nullify();
        this.requestOpModeStop();
    }

    private void vegeMightAnimation() {
        //Cool VegeMight V :D
        if (letter==0) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      :::::::::::::::::::::      :", fancytitle);
            telemetry.addData("::      :::::::::::::::::::      ::", fancytitle);
            telemetry.addData(":::      :::::::::::::::::      :::", fancytitle);
            telemetry.addData("::::      :::::::::::::::      ::::", fancytitle);
            telemetry.addData(":::::      :::::::::::::      :::::", fancytitle);
            telemetry.addData("::::::      :::::::::::      ::::::", fancytitle);
            telemetry.addData(":::::::      :::::::::      :::::::", fancytitle);
            telemetry.addData("::::::::      :::::::      ::::::::", fancytitle);
            telemetry.addData(":::::::::      :::::      :::::::::", fancytitle);
            telemetry.addData("::::::::::      :::      ::::::::::", fancytitle);
            telemetry.addData(":::::::::::      :      :::::::::::", fancytitle);
            telemetry.addData("::::::::::::           ::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData("::::::::::::::       ::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 1|| letter == 3) {
            //Cool VegeMight E
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter==2 || letter==6) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::::::                       ::::::", fancytitle);
            telemetry.addData(":::                              ::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::::      :", fancytitle);
            telemetry.addData("::       ::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::       ::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::         :::::", fancytitle);
            telemetry.addData("::       ::::::::::::           :::", fancytitle);
            telemetry.addData("::       :::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::                              ::", fancytitle);
            telemetry.addData("::::::                        :::::", fancytitle);
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
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        }  else if (letter == 7) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 8) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 9 ){
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        }
    }

}
