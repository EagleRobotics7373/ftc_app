package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.HardwareRobot;
import org.firstinspires.ftc.teamcode.Methods;

@Autonomous(name = "Autonomous_Crater")
//@Disabled
public class Autonomous_Crater extends LinearOpMode {
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
    Methods methods = new Methods(robot);
    String GoldPos = "";

    @Override
    public void runOpMode() {

        initVuforia();
        initTfod();

        robot.init(hardwareMap);

        // Stop and reset Encoders
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Run using Encoders
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        GoldPos = "";
        recognize(550, 425, 425, 350);

        // Make leftlift and rightlift go down using encoders
        robot.leftlift.setTargetPosition(3300);
        robot.rightlift.setTargetPosition(3300);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftlift.setPower(1);
        robot.rightlift.setPower(.6);

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

        methods.strafeLeft(3);
        // Drive forward 4 inches
        methods.forward(3);
        methods.strafeRight(6);
        if (GoldPos == "Try Again") {
            recognize(400, 325, 475, 400);
        }
        methods.strafeLeft(3);
        telemetry.addData("GoldPos", GoldPos);

        switch (GoldPos) {
            case "Right":
                // Move forwardright 15 inches
                methods.forwardRight(25);
                // Move forward 10 inches
                methods.forward(8);
                // Move backwards 15 inches
                methods.backward(13);
                // Strafe left 60 inches
                methods.strafeLeft(60);
                // Turn right 135 degrees
                methods.encoderDrive(robot.DRIVE_SPEED, -33, -33, -33, -33);
                methods.backward(9);
                // Strafe right 45 inches
                methods.strafeRight(45);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                methods.strafeLeft(65);
                break;
            case "Center":
                // Move forward 24 inches
                methods.forward(24);
                methods.backward(10);
                methods.strafeLeft(45);
                methods.encoderDrive(robot.DRIVE_SPEED, -33, -33, -33, -33);
                methods.backward(9);
                methods.strafeRight(45);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                methods.strafeLeft(64);
                break;
            case "Left":
                methods.forwardLeft(25);
                methods.forward(8);
                methods.backward(13);
                methods.strafeLeft(30);
                methods.encoderDrive(robot.DRIVE_SPEED, -33, -33, -33, -33);
                methods.backward(8);
                // Strafe right 45 inches
                methods.strafeRight(45);
                // Knock off team marker
                robot.servomarker.setPosition(0);
                sleep(1000);
                methods.strafeLeft(65);
                break;
        }
        telemetry.addData("GoldPos", GoldPos);
        telemetry.update();
    }

    public void recognize(int botconstraint, int topconstraint, int rightconstraint, int leftconstraint) {
        float SilverMin1X = -1;
        float SilverMin2X = -1;
        float GoldMin = -1;

        tfod.activate();
        sleep(1000);
        for (int i = 0; i < 5 & opModeIsActive(); i++) {
            int minFound = 0;
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("size", updatedRecognitions.size());
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getBottom() <= botconstraint & recognition.getTop() >= topconstraint &
                                recognition.getRight() <= rightconstraint & recognition.getLeft() <= leftconstraint) {
                            minFound++;
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                GoldMin = recognition.getLeft();
                            } else if (SilverMin1X == -1) {
                                SilverMin1X = recognition.getLeft();
                            } else
                                SilverMin2X = recognition.getLeft();
                        }
                    }
                    if (GoldMin != -1) {
                        if (GoldMin < SilverMin1X) {
                            GoldPos = "Right";
                            return;
                        } else if (GoldMin > SilverMin1X) {
                            GoldPos = "Center";
                            return;
                        }
                    } else if (SilverMin1X != -1 && SilverMin2X != -1) {
                        GoldPos = "Left";
                        return;
                    } else
                        telemetry.addLine("Could not identify gold mineral");
                    telemetry.update();
                    sleep(200);
                }
            }
            if (minFound == 1) {
                GoldPos = "Try Again";
                return;
            }

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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
