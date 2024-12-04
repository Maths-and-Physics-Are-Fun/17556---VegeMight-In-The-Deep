package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Lift extends SubsystemBase {
    private final HardwareReference hardware = HardwareReference.getInstance();

    public int targetLiftPosition;
    private int adjustment;
    private boolean positionAlreadySet;
    int distance;
    int velocity;
    public boolean DOYOUWORK = false;

    public Lift() {
        liftLow();
        positionAlreadySet = false;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    @Override
    public void periodic() {
        DOYOUWORK = false;
        // Check whether height needs to be adjusted
        if (this.adjustment > 8) {
            targetLiftPosition += this.adjustment;
            this.adjustment = 0;
            positionAlreadySet = false;
        }
        if (!positionAlreadySet && (((hardware.leftSpool.getCurrent(CurrentUnit.AMPS)) < 2) || (hardware.rightSpool.getCurrent(CurrentUnit.AMPS)) < 2)) {
            DOYOUWORK = true;
            if (targetLiftPosition < -100) targetLiftPosition = -100;
            else if (targetLiftPosition > 1800) targetLiftPosition = 1800;
            hardware.leftSpool.setTargetPosition(targetLiftPosition);
            hardware.rightSpool.setTargetPosition(targetLiftPosition);
            hardware.leftSpool.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            hardware.rightSpool.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            //velocity = (distance^2)/200 + 50;
            //hardware.leftSpool.setVelocity(Math.min(velocity, 20)); // in ticks per second
            //hardware.rightSpool.setVelocity(Math.min(velocity, 20));
            hardware.leftSpool.setPower(1);
            hardware.rightSpool.setPower(1);
            positionAlreadySet = true;
        } else {
            if ((targetLiftPosition - 40 < hardware.leftSpool.getCurrentPosition() && hardware.leftSpool.getCurrentPosition() < targetLiftPosition + 40)
                && (targetLiftPosition < 1800)) {
                hardware.leftSpool.setPower(0);
                hardware.rightSpool.setPower(0);
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
        hardware.leftSpool.setPower(0);
        hardware.rightSpool.setPower(0);
        setPosition(Globals.LIFT_LOW);
    }

    public void liftHigh() {
        setPosition(Globals.LIFT_HIGH);
    }
    public void liftMedium() {
        setPosition(Globals.LIFT_SPECIMEN_DEPOSIT);
    }
    public void liftSpecimen() {setPosition(Globals.LIFT_SPECIMEN_HOVER);}
    public void liftSpecimenTeleOP() {setPosition(Globals.LIFT_SPECIMEN_HOVER_TELEOP);}
    public void liftPickup() {setPosition(Globals.LIFT_SPECIMEN_PICKUP);}
}
