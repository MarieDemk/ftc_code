package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake_settings {
    public Servo clawServ;
    public Servo clawRotateR;
    public Servo clawRotateL;
    public Servo intakePuller_L;
    public Servo intakeAssembPuller;
    public DcMotorEx surgSpinner;
    boolean isMoving = false;
    private boolean isIntakeBack = false; // Track state
    public double startingServoPos;
    private ElapsedTime intakeTimer = new ElapsedTime(); // Make the timer persistent
    private boolean isIntakeOut = false;
    public boolean isTargetPos = false;
    private Telemetry telemetry; // Instance-specific telemetry

    public void init(String clawRotL, String clawRotR, String clawGrad,String servoName2, String servoPulBack, String motorName, HardwareMap hwMap, double startingPos) {
        intakePuller_L = hwMap.get(Servo.class, servoName2);
        surgSpinner = hwMap.get(DcMotorEx.class, motorName);
        intakeAssembPuller = hwMap.get(Servo.class, servoPulBack);
        clawServ = hwMap.get(Servo.class, clawGrad);
        clawRotateL = hwMap.get(Servo.class, clawRotL);
        clawRotateR = hwMap.get(Servo.class, clawRotR);

        startingServoPos = startingPos;
        intakePuller_L.setPosition(startingPos);
    }

    public void movePulleyToTarget(double targetPos) {
        // Ensure startingServoPos is initialized correctly
        if (startingServoPos < 0.0 || startingServoPos > 1.0) {
            startingServoPos = 0.0; // Default starting position
        }

        // Check if the servo is ready to move
        if (!isMoving) {
            // Set the target position
            double clampedTargetPos = Math.min(1.0, Math.max(0.0, targetPos));

            // Move the servo to the target position
            intakePuller_L.setPosition(clampedTargetPos);

            // Mark the servo as moving
            isMoving = true;
        }

        // Check if the servo has reached its target position
        if (isMoving) {
            double currentPosition = intakePuller_L.getPosition();
            double positionDifference = Math.abs(currentPosition - targetPos);

            // If the position is within a small margin, mark it as stopped
            if (positionDifference < 0.01) {
                isMoving = false;
            }
        }
    }

    public void movePulleyToStart() {
        // Ensure startingServoPos is initialized correctly
        if (startingServoPos < 0.0 || startingServoPos > 1.0) {
            startingServoPos = 0.0; // Default starting position
        }

        // Check if the servo is ready to move
        if (!isMoving) {
            // Move the servo to the starting position
            intakePuller_L.setPosition(startingServoPos);

            // Mark the servo as moving
            isMoving = true;
        }

        // Check if the servo has reached the starting position
        if (isMoving) {
            double currentPosition = intakePuller_L.getPosition();
            double positionDifference = Math.abs(currentPosition - startingServoPos);

            // If the position is within a small margin, mark it as stopped
            if (positionDifference < 0.01) {
                isMoving = false;
            }
        }
    }

    public void rotateSurgTubing(boolean dpad, double power){
            if (dpad) {
                surgSpinner.setPower(-power);
            }
            surgSpinner.setPower(0);
    }



    public void pullBackIntake() {
        if (!isIntakeBack) {
            intakeTimer.reset();
            isIntakeBack = true;
        }
        while (true) {
            if (intakeTimer.seconds() < 3) {
                intakeAssembPuller.setPosition(0); // Pull back for 3 seconds
            } else {
                intakeAssembPuller.setPosition(0.5); // Reset position after 3 seconds
                isIntakeBack = false; // Reset state for next usage
                break;
            }
        }
    }

    public void pullOutIntake() {
        if (!isIntakeOut) {
            intakeTimer.reset();
            isIntakeOut = true;
        }

        if (intakeTimer.seconds() < 3) {
            intakeAssembPuller.setPosition(1); // Keep pulling for 7 seconds
        } else {
            intakeAssembPuller.setPosition(0.5); // Reset position after 7 seconds
            isIntakeOut = false; // Reset state for next usage
        }
    }

    public void autoSurgSpin(){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if(timer.seconds() < 7){
            surgSpinner.setPower(0.9);
            sleep(1000);
            clawServ.setPosition(0.2);
        }
        surgSpinner.setPower(0);
    }

    public void backwardsRotategSurg(boolean dpad, double power){
        if (dpad) {
            surgSpinner.setPower(power);
        }
        surgSpinner.setPower(0);
    }

    public void clawRotateUp(){
        clawRotateR.setPosition(1);
        clawRotateL.setPosition(0.05);
    }

    public void clawRotateDown(){
        clawRotateR.setPosition(0.75);
        clawRotateL.setPosition(0.3);
    }

    public void clawgrab(boolean dpad) {
        if (dpad) {
            clawServ.setPosition(0.4);
        }
    }
    public void clawrelease(boolean dpad){
        if(dpad){
            clawServ.setPosition(0);
        }
    }
}