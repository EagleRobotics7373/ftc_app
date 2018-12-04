package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class Methods extends LinearOpMode{
    HardwareRobot robot = new HardwareRobot();

    public void ZeroPower(){
        robot.frontleft.setPower(0);
        robot.backleft.setPower(0);
        robot.frontright.setPower(0);
        robot.backright.setPower(0);
        robot.leftlift.setPower(0);
        robot.rightlift.setPower(0);
    }

    // encoderDrive method to make the robot move with input in inches
    public void encoderDrive(double speedFL, double speedFR, double speedBL, double speedBR,
                             double frontleftinches, double frontrightinches,
                             double backleftinches, double backrightinches) {

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

            robot.frontleft.setPower(speedFL);
            robot.backleft.setPower(speedBL);
            robot.frontright.setPower(speedFR);
            robot.backright.setPower(speedBR);

            while (opModeIsActive() && (robot.frontleft.isBusy() && robot.frontright.isBusy() &&
                    robot.backleft.isBusy() && robot.backright.isBusy())) {
            }

            // Use the ZeroPower method to stop all motion
            ZeroPower();

            robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void encoderDriveSame(double power, double frontleftinches,
                                 double frontrightinches, double backleftinches,
                                 double backrightinches){
        encoderDrive(power, power, power, power, frontleftinches, frontrightinches, backleftinches, backrightinches);
    }

    public void strafeLeft(double inches) {
        encoderDriveSame(robot.DRIVE_SPEED, inches, inches, -inches, -inches);
    }

    public void strafeRight(double inches) {
        encoderDriveSame(robot.DRIVE_SPEED, -inches, -inches, inches, inches);
    }
    public void coordinateDrive(double x, double y, double power){
        // double resultant = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan(x/y);

        if (x > 0 && y == 0) {
            strafeRight(x);
        } else if (theta > 0 && theta < 90){
            encoderDrive(Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)),
                    Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)), -y, -x, -y, x);
        } else if (y > 0 && x == 0){
            encoderDriveSame(power, -y, y, -y, y);
        } else  if (theta > 90 && theta < 180){
            encoderDrive(Math.abs(Math.cos(theta)), Math.abs(Math.sin(theta)),
                    Math.abs(Math.cos(theta)), Math.abs(Math.sin(theta)), -x, y, x, y);
        } else if (x < 0 && y == 0){
            strafeLeft(-x);
        } else if (theta > 180 && theta < 270){
            encoderDrive(Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)),
                    Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)), -x, y, x, y);
        } else if (y < 0 && x == 0){
            encoderDriveSame(power, -y, y, -y, y);
        } else if (theta > 270 && theta < 360){
            encoderDrive(Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)),
                    Math.abs(Math.sin(theta)), Math.abs(Math.cos(theta)), -y, -x, -y, x);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
