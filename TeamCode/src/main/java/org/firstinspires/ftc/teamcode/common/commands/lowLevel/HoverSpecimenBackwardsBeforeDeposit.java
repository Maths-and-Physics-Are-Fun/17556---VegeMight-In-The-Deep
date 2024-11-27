package org.firstinspires.ftc.teamcode.common.commands.lowLevel;


import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class HoverSpecimenBackwardsBeforeDeposit extends CommandBase {
    HardwareReference hardware;

    public HoverSpecimenBackwardsBeforeDeposit() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() { //Make specimen deposit
        hardware.claw.clawClose();
        hardware.wrist.wristSpecimenDeposit();
        hardware.arm.armDeposit();
        hardware.lift.liftSpecimen();
        hardware.currentStatus = ScoreSystem.HOVER_SPECIMEN_BEFORE_DEPOSIT;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}

