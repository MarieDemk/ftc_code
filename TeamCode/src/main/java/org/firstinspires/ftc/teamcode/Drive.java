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
    SlideControl slides = new SlideControl(50,30,0.01,0.1,0.0,0.0);
    Servo_settings servo = new Servo_settings();
    Servo_settings servoBucket = new Servo_settings();

    @Override
    public void init() {
        driver.init(hardwareMap);
        slides.init(hardwareMap);
    }

    @Override
    public void loop() {
        //wait();
        driver.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        slides.moveHSlides(gamepad1.dpad_right, gamepad1.dpad_left, 0.8);
        slides.moveVSlide(gamepad1.dpad_up, gamepad1.dpad_down, 0.8);
    }
}