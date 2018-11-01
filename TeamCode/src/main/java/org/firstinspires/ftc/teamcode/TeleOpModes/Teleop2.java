package org.firstinspires.ftc.teamcode.TeleOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareRobot;

@TeleOp(name="Teleop2")
//@Disabled
public class Teleop2 extends LinearOpMode {

    // Declare OpMode members.
    HardwareRobot robot = new HardwareRobot();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        int liftControl = 0;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double z = gamepad1.right_stick_x;

            robot.frontleft.setPower(y - x - z);
            robot.backleft.setPower(y + x - z);
            robot.frontright.setPower(-y - x - z);
            robot.backright.setPower(-y + x - z);

            robot.rightlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftlift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Y button changes lifts to running with both sticks or with just one
            if (liftControl % 2 == 0) {
                robot.rightlift.setPower(gamepad2.right_stick_y);
                robot.leftlift.setPower(gamepad2.right_stick_y);
            }

            else if (liftControl % 2 == 1) {
                robot.rightlift.setPower(gamepad2.right_stick_y);
                robot.leftlift.setPower(gamepad2.left_stick_y);
            }

            if (gamepad2.y) {
                ++liftControl;}

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}