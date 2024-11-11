package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Lift extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    private int targetLiftPosition;
    private int adjustment;
    private boolean positionAlreadySet;

    public Lift() {
        liftLow();
        positionAlreadySet = false;
    }

    @Override
    public void periodic() {
        // Check whether height needs to be adjusted
        if (this.adjustment > 8) {
            targetLiftPosition += this.adjustment;
            this.adjustment = 0;
            positionAlreadySet = false;
        }
        if (!positionAlreadySet) {
            if (targetLiftPosition < -50) targetLiftPosition = -50;
            else if (targetLiftPosition > 2000) targetLiftPosition = 2000;
            hardware.leftSpool.setTargetPosition(targetLiftPosition);
            hardware.rightSpool.setTargetPosition(targetLiftPosition);
            hardware.leftSpool.setRunMode(MotorEx.RunMode.PositionControl);
            hardware.rightSpool.setRunMode(MotorEx.RunMode.PositionControl);
            int distance = Math.abs(targetLiftPosition - hardware.leftSpool.getCurrentPosition());
            int velocity = (distance^2)/200 + 50;
            hardware.leftSpool.setVelocity(Math.min(velocity, 50)); // in ticks per second
            hardware.rightSpool.setVelocity(Math.min(velocity, 50));
            positionAlreadySet = true;
        } else {
            if (targetLiftPosition - 10 < hardware.leftSpool.getCurrentPosition() && hardware.leftSpool.getCurrentPosition() < targetLiftPosition + 10) {
                hardware.leftSpool.stopMotor();
                hardware.rightSpool.stopMotor();
            }
        }
    }

    private void setPosition(int position) {
        targetLiftPosition = position;
        positionAlreadySet = false;
    }

    public void adjustPosition(int adjustment) {
        this.adjustment += adjustment;
    }

    public void liftLow() {
        setPosition(Globals.LIFT_LOW);
    }

    public void liftHigh() {
        setPosition(Globals.LIFT_HIGH);
    }

}
