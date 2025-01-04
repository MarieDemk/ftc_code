package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (name = "Drive")
public class test extends OpMode {
    public Telemetry Telemetry;

    SlideControl slides = new SlideControl(300,0,0.01,0.1,0.0,0.0);
    Intake_settings intake = new Intake_settings();
    boolean tf = true;

    @Override
    public void init() {
        slides.init(hardwareMap, telemetry);
        intake.init(":LefttServo", "rightServo", "clawServo","armServo", "assembPuller", "motor_botLeft", hardwareMap, 0.0);
    }

    @Override
    public void loop() {
        if (gamepad2.dpad_right) {
            if (tf) {
                intake.movePulleyToTarget(0.5);
                slides.moveVSlideAuto(500, telemetry);
                intake.pullOutIntake();
                tf = false;
            }
            slides.moveHSlides(gamepad2.dpad_right, gamepad2.dpad_left, 0.8, telemetry);
        } else tf = true;

        if (gamepad2.dpad_left) {
            if (tf) {
                intake.pullBackIntake();
                intake.movePulleyToStart();
                slides.moveVSlideAuto(300, telemetry);
                intake.autoSurgSpin();
                tf = false;
            }
            slides.moveHSlides(gamepad2.dpad_right, gamepad2.dpad_left, 0.8, telemetry);
            slides.moveVSlideAuto(0, telemetry);
        } else tf = true;
        intake.backwardsRotategSurg(gamepad2.y, 0.8);
        intake.rotateSurgTubing(gamepad2.x,0.8);
    }
}