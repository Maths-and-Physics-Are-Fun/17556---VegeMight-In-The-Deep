package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Hover;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

public class goBackwardInScoringStages extends ConditionalCommand {

    public goBackwardInScoringStages() {
        super(
                new Hover(),
                new Idle(),
                () -> (HardwareReference.getInstance().currentStatus == ScoreSystem.IDLE || HardwareReference.getInstance().currentStatus == ScoreSystem.GRAB)
        );
    }

}