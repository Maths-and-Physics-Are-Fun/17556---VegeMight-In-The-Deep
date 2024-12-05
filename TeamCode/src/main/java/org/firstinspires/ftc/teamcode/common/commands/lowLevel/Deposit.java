package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class Deposit extends CommandBase {

    HardwareReference hardware;

    public Deposit() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() {
        hardware.claw.clawClose();
        hardware.wrist.wristDeposit();
        hardware.arm.armDeposit();
        hardware.lift.liftHigh();
        hardware.claw.clawRotSetPosition(0);
        hardware.currentStatus = ScoreSystem.DEPOSIT;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}