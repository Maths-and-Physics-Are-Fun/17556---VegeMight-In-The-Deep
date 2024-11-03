package org.firstinspires.ftc.teamcode.TeleOps;

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
    double targetArmPosition = 0;
    boolean armPositionAlreadSet = false;

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
        Servo clawServo = hardwareMap.servo.get("clawGrip");

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
                    // Set GamePad Colour to white
                    gamepad1.setLedColor(255, 255, 255, 100);
                    gamepad2.setLedColor(255, 255, 255, 100);

                    wristServo.setPosition(Globals.WRIST_UP);
                    clawServo.setPosition(Globals.CLAW_CLOSED);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_LOW;
                        liftPositionAlreadySet = true;
                    }
                    if (!armPositionAlreadSet) {
                        targetArmPosition = Globals.ARM_IDLE;
                        armPositionAlreadSet = true;
                    }
                    if (gamepad2.right_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.DEPOSIT_SPECIMEN;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    } else if (gamepad2.left_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.CLAW_HOVER;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    }
                    break;
                case CLAW_HOVER:
                    // Set GamePad Colour to red if Red Aliance and light blue if Blue Alliance
                    if (alliance == Alliance.RED) {
                        gamepad1.setLedColor(217, 14, 14, 100);
                        gamepad2.setLedColor(217, 14, 14, 100);
                    } else {
                        gamepad1.setLedColor(107, 233, 255, 100);
                        gamepad2.setLedColor(107, 233, 255, 100);
                    }

                    wristServo.setPosition(Globals.WRIST_DOWN);
                    clawServo.setPosition(Globals.CLAW_OPEN);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_LOW;
                        liftPositionAlreadySet = true;
                    }
                    if (!armPositionAlreadSet) {
                        targetArmPosition = Globals.ARM_HOVER;
                        armPositionAlreadSet = true;
                    }
                    if (gamepad2.right_bumper && clickTimer.milliseconds() > 500){
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.GRAB_SPECIMEN;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    } else if (gamepad2.left_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    }
                    break;
                case GRAB_SPECIMEN:
                    // Set GamePad Colour to orange if Red Alliance and dark blue if Blue Alliance
                    if (alliance == Alliance.RED) {
                        gamepad1.setLedColor(255, 137, 69, 100);
                        gamepad2.setLedColor(255, 137, 69, 100);
                    } else {
                        gamepad1.setLedColor(43, 131, 255, 100);
                        gamepad2.setLedColor(43, 131, 255, 100);
                    }

                    clawServo.setPosition(Globals.CLAW_CLOSED);
                    if (!armPositionAlreadSet) {
                        targetArmPosition = Globals.ARM_PICKUP;
                        armPositionAlreadSet = true;
                    }
                    if (gamepad2.right_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                        gamepad1.rumble(500);
                        gamepad2.rumble(500);
                    } else if (gamepad2.left_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.CLAW_HOVER;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    }
                    break;
                case DEPOSIT_SPECIMEN:
                    // Set GamePad Colour to yellow if Red Alliance and light purple if Blue Alliance
                    if (alliance == Alliance.RED) {
                        gamepad1.setLedColor(255, 229, 31, 100);
                        gamepad2.setLedColor(255, 229, 31, 100);
                    } else {
                        gamepad1.setLedColor(109, 31, 255, 100);
                        gamepad2.setLedColor(109, 31, 255, 100);
                    }

                    wristServo.setPosition(Globals.WRIST_DOWN);
                    if (!liftPositionAlreadySet) {
                        targetLiftPosition = Globals.LIFT_HIGH;
                        liftPositionAlreadySet = true;
                    }
                    if (!armPositionAlreadSet) {
                        targetArmPosition = Globals.ARM_DEPOSIT;
                        armPositionAlreadSet = true;
                    }
                    if (gamepad2.right_bumper && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        clawServo.setPosition(Globals.CLAW_OPEN);
                        while (gamepad1.circle) {
                            scoreSystemStatus = ScoreSystem.IDLE;
                        }
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    }
                    if (gamepad2.left_bumper && scoreSystemStatus != ScoreSystem.IDLE && clickTimer.milliseconds() > 500) {
                        clickTimer.reset();
                        scoreSystemStatus = ScoreSystem.IDLE;
                        liftPositionAlreadySet = false;
                        armPositionAlreadSet = false;
                    }
                    break;
            }

            // MANUAL LIFT CONTROL
            targetLiftPosition += (int) (gamepad2.left_stick_y*10);
            leftSpool.setTargetPosition(targetLiftPosition);
            rightSpool.setTargetPosition(targetLiftPosition);
            leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSpool.setVelocity(1000); // in ticks per second
            rightSpool.setVelocity(1000);

            // MANUAL ARM CONTROL
            targetArmPosition += (int) (gamepad2.right_stick_y*0.01);
            leftArm.setPosition(Globals.ARM_IDLE);
            rightArm.setPosition(Globals.ARM_IDLE);

            // EMERGENCY STOP BACK TO IDLE POSITION
            if (gamepad2.options) {
                scoreSystemStatus = ScoreSystem.IDLE;
                liftPositionAlreadySet = false;
                armPositionAlreadSet = false;
            }

            telemetry.addData("Scoring System Data", scoreSystemStatus);
            telemetry.update();
        }
    }
}