package org.firstinspires.ftc.teamcode.common.constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {
    public static double WRIST_IDLE = 0.45;
    public static double WRIST_PICKUP = 0.55;
    public static double WRIST_DEPOSIT = 0.85;
    public static double WRIST_PARK = 0.975;
    public static double WRIST_SPECIMEN_DEPOSIT = 0.56;
    public static double WRIST_SPECIMEN_PICKUP = 0.87;

    public static double CLAW_OPEN = 0.6;
    public static double CLAW_CLOSED = 1;

    public static double ARM_IDLE = 0.028;
    public static double ARM_HOVER = 0.55;
    public static double ARM_PICKUP = 0.6461;
    public static double ARM_DEPOSIT = 0.265;
    public static double ARM_PARK = 0.31;
    public static double ARM_SPECIMEN_PICKUP = 0.5209;

    public static int LIFT_LOW = 0;
    public static int LIFT_HIGH = 2250;
    public static int LIFT_SPECIMEN_DEPOSIT = 100;
    public static int LIFT_SPECIMEN_HOVER = 1150;
    public static int LIFT_SPECIMEN_PICKUP = 600;
    public static int LIFT_SPECIMEN_HOVER_TELEOP = 1200;
}