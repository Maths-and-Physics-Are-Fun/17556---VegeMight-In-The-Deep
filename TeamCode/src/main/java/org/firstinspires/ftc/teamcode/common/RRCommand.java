package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.CommandBase;

public class RRCommand extends CommandBase {
    // Wrapper based off guide from RR Docs
    // https://rr.brott.dev/docs/v1-0/guides/ftclib-commands/

    private final HardwareReference hardware = HardwareReference.getInstance();
    private boolean isCommandFinished = false;
    private Action action;

    public RRCommand() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return isCommandFinished;
    }

}
