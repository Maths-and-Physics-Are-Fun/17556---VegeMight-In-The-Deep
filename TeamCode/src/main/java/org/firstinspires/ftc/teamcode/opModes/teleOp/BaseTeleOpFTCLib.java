package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

@TeleOp
@Disabled
public class BaseTeleOpFTCLib extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    @Override
    public void initialize() {
        // Initalize hardware

        // Schedule all commands that are run once
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        hardware.drivetrain.driveRobotCentric(gamepad1.left_stick_x*1.1, gamepad1.left_stick_y, gamepad1.right_stick_x, true);
    }

}
