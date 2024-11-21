package org.firstinspires.ftc.teamcode.common.commands.lowLevel;


import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;
public class HoverSpecimen extends CommandBase {
    HardwareReference hardware;

    public HoverSpecimen() {
        hardware = HardwareReference.getInstance();
    }

    @Override
    public void initialize() { //Make specimen deposit
        hardware.claw.clawClose();
        hardware.wrist.wristPickUp();
        hardware.arm.armDeposit();
        hardware.lift.liftSpecimen();
        hardware.currentStatus = ScoreSystem.HOVERSPECIMEN;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}

