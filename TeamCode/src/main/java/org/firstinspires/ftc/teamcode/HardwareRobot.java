package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareRobot {
    // Public OpMode members.
    public DcMotor frontleft;
    public DcMotor backleft;
    public DcMotor frontright;
    public DcMotor backright;
    public DcMotor rightlift;
    public DcMotor leftlift;
    public DcMotor extensionlift;
    public DcMotor extension;
    public Servo servomarker;
    public CRServo servointake;

    // Converting encoder count to inches
    HardwareMap hwMap =  null;
    static final double COUNTS_PER_MOTOR_REV = 1120;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH_REV = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);
    public static double DRIVE_SPEED = 0.3;

    // Constructor
    public HardwareRobot(){
    }

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontleft = hwMap.get(DcMotor.class, "frontleft");
        backleft = hwMap.get(DcMotor.class, "backleft");
        frontright = hwMap.get(DcMotor.class, "frontright");
        backright = hwMap.get(DcMotor.class, "backright");
        rightlift = hwMap.get(DcMotor.class, "rightlift");
        leftlift = hwMap.get(DcMotor.class, "leftlift");
        extensionlift = hwMap.get(DcMotor.class, "extensionlift");
        extension = hwMap.get(DcMotor.class, "extension");
        servomarker = hwMap.get(Servo.class, "servomarker");
        servointake = hwMap.get(CRServo.class, "servointake");
    }
}