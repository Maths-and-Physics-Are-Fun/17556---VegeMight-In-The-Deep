package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDeposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeGrab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.PickUpSpecimen;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goBackwardInScoringStagesSpecimen extends SelectCommand {

    public goBackwardInScoringStagesSpecimen() {
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new HoverSpecimenBeforeGrab());
                    put(ScoreSystem.HOVER_SPECIMEN_BEFORE_GRAB, new Idle());
                    put(ScoreSystem.HOVER_SPECIMEN_BEFORE_DEPOSIT, new Idle());
                    put(ScoreSystem.DEPOSIT_SPECIMEN, new HoverSpecimenBeforeDeposit());
                }},
            () -> HardwareReference.getInstance().currentStatus
        );
    }

}