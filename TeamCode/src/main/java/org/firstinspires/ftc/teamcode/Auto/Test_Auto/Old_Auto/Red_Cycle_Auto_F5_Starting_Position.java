package org.firstinspires.ftc.teamcode.Auto.Test_Auto.Old_Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Drivetrain;

@Autonomous
@Disabled
public class Red_Cycle_Auto_F5_Starting_Position extends LinearOpMode {

    public DcMotor RF = null;
    public DcMotor LF = null;
    public DcMotor RB = null;
    public DcMotor LB = null;

    public ColorSensor colour = null;

    public DcMotor Right_Slide = null;
    public DcMotor Left_Slide = null;

    public DcMotor Extend = null;

    public Servo Base_Gripper = null;
    public Servo Base_Pivot = null;
    public Servo Top_Gripper = null;
    public Servo Top_Pivot = null;
    public Servo Destacker_Left = null;
    public Servo Destacker_Right = null;

    private double vertical;
    private double horizontal;
    private double pivot;

    private int Toppos = 0;
    private int stakerpos = 0;
    private double basepos = 0;

    private boolean conefound = false;
    private boolean extending = false;
    private double slow = 1;
    private boolean lowering = false;
    public double Destack_position = 0;


    //Drivetrain object
    Drivetrain drive = new Drivetrain();




    @Override
    public void runOpMode() throws InterruptedException {

        colour = hardwareMap.get(ColorSensor.class, "colour");

        RF = hardwareMap.get(DcMotor.class, "RF");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        LB = hardwareMap.get(DcMotor.class, "LB");

        Right_Slide = hardwareMap.get(DcMotor.class, "Right slide");
        Left_Slide = hardwareMap.get(DcMotor.class, "Left slide");

        Extend = hardwareMap.get(DcMotor.class, "Extend");

        Base_Gripper = hardwareMap.get(Servo.class, "Base_Gripper");
        Base_Pivot = hardwareMap.get(Servo.class, "Base_Pivot");
        Top_Gripper = hardwareMap.get(Servo.class, "Top_Gripper");
        Top_Pivot = hardwareMap.get(Servo.class, "Top_Pivot");
        Destacker_Left = hardwareMap.get(Servo.class, "Destacker Left");
        Destacker_Right = hardwareMap.get(Servo.class, "Destacker Right");

        Destacker_Left.setDirection(Servo.Direction.REVERSE);
        Base_Gripper.setDirection(Servo.Direction.FORWARD);
        Base_Pivot.setDirection(Servo.Direction.FORWARD);
        Top_Gripper.setDirection(Servo.Direction.FORWARD);
        Top_Pivot.setDirection(Servo.Direction.REVERSE);

        RF.setDirection(DcMotorSimple.Direction.FORWARD);
        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setDirection(DcMotorSimple.Direction.REVERSE);

        Left_Slide.setDirection(DcMotorSimple.Direction.REVERSE);

        Right_Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RF.setPower(0);
        LF.setPower(0);
        RB.setPower(0);
        LB.setPower(0);

        Top_Gripper.setPosition(0);
        Base_Gripper.setPosition(0.4);
        Base_Pivot.setPosition(0);
        Top_Pivot.setPosition(0.4);

        drive.init(hardwareMap, 1);


        waitForStart();


        drive.DriveDistanceLong(140, 0.6);

        drive.TurnToHeading(90,0.45);

        drive.StrafeDistance_Left(25, 0.6);

        drive.DriveDistance(20, 0.6);

        drive.TurnToHeading(14,0.45);

        drive.StrafeDistance(14, 0.6);

        drive.DriveDistance(5, 0.6);

        Top_Pivot.setPosition(0.5);
        DropPreLoad();
        try {
            Thread.sleep(20);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Destacker_Left.setPosition(0.37);
        Destacker_Right.setPosition(0.37);
        Destack();
        try {
            Thread.sleep(20);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Destacker_Left.setPosition(0.44);
        Destacker_Right.setPosition(0.44);
        Destack();
        try {
            Thread.sleep(20);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Destacker_Left.setPosition(0.51);
        Destacker_Right.setPosition(0.51);
        Destack();
        try {
            Thread.sleep(20);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Destacker_Left.setPosition(0.64);
        Destacker_Right.setPosition(0.64);
        Destack();
        try {
            Thread.sleep(20);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Destacker_Left.setPosition(0.85);
        Destacker_Right.setPosition(0.85);
        Destack();

//        line.runOpMode();
//
////        drive.TurnDegrees(-15);
////        drive.StrafeDistance(15,0.75);
////        drive.DriveDistance(-59,0.75);
//
////        sleeve.runOpMode();
//
//        if(sleeve.Pos_1 = true){
//            //Code for cycling and stopping in position 1 goes here
//            drive.DriveDistance(119, .75);
//            try {
//                Thread.sleep(2000);
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//            drive.TurnDegrees(105);
//            try {
//                Thread.sleep(2000);
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//            line.runOpMode();
////            B_grip.Base_Gripper.setPosition(0.45);
////            top.Top_Gripper.setPosition(0);
////            top.Top_Pivot.setPosition(0.4);
////            driv.Top_pole();
////            driv.return_Zero();
////            driv.Destack_5();
//            drive.TurnDegrees(-15);
//            drive.StrafeDistance(15,0.75);
//            drive.DriveDistance(-59,0.75);
//
//        }else if(sleeve.Pos_2 = true){
//            //Code for cycling and stopping in position 2 goes here
//            drive.DriveDistance(119, .75);
//            drive.TurnDegrees(105);
//            line.runOpMode();
//            B_grip.Base_Gripper.setPosition(0.45);
//            top.Top_Gripper.setPosition(0);
//            top.Top_Pivot.setPosition(0.4);
//            slide.Top_pole();
//            slide.return_Zero();
//            slide.Destack_5();
//            drive.TurnDegrees(-15);
//            drive.StrafeDistance(15,0.75);
//
//
//        }else if(sleeve.Pos_3 = true){
//            //Code for cycling and stopping in position 3 goes here
//            drive.DriveDistance(119, .75);
//            drive.TurnDegrees(105);
//            line.runOpMode();
//            B_grip.Base_Gripper.setPosition(0.45);
//            top.Top_Gripper.setPosition(0);
//            top.Top_Pivot.setPosition(0.4);
//            slide.Top_pole();
//            slide.return_Zero();
//            slide.Destack_5();
//            drive.TurnDegrees(-15);
//            drive.StrafeDistance(15,0.75);
//            drive.DriveDistance(59,0.75);
//
//      }
    }
    public void DropPreLoad(){
        //Extend vertical slides and drop cone
        Right_Slide.setTargetPosition(2000);
        Left_Slide.setTargetPosition(2000);
        Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Right_Slide.isBusy() && Left_Slide.isBusy()){
            Right_Slide.setPower(1);
            Left_Slide.setPower(1);
        }
        Right_Slide.setPower(0);
        Left_Slide.setPower(0);

        Top_Pivot.setPosition(0);

        Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        try {
            Thread.sleep(400);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Top_Gripper.setPosition(0.3);

        //TO DO: Insert WHILE loop
        if(Top_Gripper.getPosition() == 0.3) {
            try {
                Thread.sleep(100);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            Top_Pivot.setPosition(0.4);
            Right_Slide.setTargetPosition(0);
            Left_Slide.setTargetPosition(0);
            Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Right_Slide.setPower(-0.9);
            Left_Slide.setPower(-0.9);
            lowering = true;
        }
    }
    public void CheckVSlidePos() {
        if(Right_Slide.getCurrentPosition() < 10 && !Right_Slide.isBusy() && Left_Slide.getCurrentPosition() < 10 && !Left_Slide.isBusy()){
            Right_Slide.setPower(0);
            Left_Slide.setPower(0);

            Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lowering = false;
        }else if(lowering){
            Right_Slide.setPower(-0.9);
            Left_Slide.setPower(-0.9);
        }
    }

    public void Destack() {
        Top_Pivot.setPosition(0.5);
        Base_Pivot.setPosition(0);
        Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Extend.setPower(-1);
        conefound = colour.blue() > 130;

        while(!conefound && Extend.getCurrentPosition() > -1930){
            CheckVSlidePos();
            conefound = colour.blue() > 130;
            Extend.setPower(-1);
        }
        Extend.setPower(0);
        if(conefound) {
            //close gripper
            Base_Gripper.setPosition(0);

            CheckVSlidePos();

            //open top gripper
            Top_Gripper.setPosition(0.3);

            //make sure gripper is closed
            Base_Gripper.setPosition(0);
            try {
                Thread.sleep(100);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            //if the base gripper is closed retract the horizontal slides
            if(Base_Gripper.getPosition() == 0){
                Extend.setTargetPosition(0);
                Extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (Extend.isBusy()) {
                    CheckVSlidePos();
                    Base_Pivot.setPosition(1);
                    Extend.setPower(1);
                }

                Extend.setPower(0);

                //bring destacker down
                Destacker_Left.setPosition(1);
                Destacker_Right.setPosition(1);

                while (lowering) {
                    CheckVSlidePos();
                }

                //open base gripper
                Base_Gripper.setPosition(0.4);
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                Top_Gripper.setPosition(0.35);

                //take top pivot to pick up the cone
                Top_Pivot.setPosition(1);


                if(Base_Pivot.getPosition() > 0.9) {

                    try {
                        Thread.sleep(200);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    //close top gripper
                    Top_Gripper.setPosition(0);

                    try {
                        Thread.sleep(70);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    //take top pivot over
                    Top_Pivot.setPosition(0.5);

                    try {
                        Thread.sleep(25);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    //put base pivot back to zero
                    Base_Pivot.setPosition(0);

                }

            }

            Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            Base_Pivot.setPosition(0);

            //Extend vertical slides and drop cone
            Right_Slide.setTargetPosition(2000);
            Left_Slide.setTargetPosition(2000);
            Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while(Right_Slide.isBusy() && Left_Slide.isBusy()){
                Right_Slide.setPower(1);
                Left_Slide.setPower(1);
            }
            Right_Slide.setPower(0);
            Left_Slide.setPower(0);

            Top_Pivot.setPosition(0);

            Right_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Left_Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            try {
                Thread.sleep(200);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            Top_Gripper.setPosition(0.3);

            //TO DO: Insert WHILE loop
            if(Top_Gripper.getPosition() == 0.3) {
                try {
                    Thread.sleep(150);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                Top_Pivot.setPosition(0.4);
                Right_Slide.setTargetPosition(0);
                Left_Slide.setTargetPosition(0);
                Right_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Left_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Right_Slide.setPower(-0.9);
                Left_Slide.setPower(-0.9);
                lowering = true;
            }

        }else{
            Top_Pivot.setPosition(0.5);
            Extend.setTargetPosition(0);
            Extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (Extend.isBusy()) {
                CheckVSlidePos();
                Extend.setPower(0.8);
            }
            Extend.setPower(0);
            Base_Pivot.setPosition(0);
        }


    }
}
