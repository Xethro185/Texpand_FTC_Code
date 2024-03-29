package org.firstinspires.ftc.teamcode.Vision.Pole_Alinement;

import static org.opencv.core.Core.inRange;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2HSV_FULL;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_COMPLEX;
import static org.opencv.imgproc.Imgproc.RETR_TREE;
import static org.opencv.imgproc.Imgproc.boundingRect;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.findContours;
import static org.opencv.imgproc.Imgproc.rectangle;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Vision.Vision_From_Collin.VisionDash;
import org.firstinspires.ftc.teamcode.Vision.Vision_From_Collin.VisionUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class Pole_Vision_Pipeline extends OpenCvPipeline {
    //Initiated variables needed later
    private static int IMG_HEIGHT = 0;
    private static int IMG_WIDTH = 0;
    public Mat output = new Mat(),
            modified = new Mat();
    private ArrayList<MatOfPoint> contours = new ArrayList<>();
    private Mat hierarchy = new Mat();
    static final Rect center = new Rect(new Point(100, 100), new Point(550, 350));
    // Rectangle settings
    private Scalar orange = new Scalar(300, 90, 90);
    private Scalar lightBlue = new Scalar(200, 90, 90);

    public int numcontours;
    public int numrects;

    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private int font = FONT_HERSHEY_COMPLEX;

    public double Distance_To_Travel;

    public static double rectPositionFromLeft = 0;
    public double rectX;
    public double rectY;
    private Rect largestRect;

    private Rect backroundRect1;

    private Rect backroundRect2;
    private List<Rect> rects = new ArrayList<>();
    public static Scalar MIN_THRESH;
    public static Scalar MAX_THRESH;
    public Scalar values;

    @Override
    public Mat processFrame(Mat input) {
        
        MIN_THRESH = new Scalar(VisionDash.pole_min_H,VisionDash.pole_min_S,VisionDash.pole_min_V);
        MAX_THRESH = new Scalar(VisionDash.pole_max_H,VisionDash.pole_max_S,VisionDash.pole_max_V);
        input.copyTo(output);

        IMG_HEIGHT = input.rows();
        IMG_WIDTH = input.cols();

        Imgproc.cvtColor(input, modified, COLOR_RGB2HSV_FULL);
        Imgproc.cvtColor(output, output, COLOR_RGB2HSV_FULL);

        values = Core.mean(modified.submat(center));

        inRange(modified, MIN_THRESH, MAX_THRESH, modified);

        erode(modified, modified, new Mat(VisionDash.erode_const, VisionDash.erode_const, CV_8U));
        dilate(modified, modified, new Mat(VisionDash.dilate_const, VisionDash.dilate_const, CV_8U));

        findContours(modified, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

        for (int i=0; i < contours.size(); i++){
            Rect rect = boundingRect(contours.get(i));
            rects.add(rect);
        }

        numcontours = contours.size();
        if(rects.size() > 0){
            largestRect = VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects).get(0);
            
            rectangle(output, largestRect, lightBlue, 30);

            rectX = largestRect.x + largestRect.width/2;
            rectY = largestRect.y + largestRect.height/2;

            Imgproc.circle(output,new Point(rectX,rectY),50,orange,20);
        }

        modified.release();

        contours.clear();
        output.release();
        return modified;

    }

    public double getRectX() {
        return rectX;
    }

    public double getRectY() {
        return rectY;
    }

    public double TravelDistance(){
        return Distance_To_Travel;
    }

    public double rectPositionFromLeft(){
        return rectPositionFromLeft;
    }
}
