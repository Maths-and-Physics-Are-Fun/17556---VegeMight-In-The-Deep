package org.firstinspires.ftc.teamcode.common;

import androidx.annotation.GuardedBy;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subSystems.Arm;
import org.firstinspires.ftc.teamcode.common.subSystems.Claw;
import org.firstinspires.ftc.teamcode.common.subSystems.Lift;
import org.firstinspires.ftc.teamcode.common.subSystems.Wrist;

@Config
public class HardwareReference {
    private static HardwareReference instance = null;
    private HardwareMap hardwareMap;

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

    public void initHardware(final HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

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

        drivetrain = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);

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
        wristServo = hardwareMap.get(ServoEx.class, "clawRot");
        clawServo = hardwareMap.get(ServoEx.class, "clawGrip");

        //Arm Servos
        leftArm = hardwareMap.get(ServoEx.class, "leftarm");
        leftArm = hardwareMap.get(ServoEx.class, "rightarm");
        rightArm.setInverted(true);

        // Subsystems
        claw = new Claw();
        wrist = new Wrist();
        arm = new Arm();
        lift = new Lift();

    }

}
