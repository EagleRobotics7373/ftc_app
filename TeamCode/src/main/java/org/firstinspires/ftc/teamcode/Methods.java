package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

// @Disabled
public class Methods{
    HardwareRobot robot;

    public Methods(HardwareRobot robot) {
        this.robot = robot;
    }

    public void ZeroPower(){
        robot.frontleft.setPower(0);
        robot.backleft.setPower(0);
        robot.frontright.setPower(0);
        robot.backright.setPower(0);
        robot.leftlift.setPower(0);
        robot.rightlift.setPower(0);
        robot.intake.setPower(0);
    }

    // encoderDrive method to make the robot move with input in inches
    public void encoderDrive(double speed, double frontleftinches, double frontrightinches,
                             double backleftinches, double backrightinches) {

        int frontleftTarget;
        int backleftTarget;
        int frontrightTarget;
        int backrightTarget;

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

        robot.frontleft.setPower(speed);
        robot.backleft.setPower(speed);
        robot.frontright.setPower(speed);
        robot.backright.setPower(speed);

        while (robot.frontleft.isBusy() && robot.frontright.isBusy() &&
                robot.backleft.isBusy() && robot.backright.isBusy()) {
        }

        // Use the ZeroPower method to stop all motion
        ZeroPower();

        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void forward(double inches) {
        encoderDrive(robot.DRIVE_SPEED, -inches, inches, -inches, inches);
    }

    public void backward(double inches) {
        encoderDrive(robot.DRIVE_SPEED, inches, -inches, inches, -inches);
    }

    public void strafeLeft(double inches) {
        encoderDrive(robot.DRIVE_SPEED, inches, inches, -inches, -inches);
    }

    public void strafeRight(double inches) {
        encoderDrive(robot.DRIVE_SPEED, -inches, -inches, inches, inches);
    }

    public void forwardRight(double inches) {
        double hypotenuse = Math.sqrt(Math.pow(inches, 2) + Math.pow(inches, 2));

        int frontleftTarget;
        int backrightTarget;

        robot.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontleftTarget = -(robot.frontleft.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV));
        backrightTarget = robot.backright.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV);

        robot.frontleft.setTargetPosition(frontleftTarget);
        robot.backright.setTargetPosition(backrightTarget);

        robot.frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontleft.setPower(-robot.DRIVE_SPEED);
        robot.backright.setPower(robot.DRIVE_SPEED);

        while (robot.frontleft.isBusy() && robot.backright.isBusy()) {
        }

        ZeroPower();

        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void forwardLeft(double inches) {
        double hypotenuse = Math.sqrt(Math.pow(inches, 2) + Math.pow(inches, 2));

        int frontrightTarget;
        int backleftTarget;

        robot.frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontrightTarget = robot.frontright.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV);
        backleftTarget = -(robot.backleft.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV));

        robot.frontright.setTargetPosition(frontrightTarget);
        robot.backleft.setTargetPosition(backleftTarget);

        robot.frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontright.setPower(robot.DRIVE_SPEED);
        robot.backleft.setPower(-robot.DRIVE_SPEED);

        while (robot.frontright.isBusy() && robot.backleft.isBusy()) {
        }

        ZeroPower();

        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void backRight(double inches) {
        double hypotenuse = Math.sqrt(Math.pow(inches, 2) + Math.pow(inches, 2));
        int frontrightTarget;
        int backleftTarget;

        robot.frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontrightTarget = -(robot.frontright.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV));
        backleftTarget = robot.backleft.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV);

        robot.frontright.setTargetPosition(frontrightTarget);
        robot.backleft.setTargetPosition(backleftTarget);

        robot.frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontright.setPower(-robot.DRIVE_SPEED);
        robot.backleft.setPower(robot.DRIVE_SPEED);

        while (robot.frontright.isBusy() && robot.backleft.isBusy()) {
        }

        ZeroPower();

        robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void backLeft(double inches) {
        double hypotenuse = Math.sqrt(Math.pow(inches, 2) + Math.pow(inches, 2));

        int frontleftTarget;
        int backrightTarget;

        robot.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontleftTarget = robot.frontleft.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV);
        backrightTarget = -(robot.backright.getCurrentPosition() + (int) (hypotenuse * robot.COUNTS_PER_INCH_REV));

        robot.frontleft.setTargetPosition(frontleftTarget);
        robot.backright.setTargetPosition(backrightTarget);

        robot.frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontleft.setPower(robot.DRIVE_SPEED);
        robot.backright.setPower(-robot.DRIVE_SPEED);

        while (robot.frontleft.isBusy() && robot.backright.isBusy()) {
        }

        ZeroPower();

        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void intake(double intake_inches) {
        int intakeTarget;

        robot.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeTarget = robot.frontleft.getCurrentPosition() + (int) (intake_inches);
        robot.intake.setTargetPosition(intakeTarget);
        robot.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.intake.setPower(.75);
        while (robot.intake.isBusy()) {
        }
        ZeroPower();
        robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}