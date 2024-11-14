package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goBackwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristDown;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.AdjustWristUp;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.ToggleClaw;

@TeleOp
@Disabled
public abstract class BaseTeleOpFTCLib extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;

    @Override
    public void initialize() {
        // Initalize hardware
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);

        // Bind appropriate commands to buttons
        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new goBackwardInScoringStages());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new goForwardInScoringStages());

        gamepadEx1.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ToggleClaw());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ToggleClaw());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.START).whenPressed(new Idle());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.START).whenPressed(new Idle());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whileHeld(new AdjustWristUp());
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(new AdjustWristDown());

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
        CommandScheduler.getInstance().schedule(new ConditionalCommand(
                new InstantCommand(() -> hardware.velocityAdjuster = 0.5),
                new InstantCommand(() -> hardware.velocityAdjuster = 0.85),
                () -> gamepadEx1.gamepad.right_trigger > 0.5 || gamepadEx1.gamepad.left_trigger > 0.5
        ));
        CommandScheduler.getInstance().schedule(new InstantCommand(
                () -> hardware.lift.updateDistance(hardware.leftSpool.getCurrentPosition())
        ));

        CommandScheduler.getInstance().schedule(new InstantCommand(() -> hardware.arm.manageSpeed()));

        // Robot-centric driving
        hardware.drivetrain.driveRobotCentric(gamepad1.left_stick_x*hardware.velocityAdjuster, -gamepad1.left_stick_y*hardware.velocityAdjuster, gamepad1.right_stick_x*hardware.velocityAdjuster, true);

        // Run the singleton command scheduler
        CommandScheduler.getInstance().run();

        // Telemetry
        telemetry.addData("Current Status", hardware.currentStatus);
        telemetry.addData("ArmPos", hardware.leftArm.getPosition());
        telemetry.addData("WristPos", hardware.wristServo.getPosition());
        telemetry.addData("LiftPos", hardware.leftSpool.getCurrentPosition());
        telemetry.addData("LiftVelo", hardware.leftSpool.getVelocity());
        telemetry.addData("IterationsPassed", hardware.arm.getIteration());
        telemetry.update();
    }

    public abstract void initalize() throws InterruptedException;
}
