package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class Hover extends CommandBase {

    HardwareReference hardware;

    public Hover() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        hardware.claw.clawOpen();
        hardware.wrist.wristPickUp();
        hardware.arm.armHover();
        hardware.lift.liftLow();
        hardware.currentStatus = ScoreSystem.HOVER;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
