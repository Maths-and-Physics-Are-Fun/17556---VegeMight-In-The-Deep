package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.HashSet;
import java.util.Set;

public class RRCommand extends CommandBase {
    // Wrapper based off guide from RR Docs
    // https://rr.brott.dev/docs/v1-0/guides/ftclib-commands/

    private final HardwareReference hardware = HardwareReference.getInstance();
    private boolean isCommandFinished = false;
    private final Action action;
    private Set<Subsystem> requirements = new HashSet<>();

    public RRCommand(Action action) {
        this.action = action;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return requirements;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        TelemetryPacket packet = new TelemetryPacket();
        action.preview(packet.fieldOverlay());
        isCommandFinished = !action.run(new TelemetryPacket());
        dashboard.sendTelemetryPacket(packet);
    }

    @Override
    public boolean isFinished() {
        return isCommandFinished;
    }

}
