package org.firstinspires.ftc.teamcode.opModes.auto;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.common.HardwareReference;
import org.firstinspires.ftc.teamcode.common.commands.lowLevel.Wait;

@Autonomous
@Disabled
public class VegeMightTelemetry_DONOTUSE extends LinearOpMode {

    private final HardwareReference hardware = HardwareReference.getInstance();
    String fancytitle = " ";
    int letter = 0;
    public void initialize() {
        // Reset the command scheduler singleton
        CommandScheduler.getInstance().reset();
        // Initialize the singleton hardware reference
        hardware.initHardware(hardwareMap, gamepad1, gamepad2);
    }

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        // SCORE
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new InstantCommand(() -> letter=0),
                new Wait(1000),
                new InstantCommand(() -> letter=1),
                new Wait(1000),
                new InstantCommand(()->letter=2),
                new Wait(1000),
                new InstantCommand(() -> letter=3),
                new Wait(1000),
                new InstantCommand(() -> letter=4),
                new Wait(1000),
                new InstantCommand(()->letter=5),
                new Wait(1000),
                new InstantCommand(() -> letter=6),
                new Wait(1000),
                new InstantCommand(() -> letter=7),
                new Wait(1000),
                new InstantCommand(()->letter=8),
                new Wait(1000),
                new InstantCommand(()->letter=9),
                new Wait(1000)
        ));

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            vegeMightAnimation();
            telemetry.update();
        }
        // Make the hardware instance null after one run-through of the auto to stop it from looping
        hardware.nullify();
        this.requestOpModeStop();
    }

    private void vegeMightAnimation() {
        //Cool VegeMight V :D
        if (letter==0) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      :::::::::::::::::::::      :", fancytitle);
            telemetry.addData("::      :::::::::::::::::::      ::", fancytitle);
            telemetry.addData(":::      :::::::::::::::::      :::", fancytitle);
            telemetry.addData("::::      :::::::::::::::      ::::", fancytitle);
            telemetry.addData(":::::      :::::::::::::      :::::", fancytitle);
            telemetry.addData("::::::      :::::::::::      ::::::", fancytitle);
            telemetry.addData(":::::::      :::::::::      :::::::", fancytitle);
            telemetry.addData("::::::::      :::::::      ::::::::", fancytitle);
            telemetry.addData(":::::::::      :::::      :::::::::", fancytitle);
            telemetry.addData("::::::::::      :::      ::::::::::", fancytitle);
            telemetry.addData(":::::::::::      :      :::::::::::", fancytitle);
            telemetry.addData("::::::::::::           ::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData("::::::::::::::       ::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 1|| letter == 3) {
            //Cool VegeMight E
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":      ::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter==2 || letter==6) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::::::                       ::::::", fancytitle);
            telemetry.addData(":::                              ::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::::      :", fancytitle);
            telemetry.addData("::       ::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::       ::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":       :::::::::::::         :::::", fancytitle);
            telemetry.addData("::       ::::::::::::           :::", fancytitle);
            telemetry.addData("::       :::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::       ::::::::::::::::       ::", fancytitle);
            telemetry.addData(":::                              ::", fancytitle);
            telemetry.addData("::::::                        :::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 4) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::     :", fancytitle);
            telemetry.addData(":      :::::::::::::::::::::      :", fancytitle);
            telemetry.addData(":       :::::::::::::::::::       :", fancytitle);
            telemetry.addData(":        :::::::::::::::::        :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":          :::::::::::::          :", fancytitle);
            telemetry.addData(":     :     :::::::::::     :     :", fancytitle);
            telemetry.addData(":     ::     :::::::::     ::     :", fancytitle);
            telemetry.addData(":     :::     :::::::     :::     :", fancytitle);
            telemetry.addData(":     ::::     :::::     ::::     :", fancytitle);
            telemetry.addData(":     :::::     :::     :::::     :", fancytitle);
            telemetry.addData(":     ::::::     :     ::::::     :", fancytitle);
            telemetry.addData(":     :::::::         :::::::     :", fancytitle);
            telemetry.addData(":     :::::::::::::::::::::::     :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 5) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        }  else if (letter == 7) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":         :::::::::::::::         :", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 8) {
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":                                 :", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::         :::::::::::::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        } else if (letter == 9 ){
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData("::         ::         ::         ::", fancytitle);
            telemetry.addData(":::::::::::::::::::::::::::::::::::", fancytitle);
        }
    }

}
