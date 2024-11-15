package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class DriveToConverter {
    public DriveToConverter() {}
    public Action convertTrajectoryToAction(double xDest, double yDest, double heading) {
        TrajectoryActionBuilder trajActionBuilder = HardwareReference.getInstance().autoDrive.actionBuilder(HardwareReference.getInstance().autoDrive.pose);
        trajActionBuilder = trajActionBuilder.splineTo(new Vector2d(xDest, yDest), heading);
        Action driveAction = trajActionBuilder.build();
        return driveAction;
    }
}
