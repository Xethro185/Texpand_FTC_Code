package org.firstinspires.ftc.teamcode.Auto.Test_Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
@Disabled
public class DriveDistance extends LinearOpMode {

    public DcMotor RF = null;
    public DcMotor LF = null;
    public DcMotor RB = null;
    public DcMotor LB = null;

    @Override
    public void runOpMode() throws InterruptedException {


        //Driving motors config
        RF = hardwareMap.get(DcMotor.class, "RF");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        LB = hardwareMap.get(DcMotor.class, "LB");

        RF.setDirection(DcMotorSimple.Direction.FORWARD);
        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setDirection(DcMotorSimple.Direction.REVERSE);

        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RF.setPower(0);
        LF.setPower(0);
        RB.setPower(0);
        LB.setPower(0);

        waitForStart();

        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Constants for the encoder counts per revolution and gear ratio
        final int ENCODER_COUNTS_PER_REVOLUTION = 510;
        final double GEAR_RATIO = 1.0;

        // Calculate the number of ticks per revolution
        int ticksPerRevolution = (int)(ENCODER_COUNTS_PER_REVOLUTION / GEAR_RATIO);

        // Calculate the circumference of the wheel
        double wheelCircumference = Math.PI * 2 * (9.6 / 2.0);

        // Calculate the number of encoder ticks required to travel the given distance
        int ticks = Math.toIntExact((long) (60 * ticksPerRevolution / wheelCircumference));

//        // Calculate the correction factor
//        double correctionFactor = 1;
//
//        // Apply the correction factor to the calculated number of encoder ticks
//        ticks = (int)(ticks * (correctionFactor));
        telemetry.addData("Target Position:", ticks);
        telemetry.update();
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // Set the target position for each motor
        RF.setTargetPosition(ticks);
        RB.setTargetPosition(ticks);
        LF.setTargetPosition(ticks);
        LB.setTargetPosition(ticks);

        // Set the motors to run to the target position
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set the power of each motor
        RF.setPower(0.5);
        RB.setPower(0.5);
        LF.setPower(0.5);
        LB.setPower(0.5);

        // Wait for the motors to reach their target positions
        while (RF.getCurrentPosition() < ticks - 20 || RB.getCurrentPosition() < ticks - 20 || LF.getCurrentPosition() < ticks - 20 || LB.getCurrentPosition() < ticks - 20) {
            telemetry.addData("Target Position:", ticks);
            telemetry.addData("RF Motor Power:", RF.getPower());
            telemetry.addData("RF Motor Pos:", RF.getCurrentPosition());
            telemetry.addData("RB Motor Power:", RB.getPower());
            telemetry.addData("RB Motor Pos:", RB.getCurrentPosition());
            telemetry.addData("LF Motor Power:", LF.getPower());
            telemetry.addData("LF Motor Pos:", LF.getCurrentPosition());
            telemetry.addData("LB Motor Power:", LB.getPower());
            telemetry.addData("LB Motor Pos:", LB.getCurrentPosition());
            telemetry.update();


            if(RB.getCurrentPosition() > ticks){
                RB.setPower(0);
            }
            if(LF.getCurrentPosition() > ticks){
                LF.setPower(0);
            }
            if(LB.getCurrentPosition() > ticks){
                LB.setPower(0);
            }
            if(RF.getCurrentPosition() > ticks){
                RF.setPower(0);
            }
        }

        // Stop the motors
        RF.setPower(0);
        RB.setPower(0);
        LF.setPower(0);
        LB.setPower(0);
//        try {
//            Thread.sleep(10000);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}