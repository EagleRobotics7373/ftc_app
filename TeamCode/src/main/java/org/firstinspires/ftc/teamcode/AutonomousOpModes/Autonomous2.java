package org.firstinspires.ftc.teamcode.AutonomousOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.HardwareRobot;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous2")
//@Disabled
public class Autonomous2 extends LinearOpMode {

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
        /*robot.leftlift.setTargetPosition(3400);
        robot.rightlift.setTargetPosition(3400);

        robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftlift.setPower(1);
        robot.rightlift.setPower(1);

        while (robot.leftlift.isBusy() && robot.rightlift.isBusy()){
        }

        robot.ZeroPower();

        // Strafe left 4 inches
        encoderDrive(robot.DRIVE_SPEED, 4, 4, -4, -4, 2);
        // Go Forward 4 inches
        encoderDrive(robot.DRIVE_SPEED, -4, 4,-4, 4, 2);
        // Strafe right 4 inches
        encoderDrive(robot.DRIVE_SPEED, -4, -4, 4, 4, 2);
        //Drive forward 20.5 inches
        encoderDrive(robot.DRIVE_SPEED, -20, 20, -20, 20, 5);*/

        NormalizedRGBA color_center = robot.CScenter.getNormalizedColors();
        // Move back 2 inches
        encoderDrive(robot.DRIVE_SPEED, 2, -2, 2, -2, 2);

        // Set servo positions to the proper position
        robot.servoleft.setPosition(.655);
        robot.servoright.setPosition(.25);
        sleep(500);

        while (robot.servoleft.getPosition() < .855) {
            robot.servoleft.setPosition(robot.servoleft.getPosition() + .05);
            sleep(100);
        }
        while (robot.servoright.getPosition() > .1) {
            robot.servoright.setPosition(robot.servoright.getPosition() - .05);
            sleep(100);
        }
        sleep(500);

        // Using Color Sensors to compare the red values between all three
        NormalizedRGBA color_left = robot.CSleft.getNormalizedColors();
        NormalizedRGBA color_right = robot.CSright.getNormalizedColors();
        sleep(500);

        robot.servoleft.setPosition(.48);
        robot.servoright.setPosition(.41);

        // Hit left condition
        if (color_left.red < color_center.red  && color_left.red < color_right.red) {
            telemetry.addLine("Hit left");
            telemetry.addData("red center", color_center.red);
            telemetry.addData("red left", color_left.red);
            telemetry.addData("red right", color_right.red);
            telemetry.update();
            sleep(1000);
            // Move backwards 5 inches
            encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 2);
            // Strafe left 15.5 inches
            strafeLeft(15.5, 2);
            // Move forward 28 inches
            encoderDrive(robot.DRIVE_SPEED, -28, 28, -28, 28, 2);
            // Rotate left 45 degrees
            encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 2);
            // robot.servomarker.setPosition();
            strafeLeft(30, 2);
            strafeLeft(30, 2);
            strafeLeft(30, 2);
        }

        // Hit middle condition
        else if (color_center.red < color_left.red && color_center.red < color_right.red) {
            telemetry.addLine("Hit center");
            telemetry.addData("red center", color_center.red);
            telemetry.addData("red left", color_left.red);
            telemetry.addData("red right", color_right.red);
            telemetry.update();
            sleep(1000);
            // Move forward 37 inches
            encoderDrive(robot.DRIVE_SPEED, -37, 37, -37, 37, 2);
            // Rotate Left 45 degrees
            encoderDrive(robot.DRIVE_SPEED, 11, 11, 11, 11, 2);
            // Move Forward 13 inches
            encoderDrive(robot.DRIVE_SPEED, -12, 12, -12, 12, 1);
            // Move backwards 5 inches
            encoderDrive(robot.DRIVE_SPEED, 5, -5, 5,-5, 1);
            sleep(1000);
            // Strafe left 90 inches
            strafeLeft(30, 2);
            strafeLeft(30, 2);
            strafeLeft(30, 2);
        }

        // Hit right condition
        else if (color_right.red < color_center.red && color_right.red < color_left.red) {
            telemetry.addLine("Hit right");
            telemetry.addData("red center", color_center.red);
            telemetry.addData("red left", color_left.red);
            telemetry.addData("red right", color_right.red);
            telemetry.update();
            sleep(1000);
            // Move backwards 5 inches
            encoderDrive(robot.DRIVE_SPEED, 5, -5, 5, -5, 2);
            // Strafe right 15.5 inches
            strafeRight(15.5, 2);
            // Move forward 15 inches
            encoderDrive(robot.DRIVE_SPEED, -28, 28, -28, 28, 2);
            // Rotate right 45 degrees
            encoderDrive(robot.DRIVE_SPEED, -11, -11, -11, -11, 2);
            // robot.servomarker.setPosition();
            strafeRight(30, 2);
            strafeRight(30, 2);
            strafeRight(30, 2);
        }
    }

    // encoderDrive method to make the robot move with input in inches
    public void encoderDrive ( double speed, double frontleftinches, double frontrightinches,
                               double backleftinches, double backrightinches, double timeoutS){

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
    public void strafeLeft(double inches, double timeout){
        encoderDrive(robot.DRIVE_SPEED, inches, inches, -inches, -inches, timeout);
    }
    public void strafeRight(double inches, double timeout){
        encoderDrive(robot.DRIVE_SPEED, -inches, -inches, inches, inches, timeout);
    }
}