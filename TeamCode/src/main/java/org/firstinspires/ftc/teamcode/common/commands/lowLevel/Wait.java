package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.HardwareReference;

import java.util.concurrent.TimeUnit;

public class Wait extends CommandBase {

    Timing.Timer timer;

    public Wait(int time) {
        timer = new Timing.Timer(time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.done();
    }

}
