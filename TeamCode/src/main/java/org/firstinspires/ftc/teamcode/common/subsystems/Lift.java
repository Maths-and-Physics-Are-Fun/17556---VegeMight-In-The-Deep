package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Lift extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    private int targetLiftPosition;
    private int adjustment;
    private boolean positionAlreadySet;
    int distance;
    int velocity;

    public Lift() {
        liftLow();
        positionAlreadySet = false;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    @Override
    public void periodic() {
        // Check whether height needs to be adjusted
        if (this.adjustment > 8) {
            targetLiftPosition += this.adjustment;
            this.adjustment = 0;
            positionAlreadySet = false;
        }
        if (!positionAlreadySet && (((hardware.leftSpool.motorEx.getCurrent(CurrentUnit.AMPS)) < 1) || (hardware.rightSpool.motorEx.getCurrent(CurrentUnit.AMPS)) < 1)) {
            if (targetLiftPosition < -50) targetLiftPosition = -50;
            else if (targetLiftPosition > 2100) targetLiftPosition = 2100;
            hardware.leftSpool.setTargetPosition(targetLiftPosition);
            hardware.rightSpool.setTargetPosition(targetLiftPosition);
            hardware.leftSpool.setRunMode(MotorEx.RunMode.PositionControl);
            hardware.rightSpool.setRunMode(MotorEx.RunMode.PositionControl);
            velocity = (distance^2)/200 + 50;
            hardware.leftSpool.setVelocity(Math.min(velocity, 20)); // in ticks per second
            hardware.rightSpool.setVelocity(Math.min(velocity, 20));
            positionAlreadySet = true;
        } else {
            if ((targetLiftPosition - 25 < hardware.leftSpool.getCurrentPosition() && hardware.leftSpool.getCurrentPosition() < targetLiftPosition + 25)
                && (targetLiftPosition < 1800)) {
                hardware.leftSpool.set(0);
                hardware.rightSpool.set(0);
            } else {positionAlreadySet = false;}
        }
    }

    private void setPosition(int position) {
        targetLiftPosition = position;
        positionAlreadySet = false;
    }

    public void adjustPosition(int adjustment) {
        this.adjustment += adjustment;
        positionAlreadySet = false;
    }

    public void updateDistance(int currentPosition) {
        distance = Math.abs(targetLiftPosition - currentPosition);
    }

    public void liftLow() {
        hardware.leftSpool.stopMotor();
        setPosition(Globals.LIFT_LOW);
    }

    public void liftHigh() {
        setPosition(Globals.LIFT_HIGH);
    }
    public void liftMedium() {
        setPosition(Globals.LIFT_MEDIUM);
    }
}
