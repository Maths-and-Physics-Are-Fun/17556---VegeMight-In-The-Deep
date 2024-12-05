
package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.constants.Globals;

public class Flag extends SubsystemBase {
    private double flagTargetPosition;
    private final HardwareReference hardware = HardwareReference.getInstance();

    public Flag() {
        FlagDown();
        HardwareReference.getInstance().FlagServo.disable();
    }

    @Override
        public void periodic(){
        //hardware.FlagServo.setPosition(flagTargetPosition);
        }

        private void setPosition(double position) {
        hardware.FlagServo.setPosition(position);
    }
        public void FlagDown(){
        setPosition(Globals.FLAG_DOWN);
        }

        public void FlagUp(){
        setPosition(Globals.FLAG_UP);
        }
}
