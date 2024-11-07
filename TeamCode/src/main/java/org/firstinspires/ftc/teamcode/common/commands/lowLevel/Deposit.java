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
        hardware.wrist.wristPickUp();
        hardware.arm.armDeposit();
        hardware.lift.liftHigh();
        hardware.currentStatus = ScoreSystem.DEPOSIT;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}