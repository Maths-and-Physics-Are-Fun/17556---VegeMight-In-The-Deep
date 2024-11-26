package org.firstinspires.ftc.teamcode.common.commands.highLevel;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Grab;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDeposit;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.HoverSpecimenBeforeDepositTeleOp;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Idle;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.PickUpSpecimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Rumble;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Specimen;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;

import java.util.HashMap;

public class goForwardInScoringStagesSpecimen extends SelectCommand {
    public goForwardInScoringStagesSpecimen (){
        super(
                new HashMap<Object, Command>() {{
                    put(ScoreSystem.IDLE, new HoverSpecimenBeforeDepositTeleOp());
                    put(ScoreSystem.HOVER_SPECIMEN_BEFORE_GRAB, new PickUpSpecimen().alongWith(new Rumble()));
                    put(ScoreSystem.HOVER_SPECIMEN_BEFORE_DEPOSIT, new Specimen().alongWith(new Rumble()));
                    Wait wait;
                    put(ScoreSystem.DEPOSIT_SPECIMEN, new SequentialCommandGroup(
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
