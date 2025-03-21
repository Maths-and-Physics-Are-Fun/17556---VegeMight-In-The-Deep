package org.firstinspires.ftc.teamcode.opModes.teleOp;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goBackwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goBackwardInScoringStagesSpecimen;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStagesSpecimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustClawRotation;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustClawRotationBack;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristDown;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristUp;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Park;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.ToggleClaw;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

@TeleOp
@Disabled
public abstract class BaseTeleOpFTCLib extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;
    boolean specimen = false;
    boolean flagStatus = true;
    String fancytitle = " ";
    String fancytitle2 = "VegeMight ©";

    @Override
    public void initialize() {
        // RESET THE COMMAND SCHEDULER
        CommandScheduler.getInstance().reset();

        // Initalize hardware
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);


        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2,0, 0, 0);

        // Bind appropriate commands to buttons
        // Go forward and backward in intake and deposit statuses
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> this.specimen=!specimen));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new ConditionalCommand(new goBackwardInScoringStages(), new goBackwardInScoringStagesSpecimen(), () -> !specimen));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new ConditionalCommand(new goForwardInScoringStages(), new goForwardInScoringStagesSpecimen(), ()-> !specimen));

        gamepadEx2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(()-> this.flagStatus=!flagStatus));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ConditionalCommand(new InstantCommand(() -> HardwareReference.getInstance().flag.FlagUp()), new InstantCommand(()-> HardwareReference.getInstance().flag.FlagDown()), ()-> flagStatus));

        //Claw Rotation
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenPressed(new AdjustClawRotation());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new AdjustClawRotationBack());
        // Manual controls

        gamepadEx1.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ToggleClaw());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ToggleClaw());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.START).whenPressed(new Idle());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.START).whenPressed(new Idle());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whileHeld(new AdjustWristUp());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(new AdjustWristDown());

        // Park touching the bar
        gamepadEx2.getGamepadButton(GamepadKeys.Button.B).whenPressed(new Park());

    }

    @Override
    public void run() {
        // Run adjustments
        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> HardwareReference.getInstance().arm.adjustPosition((double) gamepad2.right_stick_y*0.01)
        ));
        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> HardwareReference.getInstance().lift.adjustPosition((int) gamepad2.left_stick_y*15)
        ));
        // Left trigger to slow down, right trigger to speed up, no trigger keeps default speed
        // Note: any position other than Idle defaults to slower values
        CommandScheduler.getInstance().schedule(new ConditionalCommand(
                //IF IDLE
                new ConditionalCommand(
                        new InstantCommand(() -> hardware.velocityAdjuster = 0.7), //SLOW DOWN ON RIGHT TRIGGER
                        new InstantCommand(() -> hardware.velocityAdjuster = 1),
                        () -> gamepadEx1.gamepad.right_trigger > 0.5
                ),
                new ConditionalCommand(
                        //IF DEPOSIT
                        new ConditionalCommand(
                                new InstantCommand(() -> hardware.velocityAdjuster = 0.8), //SPEED UP ON RIGHT TRIGGER
                                new InstantCommand(() -> hardware.velocityAdjuster = 0.7),
                                () -> gamepadEx1.gamepad.right_trigger > 0.5
                        ),
                        //ELSE
                        new ConditionalCommand(
                                new InstantCommand(() -> hardware.velocityAdjuster = 0.5),//SLOW DOWN ON RIGHT TRIGGER
                                new InstantCommand(() -> hardware.velocityAdjuster = 0.7),
                                () -> gamepadEx1.gamepad.right_trigger > 0.5
                        ),
                        () -> hardware.currentStatus == ScoreSystem.DEPOSIT
                ),
                () -> hardware.currentStatus == ScoreSystem.IDLE
        ));

        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> hardware.lift.updateDistance(hardware.leftSpool.getCurrentPosition())
        ));

        CommandScheduler.getInstance().schedule(new InstantCommand(() -> hardware.arm.manageSpeed()));

        // Robot-centric driving
        hardware.drivetrain.driveRobotCentric(gamepad1.left_stick_x*hardware.velocityAdjuster, -gamepad1.left_stick_y*hardware.velocityAdjuster, gamepad1.right_stick_x*hardware.velocityAdjuster, true);

        // Run the singleton command scheduler
        CommandScheduler.getInstance().run();

        //States Telemetry
        telemetry.addData("States", fancytitle);
        telemetry.addData("Current Status", hardware.currentStatus);
        telemetry.addData("Specimen",specimen);
        telemetry.addData("ClawRotationState", (hardware.clawrotServo.getPosition())/0.25);
        telemetry.addLine(fancytitle);
        //Positions Telemetry
        telemetry.addData("Current Positions", fancytitle);
        telemetry.addData("Arm Position", hardware.leftArm.getPosition());
        telemetry.addData("Wrist Position", hardware.wristServo.getPosition());
        telemetry.addData("Lift Position", hardware.leftSpool.getCurrentPosition());
        telemetry.addData("Claw Rotation Position", hardware.clawrotServo.getPosition());
        telemetry.addLine(fancytitle);
        //Other Telemetry
        telemetry.addData("Others", fancytitle);
        telemetry.addData("LiftVelo", hardware.leftSpool.getVelocity());
        telemetry.addData("LiftTargetPosition", hardware.lift.targetLiftPosition);
        telemetry.addData("LeftSpoolCurrent", hardware.leftSpool.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("RightSpoolCurrent", hardware.rightSpool.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("DOYOUWORK?", hardware.lift.DOYOUWORK);

        //Trademark
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle);
        telemetry.addLine(fancytitle2);

        telemetry.update();
    }

    public void

    public abstract void initalize() throws InterruptedException;
}
