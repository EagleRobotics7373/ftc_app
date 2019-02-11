package org.firstinspires.ftc.teamcode.TeleOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.HardwareRobot;
import org.firstinspires.ftc.teamcode.Methods;

import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous_Cargo_Depot")
//@Disabled
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
    Methods methods = new Methods(robot);

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        initVuforia();
        initTfod();

        recognize();
        tfod.deactivate();
    }

    public void recognize() {
        String position = "right";
        if (opModeIsActive()) {
            // Activate Tensor Flow Object Detection.
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        for (Recognition recognition : updatedRecognitions) {
                            recognition.getLabel().equals(LABEL_GOLD_MINERAL);
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL));
                            if (position == "right") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("Right");
                                    telemetry.update();
                                    sleep(5000);
                                    return;
                                } else
                                    methods.encoderDrive(robot.DRIVE_SPEED, 16, 16, -16, -16);
                                position = "center";
                            } else if (position == "center") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("Center");
                                    telemetry.update();
                                    sleep(5000);
                                    return;
                                } else
                                    methods.encoderDrive(robot.DRIVE_SPEED, 16, 16, -16, -16);
                                position = "left";
                            } else if (position == "left") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("Left");
                                    telemetry.update();
                                    sleep(5000);
                                    return;
                                }
                            } else
                                telemetry.addLine("Could not identify gold mineral");
                        }
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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}