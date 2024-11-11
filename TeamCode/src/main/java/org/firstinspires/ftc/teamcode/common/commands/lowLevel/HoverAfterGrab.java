package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class HoverAfterGrab extends CommandBase {

    HardwareReference hardware;

    public HoverAfterGrab() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        hardware.claw.clawClose();
        hardware.wrist.wristPickUp();
        hardware.arm.armHover();
        hardware.lift.liftLow();
        hardware.currentStatus = ScoreSystem.HOVERAFTERGRAB;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
