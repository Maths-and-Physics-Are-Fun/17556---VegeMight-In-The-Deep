package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Rumble;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goForwardInScoringStages extends SelectCommand {

    public goForwardInScoringStages() {
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new Deposit());
                    put(ScoreSystem.HOVER, new Grab().alongWith(new Rumble()));
                    put(ScoreSystem.GRAB, new Idle());
                    put(ScoreSystem.DEPOSIT, new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()).andThen(new Idle()));
                }},
                () -> HardwareReference.getInstance().currentStatus
        );
    }

}