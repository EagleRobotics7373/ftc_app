package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.comp.Flow;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.HardwareRobot;

import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous2")
//@Disabled
public class Autonomous2 extends LinearOpMode {
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
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Stop and reset Encoders
        robot.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Run using Encoders
        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Make leftlift and rightlift go down using encoders
        /*robot.leftlift.setTargetPosition(3100);
        robot.rightlift.setTargetPosition(3100);

        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftlift.setPower(1);
        robot.rightlift.setPower(1);

        while (robot.leftlift.isBusy() && robot.rightlift.isBusy()) {
        }

        robot.ZeroPower();

        // Strafe left 4 inches
        encoderDrive(robot.DRIVE_SPEED, 4, 4, -4, -4, 2);
        // Go Forward 4 inches
        encoderDrive(robot.DRIVE_SPEED, -4, 4, -4, 4, 2);
        // Strafe right 5 inches
        encoderDrive(robot.DRIVE_SPEED, -5, -5, 5, 5, 2);
        //Drive forward 20 inches
        encoderDrive(robot.DRIVE_SPEED, -19, 19, -19, 19, 5);*/

        // encoderDrive(robot.DRIVE_SPEED, -7, 7, -7, 7, 5);
        // encoderDrive(robot.DRIVE_SPEED, -8, -8, 8, 8, 5);

        String position = "right";

        initVuforia();
        initTfod();

        if (opModeIsActive()) {
            // Activate Tensor Flow Object Detection.
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions.size() == 1)
                        for (Recognition recognition : updatedRecognitions) {
                            if (position == "right") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("Right");
                                } else
                                    tfod.deactivate();
                                encoderDrive(robot.DRIVE_SPEED, 15.5, 15.5,
                                        -15.5, -15.5, 2);
                                position = "center";
                            } else if (position == "center") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("Center");
                                } else
                                    tfod.deactivate();
                                encoderDrive(robot.DRIVE_SPEED, 15.5, 15.5,
                                        -15.5, -15.5, 2);
                                position = "left";
                            } else if (position == "left") {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addLine("left");
                                }
                            } else
                                telemetry.addLine("Could not identify gold mineral");
                        }
                            /*if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    // Move backwards 5 inches
                                    encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 2);
                                    // Strafe left 15.5 inches
                                    strafeLeft(15.5, 2);
                                    encoderDrive(robot.DRIVE_SPEED, -10, 10, -10, 10, 2);
                                    // Move forward 28 inches
                                    encoderDrive(robot.DRIVE_SPEED, -28, 28, -28, 28, 2);
                                    // Rotate left 45 degrees
                                    encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 2);
                                    strafeRight(10, 2);
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    // Move backwards 5 inches
                                    encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 2);
                                    // Strafe right 15.5 inches
                                    strafeRight(15.5, 2);
                                    encoderDrive(robot.DRIVE_SPEED, -10, 10, -10, 10, 2);
                                    // Move forward 15 inches
                                    encoderDrive(robot.DRIVE_SPEED, -28, 28, -28, 28, 2);
                                    // Rotate left 180 degrees
                                    encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 1);
                                    encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 1);
                                    encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 1);
                                    encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 2);
                                    strafeRight(10, 2);
                                    robot.servomarker.setPosition(0);
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    encoderDrive(robot.DRIVE_SPEED, -10, 10, -10, 10, 2);
                                    // Move forward 37 inches
                                    encoderDrive(robot.DRIVE_SPEED, -37, 37, -37, 37, 2);
                                    // Rotate Left 45 degrees
                                    encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 2);
                                    robot.servomarker.setPosition(0);
                                    // Move Forward 13 inches
                                    encoderDrive(robot.DRIVE_SPEED, -16, 16, -16, 16, 1);
                                    // Move backwards 5 inches
                                    encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 1);
                                    sleep(1000);
                                }*/
                }
            }
            telemetry.update();
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    // encoderDrive method to make the robot move with input in inches
    public void encoderDrive(double speed, double frontleftinches, double frontrightinches,
                             double backleftinches, double backrightinches, double timeoutS) {

        int frontleftTarget;
        int backleftTarget;
        int frontrightTarget;
        int backrightTarget;

        if (opModeIsActive()) {

            robot.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            frontleftTarget = robot.frontleft.getCurrentPosition() + (int) (frontleftinches * robot.COUNTS_PER_INCH_REV);
            backleftTarget = robot.backleft.getCurrentPosition() + (int) (backleftinches * robot.COUNTS_PER_INCH_REV);
            frontrightTarget = robot.frontright.getCurrentPosition() + (int) (frontrightinches * robot.COUNTS_PER_INCH_REV);
            backrightTarget = robot.backright.getCurrentPosition() + (int) (backrightinches * robot.COUNTS_PER_INCH_REV);

            robot.frontleft.setTargetPosition(frontleftTarget);
            robot.backleft.setTargetPosition(backleftTarget);
            robot.frontright.setTargetPosition(frontrightTarget);
            robot.backright.setTargetPosition(backrightTarget);

            robot.frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.frontleft.setPower(speed);
            robot.backleft.setPower(speed);
            robot.frontright.setPower(speed);
            robot.backright.setPower(speed);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontleft.isBusy() && robot.frontright.isBusy())) {
            }

            // Use the ZeroPower method to stop all motion
            robot.ZeroPower();

            robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void strafeLeft(double inches, double timeout) {
        encoderDrive(robot.DRIVE_SPEED, inches, inches, -inches, -inches, timeout);
    }

    public void strafeRight(double inches, double timeout) {
        encoderDrive(robot.DRIVE_SPEED, -inches, -inches, inches, inches, timeout);
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