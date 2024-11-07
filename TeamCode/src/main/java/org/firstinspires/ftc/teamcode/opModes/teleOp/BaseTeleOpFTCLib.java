package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goBackwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.highLevel.goForwardInScoringStages;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
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

    }

    @Override
    public void run() {
        // Robot-centric driving
        hardware.drivetrain.driveRobotCentric(gamepad1.left_stick_x*1.1, gamepad1.left_stick_y, gamepad1.right_stick_x, true);

        // Run adjustments
        new adjustArm();
        new adjustWrist();

        // Run the singleton command scheduler
        CommandScheduler.getInstance().run();
    }

    public abstract void initalize() throws InterruptedException;
}
