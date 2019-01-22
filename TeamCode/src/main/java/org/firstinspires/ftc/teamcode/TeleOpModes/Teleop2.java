package org.firstinspires.ftc.teamcode.TeleOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.HardwareRobot;
import org.firstinspires.ftc.teamcode.Methods;

@TeleOp(name="Teleop2")
//@Disabled
public class Teleop2 extends LinearOpMode {

    // Declare OpMode members.
    HardwareRobot robot = new HardwareRobot();
    Methods methods = new Methods(robot);
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        int liftSwitch = 0;
        int directionSwitch = 0;
        int powerFactor = 1;
        int intakeTarget = 0;

        robot.DRIVE_SPEED = .2;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.leftlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Y button changes lifts to running with both sticks or with just one
            if (gamepad2.right_stick_y > .1 | gamepad2.right_stick_y < .1 | gamepad2.left_stick_y > .1 | gamepad2.left_stick_y < .1) {
                if (liftSwitch % 2 == 0) {
                    int liftControl = (int) (-gamepad2.right_stick_y * 150);

                    robot.rightlift.setTargetPosition(robot.rightlift.getCurrentPosition() + liftControl);
                    robot.leftlift.setTargetPosition(robot.leftlift.getCurrentPosition() + liftControl);

                    robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.rightlift.setPower(1);
                    robot.leftlift.setPower(1);
                }
                else if (liftSwitch % 2 == 1) {
                    int liftControlright = (int) (-gamepad2.right_stick_y * 150);
                    int liftControlleft = (int) (-gamepad2.left_stick_y * 150);

                    robot.rightlift.setTargetPosition(robot.rightlift.getCurrentPosition() + liftControlright);
                    robot.leftlift.setTargetPosition(robot.leftlift.getCurrentPosition() + liftControlleft);

                    robot.rightlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.leftlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.rightlift.setPower(1);
                    robot.leftlift.setPower(1);
                }
            }

            if (directionSwitch % 2 == 0){
                double x = gamepad1.left_stick_x;
                double y = gamepad1.left_stick_y;
                double z = gamepad1.right_stick_x;

                robot.frontleft.setPower(powerFactor * (y - x - z));
                robot.backleft.setPower(powerFactor * (y + x - z));
                robot.frontright.setPower(powerFactor * (-y - x - z));
                robot.backright.setPower(powerFactor * (-y + x - z));
            }
            else if (directionSwitch % 2 == 1){
                double x = -gamepad1.left_stick_x;
                double y = -gamepad1.left_stick_y;
                double z = gamepad1.right_stick_x;

                robot.frontleft.setPower(powerFactor * (y - x - z));
                robot.backleft.setPower(powerFactor * (y + x - z));
                robot.frontright.setPower(powerFactor * (-y - x - z));
                robot.backright.setPower(powerFactor * (-y + x - z));
            }

            if (gamepad2.left_stick_y > .1) {
                intakeTarget = robot.intake.getCurrentPosition() + (int) -(gamepad2.left_stick_y * 36);
            }
            else if (gamepad2.left_stick_y < -.1) {
                intakeTarget = robot.intake.getCurrentPosition() + (int) -(gamepad2.left_stick_y * 9);
            }

            // robot.intake.setTargetPosition(intakeTarget);
            // robot.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // robot.intake.setPower(1);

            if (gamepad2.y) {
                ++liftSwitch;
            }
            else if (gamepad1.y) {
                ++directionSwitch;
            }
            else if (gamepad1.right_trigger > 0) {
                robot.conveyor.setPower(1);
            }
            else if (gamepad1.left_trigger > 0) {
                robot.conveyor.setPower(-1);
            }
            else if (gamepad2.x) {
                robot.servointake.setPosition(0);
            }
            else if (gamepad2.a) {
                robot.servointake.setPosition(.1);
            }
            else if (gamepad2.b) {
                robot.servointake.setPosition(.05);
            }
            else if (gamepad2.right_trigger > 0) {
                robot.servopivot.setPower(0);
            }
            else if (gamepad2.left_trigger > 0) {
                robot.servopivot.setPower(1);
            }
            else if (gamepad1.x) {
                robot.servoscoop.setPosition(0);
            }
            else if (gamepad1.b) {
                robot.servoscoop.setPosition(.85);
            }
            else {
                robot.conveyor.setPower(0);
                robot.servopivot.setPower(.5);
            }

            // Telemetry Data
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("leftlift", robot.leftlift.getCurrentPosition());
            telemetry.addData("rightlift", robot.rightlift.getCurrentPosition());
            telemetry.addData("intake", robot.intake.getCurrentPosition());
            telemetry.addData("gamepad2 Position", gamepad2.left_stick_y);
            telemetry.update();
        }
    }
}