package org.firstinspires.ftc.teamcode.opModes.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

@Autonomous
@Disabled
public class BaseAuto extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    @Override
    public void initialize() {
        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);
    }
}
