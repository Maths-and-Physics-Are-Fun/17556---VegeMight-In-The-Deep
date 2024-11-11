package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goBackwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustArm;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustLift;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristDown;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristUp;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.ToggleClaw;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

@TeleOp
@Disabled
public abstract class BaseTeleOpFTCLib extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    GamepadEx gamepadEx1;
    GamepadEx gamePadEx2;

    @Override
    public void initialize() {
        // Initalize hardware
        gamepadEx1 = new GamepadEx(gamepad1);
        gamePadEx2 = new GamepadEx(gamepad2);

        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);

        // Bind appropriate commands to buttons
        gamePadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new goBackwardInScoringStages());
        gamePadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new goForwardInScoringStages());

        gamePadEx2.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ToggleClaw());
        gamePadEx2.getGamepadButton(GamepadKeys.Button.START).whenPressed(new Idle());
        gamePadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whileHeld(new AdjustWristUp());
        gamePadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(new AdjustWristDown());

    }

    @Override
    public void run() {
        // Robot-centric driving
        hardware.drivetrain.driveRobotCentric(gamepad1.right_stick_x*1.1, -gamepad1.right_stick_y, gamepad1.left_stick_x, true);

        // Run adjustments
        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> HardwareReference.getInstance().arm.adjustPosition((double) gamepad2.right_stick_y*0.01)
        ));
        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> HardwareReference.getInstance().lift.adjustPosition((int) gamepad2.left_stick_y*15)
        ));

        // Run the singleton command scheduler
        CommandScheduler.getInstance().run();

        // Telemetry
        telemetry.addData("ArmPos", hardware.leftArm.getPosition());
        telemetry.addData("WristPos", hardware.wristServo.getPosition());
        telemetry.addData("LiftPos", hardware.leftSpool.getCurrentPosition());
        telemetry.update();
    }

    public abstract void initalize() throws InterruptedException;
}
