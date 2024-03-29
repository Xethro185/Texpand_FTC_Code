package org.firstinspires.ftc.teamcode.Vision.Vision_From_Collin;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Sub_Systems.Drivetrain;
import org.firstinspires.ftc.teamcode.Vision.Cone_Alignment.TestPipelines.Cone_Stack_Pipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;


@Autonomous
@Disabled
public class Color_Feedback extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    Drivetrain drive = new Drivetrain();
    Cone_Stack_Pipeline colin = new Cone_Stack_Pipeline();

    private OpenCvCamera webcam;
    private OpenCvPipeline pipe = new Collin_Code();

    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        // Start streaming
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() { webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT); }
            @Override
            public void onError(int errorCode) { }
        });

        // Set the pipeline depending on id
        webcam.setPipeline(pipe);

    }


    @Override
    public void init_loop() {
        telemetry.addData("rect X", colin.getRectX());
        telemetry.addData("rect Y", colin.getRectY());
        telemetry.addData("Drive to target ", colin.TravelDistance());
        telemetry.addData("num contours", colin.numcontours);
        telemetry.addData("num rects", colin.numrects);
        telemetry.addData("HSV values", colin.values);
        telemetry.update();

    }


    @Override
    public void start() {

    }


    @Override
    public void loop() {

        if (colin.TravelDistance() > 0){
            drive.StrafeDistance_Left(colin.TravelDistance(), 0.5);
        }

        if (colin.TravelDistance() < 0){
            drive.StrafeDistance(colin.TravelDistance(), 0.5);
        }


        telemetry.addData("Drive to target ", colin.TravelDistance());
        telemetry.addData("X Position", colin.rectPositionFromLeft());
        telemetry.addData("num contours", colin.numcontours);
        telemetry.addData("num rects", colin.numrects);
        telemetry.addData("HSV values", colin.values);
        telemetry.update();

        loopTelemetry();
    }

    //Telemetry to be displayed during init_loop()

    private void initTelemetry(){
        telemetry.addData("Status", "InitLoop");
        telemetry.update();
    }

    //Telemetry to be displayed during loop()

    private void loopTelemetry(){
        telemetry.addData("Status", "TeleOp Running");
        telemetry.update();
    }
}