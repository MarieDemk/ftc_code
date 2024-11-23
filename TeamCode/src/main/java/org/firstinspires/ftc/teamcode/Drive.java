package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (name = "Drive")
public class Drive extends OpMode {
    Driver_setting driver = new Driver_setting();
    Servo_settings servo = new Servo_settings();

    @Override
    public void init() {
        driver.init(hardwareMap);
        servo.init("armServo",hardwareMap, telemetry, 1);
    }

    @Override
    public void loop() {
        driver.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        servo.openClaw(gamepad1.left_bumper,0.5);
    }
}