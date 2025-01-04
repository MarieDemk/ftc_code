package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (name = "test2")
public class test2 extends OpMode {
    public Telemetry Telemetry;

    SlideControl slides = new SlideControl(300,0,0.01,0.1,0.0,0.0);
    Intake_settings intake = new Intake_settings();
    boolean tf = true;

    @Override
    public void init() {
        slides.init(hardwareMap, telemetry);
        intake.init("LefttServo", "rightServo", "clawServo","armServo", "assembPuller", "motor_botLeft", hardwareMap, 0.0);
    }

    @Override
    public void loop() {
        if(gamepad1.b){
            if (tf){
                intake.pullBackIntake();
                tf = false;
            }
        } else tf = true;
        if(gamepad1.x){
            if(tf){
                intake.pullOutIntake();
                tf = false;
            }
        } else tf = true;
    }
}