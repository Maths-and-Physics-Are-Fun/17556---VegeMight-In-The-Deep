package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goBackwardInScoringStagesSpecimen extends SelectCommand {

    public goBackwardInScoringStagesSpecimen() {
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new Hover());
                    put(ScoreSystem.HOVER, new Idle());
                    put(ScoreSystem.GRAB, new Hover());
                    put(ScoreSystem.HOVERSPECIMEN, new Idle());
                    put(ScoreSystem.DEPOSIT_SPECIMEN, new HoverSpecimen());
                }},
            () -> HardwareReference.getInstance().currentStatus
        );
    }

}