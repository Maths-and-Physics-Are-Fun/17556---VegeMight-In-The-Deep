package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Deposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverAfterGrab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Rumble;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goForwardInScoringStages extends SelectCommand {

    public goForwardInScoringStages() {
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new Deposit());
                    put(ScoreSystem.HOVER, new Grab().alongWith(new Rumble()));
                    put(ScoreSystem.GRAB, new HoverAfterGrab().alongWith(new Rumble()));
                    put(ScoreSystem.HOVER_AFTER_GRAB, new Idle());
                    Wait wait;
                    put(ScoreSystem.DEPOSIT, new SequentialCommandGroup(
                            new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                            wait = new Wait(600),
                            new WaitUntilCommand(wait::isFinished),
                            new Idle()
                    ));
                }},
                () -> HardwareReference.getInstance().currentStatus
        );
    }

}