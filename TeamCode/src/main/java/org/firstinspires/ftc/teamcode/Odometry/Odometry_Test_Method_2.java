package org.firstinspires.ftc.teamcode.Odometry;

import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.driveD;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.driveF;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.driveP;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.rotationD;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.rotationF;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.rotationP;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.strafeD;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.strafeF;
import static org.firstinspires.ftc.teamcode.Odometry.PIDMovement.MovePIDTuning.strafeP;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.HolonomicOdometry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.wolfpackmachina.bettersensors.HardwareMapProvider;
import com.wolfpackmachina.bettersensors.Sensors.Gyro;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ConstantsAndSetPoints.Setpoints;
import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Bottom_Gripper_Assembly;
import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Slides;
import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Top_gripper;


@Autonomous
@Disabled
public class Odometry_Test_Method_2 extends LinearOpMode {

    Drivetrain drive = new Drivetrain();

    public static final double CENTER_WHEEL_OFFSET = -17;

    public static final double WHEEL_DIAMETER = 3.5;

    public static final double TICKS_PER_REV = 8192;
    public static final double DISTANCE_PER_PULSE = Math.PI * WHEEL_DIAMETER / TICKS_PER_REV;

    private boolean lowering = false;

    private boolean Nest_Occupied = false;

    private boolean Extending_High = false;

    private MecanumDrive driveTrain;

    private boolean conefound = false;

    private boolean abort = false;

    private MotorEx LF, RF, LB, RB;

    Setpoints setpoints = new Setpoints();

    Gyro gyro;

    PIDFController drivePID;
    PIDFController strafePID;
    PIDFController PivotPID;

    double Xdist = 0;
    double Ydist = 0;

    double rotdist = 0;

    double XdistForStop = 0;
    double YdistForStop = 0;

    double rotdistForStop = 0;

    double RRXdist = 0;
    double RRYdist = 0;
    double Horizontal = 0;
    double Vertical = 0;

    double Horizontal2 = 0;
    double Vertical2 = 0;

    double ConvertedHeading = 0;
    double Pivot = 0;

    Top_gripper top = new Top_gripper();

    Bottom_Gripper_Assembly bottom = new Bottom_Gripper_Assembly();

    Slides slide = new Slides();

    double CurrentXPos = 0;
    double CurrentYPos = 0;

    double StartingHeading = 0;

    double StartingHeadinggyro = 0;

    private Motor.Encoder leftOdometer, rightOdometer, centerOdometer;
    private HolonomicOdometry odometry;

    public static final double TRACKWIDTH = 36.32;


    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap, 0);

        top.init(hardwareMap);

        bottom.init(hardwareMap);

        slide.init(hardwareMap, 1);

        OdometryInit();

        waitForStart();

        drive.WithOutEncoders();

        ExtendHigh();

        //Drop Off Position
        Odo_Drive(112, 0, 144, 0.1);

        DropPreLoad();

        CheckVSlidePosForZero();

        Destack_4();

        while (opModeIsActive()){
            odometry.updatePose();

            telemetry.addData("heading", ConvertedHeading);
            telemetry.addData("X", getXpos());
            telemetry.addData("Y", getYpos());
            telemetry.update();
        }

    }

    public double getXpos() {
        return odometry.getPose().getX();
    }

    public double getYpos() {
        return odometry.getPose().getY();
    }

    public double getheading() {
        return odometry.getPose().getHeading();
    }

    public void ExtendHigh (){
        top.Top_Gripper.setPosition(0);

        top.Top_Pivot.setPosition(0.5);

        //Extend vertical slides and drop cone
        slide.Right_Slide.setTargetPosition(1900);
        slide.Left_Slide.setTargetPosition(1900);

        slide.Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        top.Top_Pivot.setPosition(0.25);

        slide.Right_Slide.setPower(1);
        slide.Left_Slide.setPower(1);

    }

    public void DropPreLoad () {

        if (slide.Right_Slide.getCurrentPosition() < 1875){
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (slide.Right_Slide.getCurrentPosition() < 1875){
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (slide.Right_Slide.getCurrentPosition() < 1875){
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (slide.Right_Slide.getCurrentPosition() < 1875){
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            Thread.sleep(200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        top.Top_Pivot.setPosition(0);

        try {
            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        top.Top_Gripper.setPosition(0.3);

        //TO DO: Insert WHILE loop
        if (top.Top_Gripper.getPosition() == 0.3) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            top.Top_Pivot.setPosition(0.4);

            slide.Right_Slide.setTargetPosition(0);
            slide.Left_Slide.setTargetPosition(0);

            slide.Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide.Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            slide.Right_Slide.setPower(-0.9);
            slide.Left_Slide.setPower(-0.9);

            lowering = true;
        }
    }

    public void DropPreLoadNotSame() {
        top.Top_Pivot.setPosition(0.5);

        //Extend vertical slides and drop cone
        slide.Right_Slide.setTargetPosition(1800);
        slide.Left_Slide.setTargetPosition(1800);
        slide.Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (slide.Right_Slide.getCurrentPosition() < 1750 && slide.Left_Slide.getCurrentPosition() < 1750) {
            slide.Right_Slide.setPower(1);
            slide.Left_Slide.setPower(1);
            top.Top_Pivot.setPosition(0.42);
        }
        slide.Right_Slide.setPower(0);
        slide.Left_Slide.setPower(0);
        top.Top_Pivot.setPosition(0);


        slide.Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        try {
            Thread.sleep(400);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        top.Top_Gripper.setPosition(0.3);

        //TO DO: Insert WHILE loop
        if (top.Top_Gripper.getPosition() == 0.3) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            top.Top_Pivot.setPosition(0.4);
            slide.Right_Slide.setTargetPosition(0);
            slide.Left_Slide.setTargetPosition(0);
            slide.Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide.Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide.Right_Slide.setPower(-0.9);
            slide.Left_Slide.setPower(-0.9);
            lowering = true;
        }
    }

    public void CheckVSlidePosForZero() {
        if (slide.Right_Slide.getCurrentPosition() < 10 && !slide.Right_Slide.isBusy() && slide.Left_Slide.getCurrentPosition() < 10 && !slide.Left_Slide.isBusy()) {
            slide.Right_Slide.setPower(0);
            slide.Left_Slide.setPower(0);

            slide.Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lowering = false;

        }else if (lowering) {
            slide.Right_Slide.setPower(-0.9);
            slide.Left_Slide.setPower(-0.9);
        }
    }

    public void CheckVSlidePosForDropHigh() {
        if (slide.Right_Slide.getCurrentPosition() < 1750 && slide.Left_Slide.getCurrentPosition() > 1750 ) {

            slide.Right_Slide.setPower(1);
            slide.Left_Slide.setPower(1);

            Extending_High = true;

        }else{
            slide.Right_Slide.setPower(0);
            slide.Left_Slide.setPower(0);

            slide.Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            Extending_High = false;
        }
    }

    public void CollectCone(double De_pos) {

        bottom.Base_Gripper.setPosition(0.4);

        bottom.Destacker_Left.setPosition(De_pos);
        bottom.Destacker_Right.setPosition(De_pos);

        if(bottom.Destacker_Left.getPosition() == setpoints.De_Pos_1){
            bottom.Base_Pivot.setPosition(0.1);
        }else{
            bottom.Base_Pivot.setPosition(0.05);
        }


        top.Top_Pivot.setPosition(0.5);

        slide.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slide.Extend.setPower(-1);

        conefound = slide.sensorRange.getDistance(DistanceUnit.MM) < 60;

        //extend till we find a cone or get to the slides limit
        while (!conefound && slide.Extend.getCurrentPosition() > -900) {

            CheckVSlidePosForZero();

            conefound = slide.sensorRange.getDistance(DistanceUnit.MM) < 60;

            boolean SlowPoint = slide.Extend.getCurrentPosition() < -530;

            if (SlowPoint){
                slide.Extend.setPower(-0.35);
            }else {
                slide.Extend.setPower(-1);
            }

        }
        slide.Extend.setPower(0);

        if (!conefound){
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (conefound){

            //close gripper
            bottom.Base_Gripper.setPosition(0);

            CheckVSlidePosForZero();

            //make sure gripper is closed
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            bottom.Base_Pivot.setPosition(0.82);

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            slide.Extend.setTargetPosition(0);
            slide.Extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (slide.Extend.isBusy()) {

                CheckVSlidePosForZero();

                bottom.Base_Pivot.setPosition(0.82);

                slide.Extend.setPower(0.6);

                if(slide.Extend.getCurrentPosition() > -300){
                    bottom.Destacker_Left.setPosition(setpoints.De_Pos_5);
                    bottom.Destacker_Right.setPosition(setpoints.De_Pos_5);
                }
                if(slide.Extend.getCurrentPosition() > -100){
                    bottom.Base_Gripper.setPosition(0.4);
                }
                if(slide.Extend.getCurrentPosition() > -50){
                    //open top gripper
                    top.Top_Gripper.setPosition(0.35);

                    //take top pivot to pick up the cone
                    top.Top_Pivot.setPosition(1);
                }
            }

            slide.Extend.setPower(0);


            while (lowering) {
                CheckVSlidePosForZero();
            }

            //open base gripper
            bottom.Base_Gripper.setPosition(0.4);

            Nest_Occupied = slide.colour.blue() > 2000;

            bottom.Base_Pivot.setPosition(1);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            Nest_Occupied = slide.colour.blue() > 2000;

            if(!Nest_Occupied){
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }else{
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            Nest_Occupied = slide.colour.blue() > 2000;

            if(!Nest_Occupied){
//                    Top_Pivot.setPosition(0.8);
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
//                    Top_Pivot.setPosition(1);
            }

            Nest_Occupied = slide.colour.blue() > 2000;


            if (Nest_Occupied) {

                //close top gripper
                top.Top_Gripper.setPosition(0);

            }else{
                abort = true;
            }


        }else{

            bottom.Base_Pivot.setPosition(0.82);

            slide.Extend.setTargetPosition(0);

            slide.Extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (slide.Extend.isBusy()) {

                CheckVSlidePosForZero();
                slide.Extend.setPower(0.8);

            }
            slide.Extend.setPower(0);

            bottom.Destacker_Left.setPosition(setpoints.De_Pos_5);
            bottom.Destacker_Right.setPosition(setpoints.De_Pos_5);

            abort = true;
        }
    }

    public void Odo_Drive(double targetX, double targetY, double targetRot, double error) {

        do {

//            CheckVSlidePosForDropHigh();

            CheckVSlidePosForZero();

            //UPDATE ODOMETRY
            odometry.updatePose();

            //GET CURRENT X
            CurrentXPos = getXpos();

            //GET CURRENT Y
            CurrentYPos = getYpos();
//
//            gyro.update();
//
//            //GET START HEADING WITH GYRO
//            StartingHeadinggyro = gyro.angle();

            //GET START HEADING WITH ODOMETRY
            StartingHeading = Math.toDegrees(getheading());

            //PID FOR DRIVING IN THE Y DIRECTION
            drivePID.setPIDF(driveP, 0, driveD, driveF);

            //PID FOR DRIVING IN THE X DIRECTION
            strafePID.setPIDF(strafeP, 0, strafeD, strafeF);

            //PID FOR TURNING
            PivotPID.setPIDF(rotationP, 0, rotationD, rotationF);

            //SET DISTANCE TO TRAVEL ERROR
            Xdist = (targetX - CurrentXPos) * 1.24;
            Ydist = (targetY - CurrentYPos) * 1.24;

            XdistForStop = (targetX - CurrentXPos);
            YdistForStop = (targetY - CurrentYPos);

            //CONVERT HEADING FOR TRIG CALCS
            if (StartingHeading <= 0) {
                ConvertedHeading = (360 + StartingHeading);
            } else {
                ConvertedHeading = (0 + StartingHeading);
            }

            rotdist = (targetRot - ConvertedHeading) * 1.3;

            rotdistForStop = (targetRot - ConvertedHeading);

            if (rotdist < -180) {
                rotdist = (360 + rotdist);
            } else if (rotdist > 180) {
                rotdist = (rotdist - 360);
            }

            if (rotdistForStop < -180) {
                rotdistForStop = (360 + rotdistForStop);
            } else if (rotdistForStop > 180) {
                rotdistForStop = (rotdistForStop - 360);
            }

            //CONVERT TARGET TO ROBOT RELATIVE TARGET
            RRXdist = Xdist * Math.cos(Math.toRadians(360 - ConvertedHeading)) - Ydist * Math.sin(Math.toRadians(360 - ConvertedHeading));

            RRYdist = Xdist * Math.sin(Math.toRadians(360 - ConvertedHeading)) + Ydist * Math.cos(Math.toRadians(360 - ConvertedHeading));

            //SET DRIVE CONSTANTS TO THE PIDF CONTROL LOOPS
            Vertical = drivePID.calculate(-RRXdist);
            Horizontal = strafePID.calculate(-RRYdist);
            Pivot = PivotPID.calculate(-rotdist);

            //SET MOTOR POWER USING THE PID OUTPUT
            drive.RF.setPower(-Pivot + (Vertical + Horizontal));
            drive.RB.setPower((-Pivot * 1.4) + (Vertical - (Horizontal * 1.3)));
            drive.LF.setPower(Pivot + (Vertical - Horizontal));
            drive.LB.setPower((Pivot * 1.4) + (Vertical + (Horizontal * 1.3)));

            telemetry.addData("heading", ConvertedHeading);
            telemetry.addData("X", getXpos());
            telemetry.addData("Y", getYpos());
            telemetry.update();

        }while ((Math.abs(XdistForStop) > 0.6 + error) || (Math.abs(YdistForStop) > 0.6 + error) || (Math.abs(rotdistForStop) > 0.8 + error));

        drive.RF.setPower(0);
        drive.RB.setPower(0);
        drive.LF.setPower(0);
        drive.LB.setPower(0);

    }

    public void OdometryInit() {

        HardwareMapProvider.setMap(this);

        drivePID = new PIDFController(driveP, 0, driveD, driveF);
        strafePID = new PIDFController(strafeP, 0, strafeD, strafeF);
        PivotPID = new PIDFController(rotationP, 0, rotationD, rotationF);

        LF = new MotorEx(hardwareMap, "LF");
        LB = new MotorEx(hardwareMap, "LB");
        RF = new MotorEx(hardwareMap, "RF");
        RB = new MotorEx(hardwareMap, "RB");

        gyro = new Gyro("imu", 0);

        driveTrain = new MecanumDrive(RB, RF, LB, LF);

        leftOdometer = LF.encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        rightOdometer = RF.encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        centerOdometer = RB.encoder.setDistancePerPulse(DISTANCE_PER_PULSE);

        leftOdometer.setDirection(Motor.Direction.FORWARD);
        rightOdometer.setDirection(Motor.Direction.REVERSE);

        odometry = new HolonomicOdometry(
                leftOdometer::getDistance,
                rightOdometer::getDistance,
                centerOdometer::getDistance,
                TRACKWIDTH, CENTER_WHEEL_OFFSET
        );

        odometry.update(0, 0, 0);

        odometry.updatePose(new Pose2d(0, 0, new Rotation2d(3.141)));

//        odometry.updatePose();

    }

    public void Destack_4 () {

        //Collect Cone Position
        Odo_Drive(130, 0, 90, 0);

        //Collect Cone Position
        Odo_Drive(130, 20, 90, 0);

        bottom.Base_Gripper.setPosition(0.4);

        bottom.Base_Pivot.setPosition(0.12);

        try {
            Thread.sleep(350);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //cone 1
        CollectCone(setpoints.De_Pos_1);

        //Drop Off Position
        Odo_Drive(124, 11, 131 , 0);

        if (!abort){
            DropPreLoadNotSame();
        }

        if (abort){

            //Drive to position
            top.Top_Pivot.setPosition(setpoints.Top_Pivot_Waiting_For_Cone);

            bottom.Base_Pivot.setPosition(0.72);

        }else {

            //Collect Cone Position
            Odo_Drive(130, 20, 90, 0);

            bottom.Base_Gripper.setPosition(0.4);

            bottom.Base_Pivot.setPosition(0.12);

            try {
                Thread.sleep(350);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //cone 1
            CollectCone(setpoints.De_Pos_2);

            //Drop Off Position
            Odo_Drive(124, 11, 131 , 0);

            if (!abort){
                DropPreLoadNotSame();
            }

            if (abort){

                top.Top_Pivot.setPosition(setpoints.Top_Pivot_Waiting_For_Cone);

                bottom.Base_Pivot.setPosition(0.72);

            }else {

                //Collect Cone Position
                Odo_Drive(130, 20, 90, 0);

                bottom.Base_Gripper.setPosition(0.4);

                bottom.Base_Pivot.setPosition(0.12);

                try {
                    Thread.sleep(350);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //cone 1
                CollectCone(setpoints.De_Pos_3);

                //Drop Off Position
                Odo_Drive(124, 11, 131 , 0);

                if (!abort){
                    DropPreLoadNotSame();
                }

                if (abort){

                    top.Top_Pivot.setPosition(setpoints.Top_Pivot_Waiting_For_Cone);

                    bottom.Base_Pivot.setPosition(0.72);

                }else {

                    //Collect Cone Position
                    Odo_Drive(130, 20, 90, 0);

                    bottom.Base_Gripper.setPosition(0.4);

                    bottom.Base_Pivot.setPosition(0.12);

                    try {
                        Thread.sleep(350);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    //cone 1
                    CollectCone(setpoints.De_Pos_4);

                    ///Drop Off Position
                    Odo_Drive(124, 11, 131 , 0);

                    if (!abort){
                        DropPreLoadNotSame();
                    }
                }
            }

        }

    }

    public void Destack_3 () {

        //Collect Cone Position
        Odo_Drive(130, 0, 90,0);

        //Collect Cone Position
        Odo_Drive(130, 20, 90,0);

        bottom.Base_Gripper.setPosition(0.4);

        bottom.Base_Pivot.setPosition(0.12);

        try {
            Thread.sleep(350);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //cone 1
        CollectCone(setpoints.De_Pos_1);

        ///Drop Off Position
        Odo_Drive(124, 11, 131 , 0);

        if (!abort){
            DropPreLoadNotSame();
        }

        if (abort){

            //Drive to position
            top.Top_Pivot.setPosition(setpoints.Top_Pivot_Waiting_For_Cone);

            bottom.Base_Pivot.setPosition(0.72);

        }else {

            //Collect Cone Position
            Odo_Drive(130, 20, 90, 0);

            bottom.Base_Gripper.setPosition(0.4);

            bottom.Base_Pivot.setPosition(0.12);

            try {
                Thread.sleep(350);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //cone 1
            CollectCone(setpoints.De_Pos_2);

            ///Drop Off Position
            Odo_Drive(124, 11, 131 , 0);

            if (!abort){
                DropPreLoadNotSame();
            }

            if (abort){

                top.Top_Pivot.setPosition(setpoints.Top_Pivot_Waiting_For_Cone);

                bottom.Base_Pivot.setPosition(0.72);

            }else {

                //Collect Cone Position
                Odo_Drive(130, 20, 90, 0);

                bottom.Base_Gripper.setPosition(0.4);

                bottom.Base_Pivot.setPosition(0.12);

                try {
                    Thread.sleep(350);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //cone 1
                CollectCone(setpoints.De_Pos_3);

                ///Drop Off Position
                Odo_Drive(124, 11, 131 , 0);

                if (!abort){
                    DropPreLoadNotSame();
                }
            }

        }

    }
}