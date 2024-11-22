package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goBackwardInScoringStages extends SelectCommand {

    public goBackwardInScoringStages() {
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new Hover());
                    put(ScoreSystem.HOVER, new Idle());
                    put(ScoreSystem.GRAB, new Hover());
                    put(ScoreSystem.HOVER_AFTER_GRAB, new Grab());
                    put(ScoreSystem.DEPOSIT, new Idle());
                }},
            () -> HardwareReference.getInstance().currentStatus
        );
    }

}