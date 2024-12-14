package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class SlideControl {
    public final double COUNTS_PER_INCH = 537.6;
    public static double kP = 0.01;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public DcMotorEx leftVSlideMotor;
    public DcMotorEx rightVSlideMotor;
    public DcMotorEx hMotor;
    public int minPos = 0;
    public int maxPos = 0;
    double safety_distance = 50;

    public SlideControl(int maxPos, int minPos, double kP, double kI, double kD, double kF){
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;

    }

    public void init(HardwareMap hwMap){
        leftVSlideMotor = hwMap.get(DcMotorEx.class, "leftVSlide");
        rightVSlideMotor = hwMap.get(DcMotorEx.class, "rightVSlide");
        hMotor = hwMap.get(DcMotorEx.class, "hMotor");

        hMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftVSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightVSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        hMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(kP, kI, kD, kF));
        rightVSlideMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(kP, kI, kD, kF));
        leftVSlideMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(kP, kI, kD, kF));
    }


    public void moveHSlidesAuto(int targetPos){
        hMotor.setTargetPosition(targetPos);
        hMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hMotor.setPower(0.8);
        while (hMotor.isBusy()){

        }
        hMotor.setPower(0);
        hMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveHSlides(boolean dPadUp, boolean dPadDown, double power){
        double currentPos = hMotor.getCurrentPosition();

        if(dPadUp && currentPos < maxPos - safety_distance) {
            hMotor.setPower(power);
        } else if (dPadDown && currentPos > minPos + safety_distance) {
            hMotor.setPower(-power);
        } else {
            hMotor.setPower(0);
        }
    }

    public void moveVSlide(boolean dPadUp, boolean dPadDown, double power){
        double currentPosR = rightVSlideMotor.getCurrentPosition();
        double currentPosL = leftVSlideMotor.getCurrentPosition();

        if(dPadUp && currentPosR < maxPos - safety_distance && currentPosL < maxPos - safety_distance) {
            rightVSlideMotor.setPower(power);
            leftVSlideMotor.setPower(-power);
        } else if (dPadDown && currentPosR > minPos + safety_distance && currentPosL > minPos + safety_distance) {
            rightVSlideMotor.setPower(-power);
            leftVSlideMotor.setPower(power);
        } else {
            rightVSlideMotor.setPower(0);
            leftVSlideMotor.setPower(0);
        }

    }

    public void moveVSlideAuto(int targetPos){
        rightVSlideMotor.setTargetPosition(targetPos);
        leftVSlideMotor.setTargetPosition(targetPos);
        rightVSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftVSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightVSlideMotor.setPower(0.8);
        leftVSlideMotor.setPower(0.8);
        while (rightVSlideMotor.isBusy() && leftVSlideMotor.isBusy()){

        }
        rightVSlideMotor.setPower(0);
        leftVSlideMotor.setPower(0);
        rightVSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftVSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
