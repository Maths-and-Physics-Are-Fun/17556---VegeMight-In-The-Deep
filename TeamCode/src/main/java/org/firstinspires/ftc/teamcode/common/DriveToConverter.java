package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class DriveToConverter {

    public enum MovementType {
        STRAFE_TO,
        LINE_TO_X,
        LINE_TO_Y,
        SPLINE_TO,
        SPLINE_TO_CONSTANT_HEADING,
        SPLINE_TO_LINEAR_HEADING,
        STRAFE_TO_HEADING,
        TURN
    }

    public DriveToConverter() {}
    public Action convertTrajectoryToAction(double xDest, double yDest, double heading, MovementType movementType) {
        TrajectoryActionBuilder trajActionBuilder = HardwareReference.getInstance().autoDrive.actionBuilder(HardwareReference.getInstance().currentPose);
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
            case SPLINE_TO_CONSTANT_HEADING:
                trajActionBuilder = trajActionBuilder.splineToConstantHeading(new Vector2d(xDest, yDest), heading);
                break;
            case SPLINE_TO_LINEAR_HEADING:
                trajActionBuilder = trajActionBuilder.splineToLinearHeading(new Pose2d(xDest, yDest, heading), Math.toRadians(90));
                break;
            case STRAFE_TO_HEADING:
                trajActionBuilder = trajActionBuilder.strafeToLinearHeading(new Vector2d(xDest, yDest), heading);
                break;
            case TURN:
                trajActionBuilder = trajActionBuilder.turnTo(heading);
                break;
        }
        Action driveAction = trajActionBuilder.build();
        HardwareReference.getInstance().currentPose = new Pose2d(xDest, yDest, heading);
        return driveAction;
    }

    public Action convertTrajectoryToAction(TrajectoryActionBuilder trajActionBuilder) {
        Action driveAction = trajActionBuilder.build();
        //HardwareReference.getInstance().currentPose = new Pose2d(driveAction., yDest, heading);
        return driveAction;
    }

}
