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
    String GoldPos = "";

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Stop and reset Encoders
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Run using Encoders
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        initVuforia();
        initTfod();

        waitForStart();
        tfod.deactivate();

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

        // Drive forward 5 inches
        methods.forward(5);

        switch (GoldPos) {
            case "Right":
                methods.forwardRight(5);
                break;
            case "Center":
                methods.forward(10);
                break;
            case "Left":
                methods.forwardLeft(5);
                break;
        }
    }

    public void recognize() {
        tfod.activate();
        while (opModeIsActive()) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    if (updatedRecognitions.size() == 2) {
                        int SilverMin = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                GoldMin = (int) recognition.getLeft();
                            } else if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                                SilverMin = (int) recognition.getLeft();
                            }
                            if (GoldMin > SilverMin)
                                if (position == "right") {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        telemetry.addLine("Right");
                                        telemetry.update();
                                        robot.DRIVE_SPEED = .7;
                                        // Strafe right 5 inches
                                        methods.strafeRight(5);
                                        // Move forward 30 inches
                                        methods.forward(30);
                                        // Rotate Left 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Move forward 10 inches
                                        methods.forward(10);
                                        // Rotate left 90 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 22, 22, 22, 22);
                                        methods.backward(5);
                                        // Knock team marker off
                                        robot.servomarker.setPosition(0);
                                        sleep(2000);
                                        methods.strafeRight(10);
                                        methods.forward(15);
                                        // Strafe left 70 inches
                                        // methods.strafeLeft(70);
                                        return;
                                    } else
                                        methods.encoderDrive(robot.DRIVE_SPEED, 16, 16, -16, -16);
                                    position = "center";
                                } else if (position == "center") {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        telemetry.addLine("Center");
                                        telemetry.update();
                                        // Strafe right 5 inches
                                        methods.strafeRight(5);
                                        robot.DRIVE_SPEED = .7;
                                        // Move forward 37 inches
                                        methods.forward(37);
                                        // Rotate Left 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Knock off team marker
                                        robot.servomarker.setPosition(0);
                                        sleep(1000);
                                        methods.forward(5);
                                        methods.strafeLeft(5);
                                        return;
                                    } else
                                        methods.encoderDrive(robot.DRIVE_SPEED, 16, 16, -16, -16);
                                    position = "left";
                                } else if (position == "left") {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        telemetry.addLine("Left");
                                        telemetry.update();
                                        // Strafe right 5 inches
                                        methods.strafeRight(5);
                                        robot.DRIVE_SPEED = .7;
                                        // Move forward 28 inches
                                        methods.forward(28);
                                        // Rotate left 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Strafe right 10 inches
                                        methods.strafeRight(10);
                                        // Knock off team marker
                                        robot.servomarker.setPosition(0);
                                        sleep(1000);
                                        methods.forward(5);
                                        methods.strafeLeft(5);
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
