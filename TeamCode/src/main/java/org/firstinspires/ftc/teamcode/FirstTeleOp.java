package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.Globals;


@TeleOp (name = "TeleOp 1")
public class FirstTeleOp extends LinearOpMode {
    public enum InsertGoodName {
        GRAB_SPECIMEN,
        HOLD_SPECIMEN,
        DEPOSIT_SPECIMEN,
        CLAW_IDLE
    }
    InsertGoodName insertGoodName = InsertGoodName.CLAW_IDLE;

    @Override
    public void runOpMode() throws InterruptedException {
        // Drivetrain motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontL");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backL");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontR");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backR");

        //Claw Servos
        Servo clawServo = hardwareMap.servo.get("claw");

        //Arm Servos
        Servo leftArm = hardwareMap.servo.get("leftarm");
        Servo rightArm = hardwareMap.servo.get("rightarm");
        rightArm.setDirection(Servo.Direction.REVERSE);
/*

        //Spool Motors
        DcMotorEx leftSpool = hardwareMap.get(DcMotorEx.class, "LeftSpool");
        DcMotorEx rightSpool = hardwareMap.get(DcMotorEx.class, "RightSpool");

        //Reset Encoders
        leftSpool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSpool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
*/

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
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }
            //Set the position to set values - b=open, a=closed
            //Check Directions
            if (gamepad1.circle) {
                clawServo.setPosition(Globals.CLAW_OPEN);
            }
            if (gamepad1.cross) {
                clawServo.setPosition(Globals.CLAW_CLOSED);
            }
            //Move the servo by small amount - x=opening, y=closing
            //Check Directions

            //Move the arm
           if (gamepad1.square) {
               leftArm.setPosition(Globals.ARM_IDLE);
               rightArm.setPosition(Globals.ARM_IDLE);
           }
           if (gamepad1.triangle) {
               leftArm.setPosition(Globals.ARM_DEPOSIT);
               rightArm.setPosition(Globals.ARM_DEPOSIT);
           }

            //Move the spool
            /*if (gamepad2.cross) {
                leftSpool.setTargetPosition(Globals.ARM_LOW);
                rightSpool.setTargetPosition(Globals.ARM_LOW);
                leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSpool.setVelocity(1000); // in ticks per second
                rightSpool.setVelocity(1000);
            }
            if (gamepad2.circle){
               leftSpool.setTargetPosition(Globals.ARM_HIGH);
               rightSpool.setTargetPosition(Globals.ARM_HIGH);
               leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               leftSpool.setVelocity(1000); // in ticks per second
                rightSpool.setVelocity(1000);
            }*/

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

/*            switch (insertGoodName) {
                case CLAW_IDLE:
                    clawServo.setPosition(Globals.CLAW_OPEN);
                    leftArm.setPosition(Globals.ARM_IDLE);
                    rightArm.setPosition(Globals.ARM_IDLE);
                    leftSpool.setTargetPosition(Globals.ARM_LOW);
                    rightSpool.setTargetPosition(Globals.ARM_LOW);
                    leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftSpool.setVelocity(1000); // in ticks per second
                    rightSpool.setVelocity(1000);
                    if (gamepad1.circle){
                        insertGoodName = InsertGoodName.GRAB_SPECIMEN;
                    }
                break;
                case GRAB_SPECIMEN:
                    clawServo.setPosition(Globals.CLAW_CLOSED);
                    if (gamepad1.circle) {
                        insertGoodName = InsertGoodName.HOLD_SPECIMEN;
                    } else if (gamepad1.cross) {
                        insertGoodName = InsertGoodName.CLAW_IDLE;
                    }

                break;
                case HOLD_SPECIMEN:
                    if (gamepad1.circle) {
                    leftArm.setPosition(Globals.ARM_DEPOSIT);
                    rightArm.setPosition(Globals.ARM_DEPOSIT);
                    leftSpool.setTargetPosition(Globals.ARM_HIGH);
                    rightSpool.setTargetPosition(Globals.ARM_HIGH);
                    leftSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftSpool.setVelocity(1000); // in ticks per second
                    rightSpool.setVelocity(1000);
                    insertGoodName = InsertGoodName.DEPOSIT_SPECIMEN;
                    }
                break;
                case DEPOSIT_SPECIMEN:
                    if (gamepad1.circle) {
                    clawServo.setPosition(Globals.CLAW_OPEN);
                    insertGoodName = InsertGoodName.CLAW_IDLE;
                }
            if (gamepad1.cross && insertGoodName != InsertGoodName.CLAW_IDLE) {
                insertGoodName = InsertGoodName.CLAW_IDLE;
            }
            } */
        }
    }
}