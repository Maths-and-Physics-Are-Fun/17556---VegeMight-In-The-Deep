package org.firstinspires.ftc.teamcode.opModes.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;

@Autonomous
@Disabled
public class BaseAuto extends CommandOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();

    @Override
    public void initialize() {
        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);

        // Go to scoring position

        // Score
        CommandScheduler.getInstance().schedule(new Deposit());
        //Move forward slightly
        CommandScheduler.getInstance().schedule(new );

    }
}
