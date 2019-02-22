package org.firstinspires.ftc.teamcode.TeleOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.HardwareRobot;
import org.firstinspires.ftc.teamcode.Methods;

@TeleOp(name = "Testing", group = "Concept")
@Disabled
public class Testing extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AeHdjCr/////AAABmVxnuq1NN0iotO8KeuyOVIxb+p8H7X" +
            "/bxZ2FUs7eXhvv4vKcW1eod6wHyu3ZYIHJU0JWD/BoNEi4Kl1POShvtrW9iUEoRqylurPbfs9S+CxH84" +
            "YxYmjnf+F7pxoWYS1V9h/JnW7hIWs2JUEXElL8SoBV9mcOUtOn/hZW+X0TEdCeHNp8jrRSFqcr8UX/+r" +
            "V6GGlYFedJpbmh+v6Ilj2zIeHYErnfCZvZIP7PfcO/sAgGSKQQxVPlvz9kV14trx+8vTBqnN8eSrb8xF" +
            "i/XLU9b+wV7cvpxTPeno9kvoROfH/mEZO/6iZEi89Evd7ZBgqu+NeK4Nfg+oMvcozYkIrTatKqPZ6iY7" +
            "w/YWLKGdib+I98";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    HardwareRobot robot = new HardwareRobot();
    // Methods methods = new Methods(robot);
    String GoldPos = "";
    int SilverMin1X = -1;
    int SilverMin2X = -1;
    int GoldMin = -1;

    int GoldMinLeft;
    int GoldMinRight;
    int GoldMinTop;
    int GoldMinBot;
    int SilverMinLeft;
    int SilverMinRight;
    int SilverMinTop;
    int SilverMinBot;

    @Override
    public void runOpMode() {

        waitForStart();
        initVuforia();
        initTfod();
        recognize();

        /*robot.init(hardwareMap);

        // Stop and reset Encoders
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Run using Encoders
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        initVuforia();
        initTfod();

        waitForStart();
        recognize();

        // Make leftlift and rightlift go down using encoders
        robot.leftlift.setTargetPosition(2800);
        robot.rightlift.setTargetPosition(2800);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftlift.setPower(1);
        robot.rightlift.setPower(1);

        while (robot.leftlift.isBusy() && robot.rightlift.isBusy()) {
        }

        methods.ZeroPower();

        // Strafe left 4 inches
        methods.strafeLeft(4);

        robot.leftlift.setTargetPosition(1400);
        robot.rightlift.setTargetPosition(1400);

        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftlift.setPower(1);
        robot.rightlift.setPower(1);

        while (robot.leftlift.isBusy() && robot.rightlift.isBusy()) {
        }

        // Drive forward 4 inches
        methods.forwardRight(4);

        switch (GoldPos) {
            case "Right":
                // Move forwardright 15 inches
                methods.forwardRight(15);
                // Move forward 22 inches
                methods.forward(22);
                // Rotate Right 135 degrees
                methods.encoderDrive(robot.DRIVE_SPEED, -33, -33, -33, -33);
                // Strafe right 5
                methods.strafeRight(5);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                // Strafe left 70 inches
                methods.strafeLeft(70);
                break;
            case "Center":
                // Move forward 50 inches
                methods.forward(50);
                // Rotate Left 135 degrees
                methods.encoderDrive(robot.DRIVE_SPEED, 33, 33, 33, 33);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                // Strafe left 72 inches
                methods.strafeLeft(72);

                break;
            case "Left":
                // Move forwardleft 15 inches
                methods.forwardLeft(15);
                // Move forward 22 inches
                methods.forward(22);
                // Rotate left 45 degrees
                methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                // Strafe right 5
                methods.strafeRight(5);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                // Strafe left 70 inches
                methods.strafeLeft(70);
                break;
        }*/
    }

    public void recognize() {
        if (opModeIsActive()) {
            if (tfod != null) {
                tfod.activate();
            }
        }

        while (opModeIsActive()) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    for (Recognition recognition : updatedRecognitions) {
                        //if (recognition.getBottom() > 0 && recognition.getTop() > 0 && recognition.getRight() > 0 && recognition.getLeft()) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                /*int GoldMinLeft = (int) recognition.getLeft();
                                int GoldMinRight = (int) recognition.getRight();
                                int GoldMinTop = (int) recognition.getTop();
                                int GoldMinBot = (int) recognition.getBottom();
                                telemetry.addData("GoldMin Left", GoldMinLeft);
                                telemetry.addData("GoldMinRight ", GoldMinRight);
                                telemetry.addData("GoldMinTop", GoldMinTop);
                                telemetry.addData("GoldMinBot", GoldMinBot);*/
                            GoldMin = (int) recognition.getLeft();
                        } else if (SilverMin1X == -1) {
                                    /*int SilverMinLeft = (int) recognition.getLeft();
                                    int SilverMinRight = (int) recognition.getRight();
                                    int SilverMinTop = (int) recognition.getTop();
                                    int SilverMinBot = (int) recognition.getBottom();
                                    telemetry.addData("SilverMinLeft", SilverMinLeft);
                                    telemetry.addData("SilverMinRight ", SilverMinRight);
                                    telemetry.addData("SilverMinTop", SilverMinTop);
                                    telemetry.addData("SilverMinBot", SilverMinBot);*/
                                    SilverMin1X = (int) recognition.getLeft();
                        } else
                            SilverMin2X = (int) recognition.getLeft();
                        //}
                    }
                    if (GoldMin < SilverMin1X) {
                        GoldPos = "Right";
                    } else if (GoldMin > SilverMin1X) {
                        GoldPos = "Center";
                    } else if (SilverMin1X != -1 && SilverMin2X != -1) {
                        GoldPos = "Left";
                    } else {
                        telemetry.addLine("Could not identify gold mineral");
                        telemetry.addData("GoldPos", GoldPos);
                        telemetry.addData("SilverMin1X", SilverMin1X);
                        telemetry.addData("SilverMin2X", SilverMin2X);
                    }
                }
            }
            telemetry.update();
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initVuforia() {

        // Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    // Initialize the Tensor Flow Object Detection engine.
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = .3;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
