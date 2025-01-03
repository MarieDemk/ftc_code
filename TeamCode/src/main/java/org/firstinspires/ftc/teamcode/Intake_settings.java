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
    public double startingServoPos;

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

    public void rotateIntakeAssembly(double targetPos) {
        // Ensure startingServoPos is initialized correctly
        if (startingServoPos < 0.0 || startingServoPos > 1.0) {
            startingServoPos = 0.0; // Default starting position
        }

        // Check if the servo is ready to move
        if (!isMoving) {
            // Toggle the target position
            isTargetPos = !isTargetPos;

            // Calculate the new target position based on the toggle
            double newTargetPos = isTargetPos ? targetPos : startingServoPos;

            // Clamp the position to a valid range (0.0 to 1.0)
            newTargetPos = Math.min(1.0, Math.max(0.0, newTargetPos));

            // Set the servo to the new target position
            intakePuller_L.setPosition(newTargetPos);

            // Mark the servo as moving
            isMoving = true;
        }

        // Check if the servo has reached its target position
        if (isMoving) {
            double currentPosition = intakePuller_L.getPosition();
            double expectedPosition = isTargetPos ? targetPos : startingServoPos;
            double positionDifference = Math.abs(currentPosition - expectedPosition);
        }
    }

    public void rotateSurgTubing(boolean dpad, double power){
            if (dpad) {
                surgSpinner.setPower(-power);
            }
            surgSpinner.setPower(0);
    }

    public void pullBackIntake(){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if(timer.seconds() < 3){
            intakeAssembPuller.setPosition(-0.5);
        }
        intakeAssembPuller.setPosition(0);
    }

    public void pullOutIntake(){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if(timer.seconds() < 2.5){
            intakeAssembPuller.setPosition(0.5);
        }
        intakeAssembPuller.setPosition(0);
    }

    public void autoSurgSpin(){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if(timer.seconds() < 3){
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
        clawRotateL.setPosition(1);
    }

    public void clawRotateDown(){
        clawRotateR.setPosition(0.3);
        clawRotateL.setPosition(0.3);
    }

    public void clawrelease(boolean dpad){
        if(dpad){
            clawServ.setPosition(0);
        }
    }
}