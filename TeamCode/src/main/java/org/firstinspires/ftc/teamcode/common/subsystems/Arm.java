package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Arm extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();
    private double armTargetPosition = 0;
    private double armMiniTargetPosition = 0;
    private boolean targetPositionChanged = true;
    private int iterationsPassed;

    public Arm() {
        CommandScheduler.getInstance().registerSubsystem(this);
        armIdle();
    }

    @Override
    public void periodic() {
        hardware.leftArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        hardware.rightArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
    }

    public int getIteration() {
        return iterationsPassed;
    }

    public void manageSpeed() {
        if (targetPositionChanged) {
            armMiniTargetPosition = hardware.leftArm.getPosition();
            targetPositionChanged = false;
            iterationsPassed = 0;
        }
        if (iterationsPassed % 50 == 0) {
            armMiniTargetPosition += 0.1;
        }
        iterationsPassed += 1;
    }

    private void setPosition(double position) {
        armTargetPosition = position;
        targetPositionChanged = true;
    }

    public void adjustPosition(double adjustment) {
        armTargetPosition += adjustment;
        targetPositionChanged = true;
    }

    public void armIdle() {
        setPosition(Globals.ARM_IDLE);
    }

    public void armHover() {
        setPosition(Globals.ARM_HOVER);
    }

    public void armPickup() {
        setPosition(Globals.ARM_PICKUP);
    }

    public void armDeposit() {
        setPosition(Globals.ARM_DEPOSIT);
    }

}
