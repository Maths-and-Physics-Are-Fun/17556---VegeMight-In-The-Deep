package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class Grab extends CommandBase {

    HardwareReference hardware;

    public Grab() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        hardware.claw.clawClose();
        hardware.wrist.wristPickUp();
        hardware.arm.armPickup();
        hardware.lift.liftLow();
        hardware.currentStatus = ScoreSystem.GRAB;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}