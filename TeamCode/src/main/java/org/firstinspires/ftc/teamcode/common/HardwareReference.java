package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.common.statuses.ClawStatus;
import org.firstinspires.ftc.teamcode.common.statuses.ScoreSystem;
import org.firstinspires.ftc.teamcode.common.subsystems.Arm;
import org.firstinspires.ftc.teamcode.common.subsystems.Claw;
import org.firstinspires.ftc.teamcode.common.subsystems.Lift;
import org.firstinspires.ftc.teamcode.common.subsystems.Wrist;

@Config
public class HardwareReference {
    private static HardwareReference instance = null;
    private HardwareMap hardwareMap;

    public ScoreSystem currentStatus = ScoreSystem.IDLE;
    public ClawStatus clawStatus;

    public Gamepad gamepad1;
    public Gamepad gamepad2;

    public IMU imu;

    public MotorEx frontLeftMotor;
    public MotorEx backLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backRightMotor;
    public MecanumDrive drivetrain;

    public MotorEx leftSpool;
    public MotorEx rightSpool;


    public ServoEx wristServo;
    public ServoEx clawServo;

    public ServoEx leftArm;
    public ServoEx rightArm;

    public Claw claw;
    public Wrist wrist;
    public Arm arm;
    public Lift lift;


    public static HardwareReference getInstance() {
        if (instance == null) instance = new HardwareReference();
        return instance;
    }

    public void initHardware(final HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        // Retrieve the drivetrain motors and configure them
        frontLeftMotor = new MotorEx(hardwareMap, "frontL", MotorEx.GoBILDA.RPM_312);
        backLeftMotor = new MotorEx(hardwareMap, "backL", MotorEx.GoBILDA.RPM_312);
        frontRightMotor = new MotorEx(hardwareMap, "frontR", MotorEx.GoBILDA.RPM_312);
        backRightMotor = new MotorEx(hardwareMap, "backR", MotorEx.GoBILDA.RPM_312);
        frontLeftMotor.setInverted(true);
        backLeftMotor.setInverted(true);

        drivetrain = new MecanumDrive(false, frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);

        // Retrieve the spool motors and configure them
        leftSpool = new MotorEx(hardwareMap, "LeftSpool", MotorEx.GoBILDA.RPM_312);
        rightSpool = new MotorEx(hardwareMap, "RightSpool", MotorEx.GoBILDA.RPM_312);
        leftSpool.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        rightSpool.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        rightSpool.setInverted(true);

        // Reset spool motor encoders
        leftSpool.stopAndResetEncoder();
        rightSpool.stopAndResetEncoder();

        //Claw Servos
        wristServo = new SimpleServo(hardwareMap, "clawRot", 0, 270);
        clawServo = new SimpleServo(hardwareMap, "clawGrip", 0, 270);

        //Arm Servos
        leftArm = new SimpleServo(hardwareMap, "leftarm", 0, 270);
        rightArm = new SimpleServo(hardwareMap, "rightarm", 0, 270);
        rightArm.setInverted(true);

        // Subsystems
        claw = new Claw();
        wrist = new Wrist();
        arm = new Arm();
        lift = new Lift();

    }

}
