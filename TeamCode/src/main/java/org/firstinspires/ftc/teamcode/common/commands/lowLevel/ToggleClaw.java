package org.firstinspires.ftc.teamcode.common.commands.lowLevel;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.statuses.ClawStatus;

public class ToggleClaw extends ConditionalCommand {
    public ToggleClaw() {
        super(
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawOpen()),
                new InstantCommand(() -> HardwareReference.getInstance().claw.clawClose()),
                () -> HardwareReference.getInstance().clawStatus == ClawStatus.CLOSED
        );
    }
}
