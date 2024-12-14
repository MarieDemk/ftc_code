package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Servo_settings {

    public Servo intakePuller_R;
    public Servo intakePuller_L;
    public Servo intakeRotater;
    public Servo surgSpinner;
    boolean isMoving = false;
    public double startingServoPos;
    public boolean isButtonPress;
    public boolean isTargetPos;
    private Telemetry telemetry; // Instance-specific telemetry

    public void init(String servoName1, String servoName2, String servoName3, String servoName4, HardwareMap hwMap, Telemetry telemetry, double startingPos) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        intakePuller_R = hwMap.get(Servo.class, servoName1);
        intakePuller_L = hwMap.get(Servo.class, servoName2);
        intakeRotater = hwMap.get(Servo.class, servoName3);
        surgSpinner = hwMap.get(Servo.class, servoName4);

        startingServoPos = startingPos;
        intakePuller_R.setPosition(startingPos);
        intakePuller_L.setPosition(startingPos);
    }

    public void rotateIntakeAssembly(boolean button, int targetPos) {
        // Check if the button is pressed
        if (button && !isMoving) {
            // Toggle the target position
            isTargetPos = !isTargetPos;

            // Set servo positions based on the new target position
            double newTargetPos = isTargetPos ? targetPos : startingServoPos;

            // Start moving the servos
            intakePuller_L.setPosition(newTargetPos);
            intakePuller_R.setPosition(newTargetPos);
            isMoving = true;
        }

        // Check if the servos have reached their target position
        if (isMoving) {
            double leftDiff = Math.abs(intakePuller_L.getPosition() - (isTargetPos ? targetPos : startingServoPos));
            double rightDiff = Math.abs(intakePuller_R.getPosition() - (isTargetPos ? targetPos : startingServoPos));

            if (leftDiff < 0.01 && rightDiff < 0.01) {
                isMoving = false;
            }
        }
    }

    public void rotateSurgTubing(double duration){


    }

    public void pullBackIntake(){

    }

    public void clawGrab(){

    }

    public void rotateClawMech(){

    }

}