package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Lift extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    private int targetLiftPosition;
    private boolean positionAlreadySet;

    public Lift() {
        liftLow();
        positionAlreadySet = false;
    }

    @Override
    public void periodic() {
        if (!positionAlreadySet) {
            if (targetLiftPosition < -50) targetLiftPosition = -50;
            hardware.leftSpool.setTargetPosition(targetLiftPosition);
            hardware.rightSpool.setTargetPosition(targetLiftPosition);
            hardware.leftSpool.setRunMode(MotorEx.RunMode.PositionControl);
            hardware.rightSpool.setRunMode(MotorEx.RunMode.PositionControl);
            hardware.leftSpool.setVelocity(700); // in ticks per second
            hardware.rightSpool.setVelocity(700);
            positionAlreadySet = true;
        } else {
            hardware.leftSpool.stopMotor();
            hardware.rightSpool.stopMotor();
        }
    }

    private void setPosition(int position) {
        targetLiftPosition = position;
        positionAlreadySet = false;
    }

    public void adjustPosition(int adjustment) {
        targetLiftPosition += adjustment;
        positionAlreadySet = false;
    }

    public void liftLow() {
        setPosition(Globals.LIFT_LOW);
    }

    public void liftHigh() {
        setPosition(Globals.LIFT_HIGH);
    }

}
