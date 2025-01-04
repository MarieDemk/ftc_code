package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (name = "pidTest")
public class PidTest extends OpMode {
    public Telemetry Telemetry;

    SlideControl slides = new SlideControl(2000,0,0.01,0.1,0.0,0.0);
    Intake_settings intake = new Intake_settings();
    Driver_setting driver = new Driver_setting();
    boolean tf = true;

    @Override
    public void init() {
        slides.init(hardwareMap, telemetry);
        driver.init(hardwareMap);
        intake.init(":LefttServo", "rightServo", "clawServo","armServo", "assembPuller", "surg", hardwareMap, 0.7);
    }

    @Override
    public void loop() {
        // Pull out intake when dpad_right is pressed
        driver.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if (gamepad2.dpad_up && tf) {
            intake.clawRotateUp();
            tf = false;
        }

        if (gamepad2.right_bumper && tf) {
            intake.clawgrab(gamepad2.right_bumper);
            tf = false;
        }
        if (gamepad2.left_bumper && tf) {
            intake.clawrelease(gamepad2.left_bumper);
            tf = false;
        }


//        if (gamepad2.dpad_down && tf) {
//            intake.clawRotateDown();
//            tf = false;
//        }

        if(gamepad2.b && tf){
            intake.clawRotateDown();
            tf = false;
        }

        slides.moveVSlide(gamepad2.dpad_up, gamepad2.dpad_down, 1, telemetry);
        if (!gamepad2.dpad_right && !gamepad2.dpad_left) {
            tf = true;
        }
    }
}