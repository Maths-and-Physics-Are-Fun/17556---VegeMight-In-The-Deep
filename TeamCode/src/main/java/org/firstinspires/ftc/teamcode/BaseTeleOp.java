package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.Globals;

import org.firstinspires.ftc.teamcode.statuses.Alliance;
import org.firstinspires.ftc.teamcode.statuses.ScoreSystem;


public class BaseTeleOp extends LinearOpMode {
    ScoreSystem scoreSystemStatus = ScoreSystem.IDLE;
    ElapsedTime clickTimer = new ElapsedTime();
    int targetLiftPosition = Globals.LIFT_LOW;
    boolean liftPositionAlreadySet = false;

    @Override
    public void runOpMode() throws InterruptedException {
        // Checks the name of the child class that is calling this method
        // If it contains "Blue" then the alliance is BLUE, if not it is RED
        Alliance alliance = this.getClass().getSimpleName().contains("Blue") ? Alliance.BLUE : Alliance.RED;

        // Drivetrain motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontL");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backL");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontR");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backR");

        //Claw Servos
        Servo wristServo = hardwareMap.servo.get("clawRot");
        Servo clawServo= hardwareMap.servo.get("clawGrip");

        //Arm Servos
        Servo leftArm = hardwareMap.servo.get("leftarm");
        Servo rightArm = hardwareMap.servo.get("rightarm");
        rightArm.setDirection(Servo.Direction.REVERSE);

        //Spool Motors
        DcMotorEx leftSpool = hardwareMap.get(DcMotorEx.class, "LeftSpool");
        DcMotorEx rightSpool = hardwareMap.get(DcMotorEx.class, "RightSpool");
        rightSpool.setDirection(DcMotorEx.Direction.REVERSE);

        //Reset Encoders
        leftSpool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSpool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // DRIVETRAIN
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            switch (scoreSystemStatus) {
                case IDLE:
                    wristServo.setPosition(Globals.WRIST_UP);
                    clawServo.setPosition(Globals.CLAW_CLOSED);
                    leftArm.setPosition(Globals.ARM_IDLE);
                    rightArm.setPosition(Globals.ARM_IDLE);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_LOW;
                        liftPositionAlreadySet = true;
                    }
                    if (gamepad1.circle && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.DEPOSIT_SPECIMEN;
                        liftPositionAlreadySet = false;
                    } else if (gamepad1.cross && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.CLAW_HOVER;
                        liftPositionAlreadySet = false;
                    }
                    break;
                case CLAW_HOVER:
                    wristServo.setPosition(Globals.WRIST_DOWN);
                    clawServo.setPosition(Globals.CLAW_OPEN);
                    leftArm.setPosition(Globals.ARM_HOVER);
                    rightArm.setPosition(Globals.ARM_HOVER);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_LOW;
                        liftPositionAlreadySet = true;
                    }
                    if (gamepad1.circle && clickTimer.milliseconds() > 500){
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.GRAB_SPECIMEN;
                        liftPositionAlreadySet = false;
                    } else if (gamepad1.cross && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                    }
                    break;
                case GRAB_SPECIMEN:
                    clawServo.setPosition(Globals.CLAW_CLOSED);
                    leftArm.setPosition(Globals.ARM_PICKUP);
                    rightArm.setPosition(Globals.ARM_PICKUP);
                    if (gamepad1.circle && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                    } else if (gamepad1.cross && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.CLAW_HOVER;
                        liftPositionAlreadySet = false;
                    }
                    break;
//                case HOLD_SPECIMEN:
//                    if (gamepad1.circle && clickTimer.milliseconds() > 500) {
//                        clickTimer.reset();
//                        wristServo.setPosition(Globals.WRIST_DOWN);
//                        leftArm.setPosition(Globals.ARM_DEPOSIT);
//                        rightArm.setPosition(Globals.ARM_DEPOSIT);
//                        leftSpool.setTargetPosition(Globals.ARM_HIGH);
//                        rightSpool.setTargetPosition(Globals.ARM_HIGH);
//                        leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        leftSpool.setVelocity(1000); // in ticks per second
//                        rightSpool.setVelocity(1000);
//                        scoreSystemStatus = ScoreSystem.DEPOSIT_SPECIMEN;
//                    } else if (gamepad1.cross && clickTimer.milliseconds() > 500) {
//                        clickTimer.reset();
//                        scoreSystemStatus = ScoreSystem.IDLE;
//                    }
//                    break;
                case DEPOSIT_SPECIMEN:
                    wristServo.setPosition(Globals.WRIST_DOWN);
                    leftArm.setPosition(Globals.ARM_DEPOSIT);
                    rightArm.setPosition(Globals.ARM_DEPOSIT);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_HIGH;
                        liftPositionAlreadySet = true;
                    }
                    if (gamepad1.circle && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        clawServo.setPosition(Globals.CLAW_OPEN);
                        while (gamepad1.circle) {
                            scoreSystemStatus = ScoreSystem.IDLE;
                        }
                        liftPositionAlreadySet = false;
                    }
                    if (gamepad1.cross && scoreSystemStatus != ScoreSystem.IDLE && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                    }
                    break;
            }

            // MANUAL LIFT CONTROL
            if (gamepad1.left_trigger > 0.5) {
                targetLiftPosition -= (int) (gamepad1.left_trigger*10);
            } else if (gamepad1.right_trigger > 0.5) {
                targetLiftPosition += (int) (gamepad1.right_trigger*10);
            }
            leftSpool.setTargetPosition(targetLiftPosition);
            rightSpool.setTargetPosition(targetLiftPosition);
            leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSpool.setVelocity(1000); // in ticks per second
            rightSpool.setVelocity(1000);

            // EMERGENCY STOP?

            telemetry.addData("Scoring System Data", scoreSystemStatus);
            telemetry.update();
        }
    }
}