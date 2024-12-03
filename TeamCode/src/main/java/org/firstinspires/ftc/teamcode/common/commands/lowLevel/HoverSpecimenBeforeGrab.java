package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class HoverSpecimenBeforeGrab extends CommandBase {
    HardwareReference hardware;

    public HoverSpecimenBeforeGrab() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() { //Make specimen deposit
        hardware.claw.clawOpen();
        hardware.wrist.wristSpecimenPickUp();
        hardware.arm.armSpecimenPickUp();
        hardware.lift.liftLow();
        hardware.claw.clawRotSetPosition(3);
        hardware.currentStatus = ScoreSystem.HOVER_SPECIMEN_BEFORE_GRAB;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
