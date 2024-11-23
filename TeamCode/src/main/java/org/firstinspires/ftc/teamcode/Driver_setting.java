package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Driver_setting  {
    public final double COUNTS_PER_INCH = 537.6;
    public DcMotor topLeftDriveMotor;
    public DcMotor bottomLeftDriveMotor;
    public DcMotor topRightDriveMotor;
    public DcMotor bottomRightDriveMotor;
    public DcMotor leftSlideMotor;
    public DcMotor rightSlideMotor;
    public DcMotor armMotor;

    // Initialization of hardware map
    public void init(HardwareMap hwMap) {

        // Initialize the motors from the hardware map
        topLeftDriveMotor = hwMap.get(DcMotor.class,"motor_topLeft");
        bottomLeftDriveMotor = hwMap.get(DcMotor.class, "motor_botLeft");
        topRightDriveMotor = hwMap.get(DcMotor.class, "motor_topRight");
        bottomRightDriveMotor = hwMap.get(DcMotor.class, "motor_botRight");
        leftSlideMotor = hwMap.get(DcMotor.class, "leftSlide");
        rightSlideMotor = hwMap.get(DcMotor.class, "rightSlide");
        armMotor = hwMap.get(DcMotor.class, "armMotor");

        // Set motors to run without encoders
        topLeftDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomLeftDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        topRightDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomRightDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reverse direction for the left side motors
        topLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        bottomLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        topRightDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        bottomRightDriveMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set motors to brake when power is set to zero
        topLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize motor power to zero
        stopMotors();
    }

    // Method to stop all motors
    public void stopMotors() {
        topLeftDriveMotor.setPower(0);
        bottomLeftDriveMotor.setPower(0);
        topRightDriveMotor.setPower(0);
        bottomRightDriveMotor.setPower(0);
    }

    // Set equal power to all wheels (for forward/backward movement)
    public void power(double output) {
        topLeftDriveMotor.setPower(-output);
        bottomLeftDriveMotor.setPower(-output);
        topRightDriveMotor.setPower(output);
        bottomRightDriveMotor.setPower(output);
    }

    // Method to move robot using joystick inputs for mecanum drive
    public void moveRobot(double leftStickY, double leftStickX, double rightStickX) {
        // Calculate motor power based on joystick inputs
        double topLeftPower = leftStickY + leftStickX + rightStickX;
        double bottomLeftPower = leftStickY - leftStickX + rightStickX;
        double topRightPower = leftStickY - leftStickX - rightStickX;
        double bottomRightPower = leftStickY + leftStickX - rightStickX;

        // Set motor power
        topLeftDriveMotor.setPower(topLeftPower);
        topRightDriveMotor.setPower(topRightPower);
        bottomLeftDriveMotor.setPower(bottomLeftPower);
        bottomRightDriveMotor.setPower(bottomRightPower);
    }

    public void moveSlides(boolean padUp, boolean padDown, double distanceInInches, Telemetry telemetry) {
        // Ensure distance is valid
        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //
        // Calculate target position
        //int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);
        double circumference = 3.14*1.53;
        double rotationNeeded = 24/circumference;
        int encoderStuff = (int)(rotationNeeded*537.6);

        // Set target position once


        // Apply power based on direction
        if (padUp) {
            leftSlideMotor.setTargetPosition(-encoderStuff);
            rightSlideMotor.setTargetPosition(encoderStuff);

            // Switch to RUN_TO_POSITION mode
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftSlideMotor.setPower(0.3);
            rightSlideMotor.setPower(0.3);

            while (leftSlideMotor.isBusy() || rightSlideMotor.isBusy()){

            }
            leftSlideMotor.setPower(0);
            rightSlideMotor.setPower(0);
        } else if (padDown) {
            leftSlideMotor.setTargetPosition(encoderStuff);
            rightSlideMotor.setTargetPosition(-encoderStuff);

            // Switch to RUN_TO_POSITION mode
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftSlideMotor.setPower(-0.3);
            rightSlideMotor.setPower(-0.3);
            while (leftSlideMotor.isBusy() || rightSlideMotor.isBusy()){

            }
            leftSlideMotor.setPower(0);
            rightSlideMotor.setPower(0);
        } else {
            // Stop the motors if neither button is pressed
            stopSlides();
        }

        // Monitor and display telemetry
        telemetry.addData("Left Slide Target", leftSlideMotor.getTargetPosition());
        telemetry.addData("Right Slide Target", rightSlideMotor.getTargetPosition());
        telemetry.addData("Left Current Pos", leftSlideMotor.getCurrentPosition());
        telemetry.addData("Right Current Pos", rightSlideMotor.getCurrentPosition());
        telemetry.update();
    }

    public void moveArm(boolean padUp, boolean padDown, double distanceInInches, Telemetry telemetry) {
        // Reset encoder and calculate target position
        double circumference = 3.14 * 7;
        double rotationNeeded = distanceInInches / circumference;
        int encoderStuff = (int) (rotationNeeded * 537.6);

        if (padUp) {
            armMotor.setTargetPosition(-encoderStuff);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.3); // Positive power for upward motion
        } else if (padDown) {
            armMotor.setTargetPosition(encoderStuff);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.3); // Positive power for downward motion
        } else {
            // Maintain current position
            armMotor.setTargetPosition(armMotor.getCurrentPosition());
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.3); // Small power to actively hold position
        }

        // Telemetry
        telemetry.addData("Target Pos", armMotor.getTargetPosition());
        telemetry.addData("Current Pos", armMotor.getCurrentPosition());
        telemetry.update();
    }

    // Method to safely stop the slides
    public void stopSlides() {
        leftSlideMotor.setPower(0);
        rightSlideMotor.setPower(0);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopArm(){
        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}