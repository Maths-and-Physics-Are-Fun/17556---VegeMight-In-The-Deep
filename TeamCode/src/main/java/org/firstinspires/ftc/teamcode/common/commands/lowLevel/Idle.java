package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class Idle extends CommandBase {

    HardwareReference hardware;

    public Idle() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        hardware.claw.clawClose();


        hardware.wrist.wristIdle();
        hardware.arm.armIdle();
        hardware.lift.liftLow();
        hardware.claw.clawRotSetPosition(0);
        hardware.currentStatus = ScoreSystem.IDLE;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}