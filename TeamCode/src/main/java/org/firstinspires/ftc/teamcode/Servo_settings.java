package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Servo_settings {
    public Servo myservo;
    public boolean lasButtonPres = false;
    public boolean isClawOpen = false;
    public double startingServoPos;
    private Telemetry telemetry; // Instance-specific telemetry

    public void init(String servoName, HardwareMap hwMap, Telemetry telemetry, double startingPos) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        myservo = hwMap.get(Servo.class, servoName);
        startingServoPos = startingPos;
        myservo.setPosition(startingServoPos);
    }

    public void openClaw(boolean buttonPressed, double openPos) {
        if (buttonPressed && !lasButtonPres) {
            isClawOpen = !isClawOpen;
            myservo.setPosition(isClawOpen ? openPos : startingServoPos);
            telemetry.addData("buttonPressed", buttonPressed);
            telemetry.update();
        }
        lasButtonPres = buttonPressed;
    }
}