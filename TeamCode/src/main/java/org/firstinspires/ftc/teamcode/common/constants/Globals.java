package org.firstinspires.ftc.teamcode.common.constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {
    public static double WRIST_IDLE = 0.12; //0.36;
    public static double WRIST_PICKUP = 0.21;
    public static double WRIST_DEPOSIT = 0.51;
    public static double WRIST_PARK = 0.455;
    public static double WRIST_SPECIMEN_DEPOSIT = 0.9;
    public static double WRIST_SPECIMEN_DEPOSIT_TELEOP = 0.77;
    public static double WRIST_SPECIMEN_PICKUP = 0.5;

    public static double CLAW_OPEN = 0.8;
    public static double CLAW_CLOSED = 0.40; //1;

    public static double ARM_IDLE = 0; //0.028;
    public static double ARM_HOVER = 0.4942;
    public static double ARM_PICKUP = 0.5723;
    public static double ARM_DEPOSIT = 0.237;
    //public WHAT HERE CHARLIE_
    public static double ARM_PARK = 0.28;
    public static double ARM_SPECIMEN_PICKUP = 0.4929;
    public static double ARM_SPECIMEN_DEPOSIT= 0;

    public static int LIFT_LOW = 0;
    public static int LIFT_HIGH = 1700;
    public static int LIFT_SPECIMEN_DEPOSIT = 100;
    public static int LIFT_SPECIMEN_HOVER = 1250;
    public static int LIFT_SPECIMEN_PICKUP = 600;
    public static int LIFT_SPECIMEN_HOVER_TELEOP = 940;

    public static int FLAG_UP =1;
    public static double FLAG_DOWN= 0.33;
}