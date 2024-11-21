package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class DriveToConverter {

    public enum MovementType {
        STRAFE_TO,
        LINE_TO_X,
        LINE_TO_Y,
        SPLINE_TO,
        LINE_TO
    }

    public DriveToConverter() {}
    public Action convertTrajectoryToAction(double xDest, double yDest, double heading, MovementType movementType) {
        TrajectoryActionBuilder trajActionBuilder = HardwareReference.getInstance().autoDrive.actionBuilder(HardwareReference.getInstance().autoDrive.pose);
        switch (movementType) {
            case STRAFE_TO:
                trajActionBuilder = trajActionBuilder.strafeTo(new Vector2d(xDest, yDest));
                break;
            case LINE_TO_X:
                trajActionBuilder = trajActionBuilder.lineToX(xDest);
                break;
            case LINE_TO_Y:
                trajActionBuilder = trajActionBuilder.lineToY(yDest);
                break;
            case SPLINE_TO:
                trajActionBuilder = trajActionBuilder.splineTo(new Vector2d(xDest, yDest), heading);
                break;
            case LINE_TO:
                trajActionBuilder = trajActionBuilder.splineToConstantHeading(new Vector2d(xDest, yDest), heading);
                break;
        }
        Action driveAction = trajActionBuilder.build();
        return driveAction;
    }
}
