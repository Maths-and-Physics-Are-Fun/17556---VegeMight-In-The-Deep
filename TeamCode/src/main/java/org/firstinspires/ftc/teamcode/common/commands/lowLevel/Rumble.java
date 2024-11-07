package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

public class Rumble extends CommandBase {

    public Rumble() {
    }

    @Override
    public void initialize() {
        new InstantCommand(
                () -> HardwareReference.getInstance().gamepad1.rumble(700)
        ).alongWith(new InstantCommand(
                () -> HardwareReference.getInstance().gamepad2.rumble(700)
        ));
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
