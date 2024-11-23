package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Servo_settings {
    public Servo myservo;
    public boolean lasButtonPres = false;
    public boolean isClawOpen = false;
    public double startingServoPos;

    public void init(String servoName, HardwareMap hwMap, double startingPos){
        myservo = hwMap.get(Servo.class, servoName);
        startingServoPos = startingPos;
        myservo.setPosition(startingServoPos);
    }

    public void openClaw(boolean buttonPressed, double openPos){
        if(buttonPressed && !lasButtonPres){
            isClawOpen = !isClawOpen;
            myservo.setPosition(isClawOpen ? openPos : startingServoPos);
        }
        lasButtonPres = buttonPressed;
    }

}
