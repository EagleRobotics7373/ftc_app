package org.firstinspires.ftc.teamcode.AutonomousOpModes;

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

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous_Crater")
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

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Stop and reset Encoders
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Run using Encoders
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Make leftlift and rightlift go down using encoders
        robot.leftlift.setTargetPosition(3100);
        robot.rightlift.setTargetPosition(3100);

        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftlift.setPower(1);
        robot.rightlift.setPower(1);

        while (robot.leftlift.isBusy() && robot.rightlift.isBusy()) {
        }

        methods.ZeroPower();

        // Strafe left 4 inches
        methods.encoderDrive(robot.DRIVE_SPEED, 4, 4, -4, -4);
        // Drive forward 20 inches
        methods.encoderDrive(robot.DRIVE_SPEED, -15, 15, -15, 15);
        // strafe right 15 inches
        methods.strafeRight(15);

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
                        if (updatedRecognitions.size() == 1)
                            for (Recognition recognition : updatedRecognitions) {
                                if (position == "right") {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        telemetry.addLine("Right");
                                        telemetry.update();
                                        // Strafe right 5 inches
                                        methods.strafeRight(5);
                                        // Move forward 10 inches
                                        methods.forward(10);
                                        // Move backwards 10 inches
                                        methods.backward(10);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left x inches
                                        methods.strafeLeft(60);
                                        robot.DRIVE_SPEED = .3;
                                        // Turn 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Move forward 7 inches
                                        methods.forward(7);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left 40 inches
                                        methods.strafeLeft(45);
                                        robot.DRIVE_SPEED = .3;
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Rotate 360 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 46, 46, 46, 46);
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Knock off team marker
                                        robot.servomarker.setPosition(0);
                                        sleep(1000);
                                        robot.DRIVE_SPEED = .5;
                                        methods.strafeLeft(68);
                                        return;
                                    } else
                                        methods.encoderDrive(robot.DRIVE_SPEED, 16, 16, -16, -16);
                                    position = "center";
                                } else if (position == "center") {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        telemetry.addLine("Center");
                                        telemetry.update();
                                        // Strage right 5 inches
                                        methods.strafeRight(5);
                                        // Move forward 10 inches
                                        methods.forward(10);
                                        // Move backwards 10 inches
                                        methods.backward(10);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left x inches
                                        methods.strafeLeft(44);
                                        robot.DRIVE_SPEED = .3;
                                        // Turn 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Move forward 7 inches
                                        methods.forward(7);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left 40 inches
                                        methods.strafeLeft(45);
                                        robot.DRIVE_SPEED = .3;
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Rotate 360 defrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 46, 46, 46, 46);
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Knock off team marker
                                        robot.servomarker.setPosition(0);
                                        sleep(1000);
                                        robot.DRIVE_SPEED = .5;
                                        methods.strafeLeft(68);
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
                                        // Move forward 10 inches
                                        methods.forward(10);
                                        // Move backwards 10 inches
                                        methods.backward(10);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left x inches
                                        methods.strafeLeft(28);
                                        robot.DRIVE_SPEED = .3;
                                        // Turn 45 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11);
                                        // Move forward 7 inches
                                        methods.forward(7);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left 40 inches
                                        methods.strafeLeft(45);
                                        robot.DRIVE_SPEED = .3;
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Rotate 360 degrees
                                        methods.encoderDrive(robot.DRIVE_SPEED, 46, 46, 46, 46);
                                        // Move backwards 5 inches
                                        methods.backward(5);
                                        // Knock team marker off
                                        robot.servomarker.setPosition(0);
                                        sleep(1000);
                                        robot.DRIVE_SPEED = .5;
                                        // Strafe left 68 inches
                                        methods.strafeLeft(68);
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
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
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