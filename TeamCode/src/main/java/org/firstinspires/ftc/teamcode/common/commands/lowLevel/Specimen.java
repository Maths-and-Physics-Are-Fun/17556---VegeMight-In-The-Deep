package org.firstinspires.ftc.teamcode.common.commands.lowLevel;


import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;
public class Specimen extends CommandBase {
    HardwareReference hardware;

    public Specimen() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() { //Make specimen deposit
        hardware.claw.clawClose();
        hardware.wrist.wristDeposit();
        hardware.arm.armDeposit();
        hardware.lift.liftMedium();
        hardware.currentStatus = ScoreSystem.DEPOSIT_SPECIMEN;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
