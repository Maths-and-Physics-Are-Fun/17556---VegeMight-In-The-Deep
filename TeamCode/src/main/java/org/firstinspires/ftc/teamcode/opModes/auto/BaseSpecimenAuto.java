package org.firstinspires.ftc.teamcode.opModes.auto;

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
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
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
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);
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
                new Wait(3000)
        ));
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                // Go to specimen scoring position
                new RRCommand(converter.convertTrajectoryToAction(hardware.currentPose.position.x, 37.5,  Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)).alongWith(
                // Score
                new HoverSpecimenBeforeDeposit()),
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

                // Go to intermitent position
                new RRCommand(converter.convertTrajectoryToAction(-36, 40, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO)),

                // Go to sample 1
                new RRCommand(converter.convertTrajectoryToAction(-36, 10, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Strafe in front of the sample
                new RRCommand(converter.convertTrajectoryToAction(-52, 10, Math.toRadians(90), DriveToConverter.MovementType.STRAFE_TO)),

                // Push Sample 1 into the observation zone
                new RRCommand(converter.convertTrajectoryToAction(-52, 55, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),

                // Go backwards to Sample 2
                new RRCommand(converter.convertTrajectoryToAction(-52, 7, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Strafe in front of Sample 2
                new RRCommand(converter.convertTrajectoryToAction(-62, 7, Math.toRadians(90), DriveToConverter.MovementType.STRAFE_TO)),

                // Push Sample 2 into the observation zone
                new RRCommand(converter.convertTrajectoryToAction(-62, 55, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Go backwards slightly
                new RRCommand(converter.convertTrajectoryToAction(-62, 45, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Take our arm
                // Pick up sample

                // Go back to submersible
                new RRCommand(converter.convertTrajectoryToAction(-9, 27, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                // Score

                // Go backwards
                new RRCommand(converter.convertTrajectoryToAction(HardwareReference.getInstance().currentPose.position.x, 45, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)),
                wait = new Wait(0),
                new WaitUntilCommand(wait::isFinished),

                // Go to observation zone
                new RRCommand(converter.convertTrajectoryToAction(-62, 55, Math.toRadians(90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),

                // Go backwards slightly
                new RRCommand(converter.convertTrajectoryToAction(-62, 45, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

                // Take our arm
                // Pick up sample

                // Go to submersible
                new RRCommand(converter.convertTrajectoryToAction(-9, 27, Math.toRadians(-90), DriveToConverter.MovementType.SPLINE_TO_LINEAR_HEADING)),
                wait = new Wait(300),
                new WaitUntilCommand(wait::isFinished),

                // Score

                // Go backwards
                new RRCommand(converter.convertTrajectoryToAction(HardwareReference.getInstance().currentPose.position.x, 45, Math.toRadians(-90), DriveToConverter.MovementType.LINE_TO_Y)),
                wait = new Wait(0),
                new WaitUntilCommand(wait::isFinished),

                // Park in observation
                new RRCommand(converter.convertTrajectoryToAction(-62, 55, Math.toRadians(90), DriveToConverter.MovementType.LINE_TO_Y)),

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
        } else if (letter==2 || letter==6) {
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
        }  else if (letter == 7) {
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
        } else if (letter == 8) {
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
    }

}
