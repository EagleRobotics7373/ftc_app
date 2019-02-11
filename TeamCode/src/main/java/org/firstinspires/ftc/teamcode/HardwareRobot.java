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
    public DcMotor intake;
    public DcMotor conveyor;
    public Servo servomarker;
    public Servo servointake;
    // public Servo servopivot;
    public CRServo servopivot;
    public Servo servoscoop;
    public Servo servonudge;

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
        intake = hwMap.get(DcMotor.class, "intake_motor");
        conveyor = hwMap.get(DcMotor.class, "conveyor");
        servomarker = hwMap.get(Servo.class, "servomarker");
        servointake = hwMap.get(Servo.class, "servointake");
        // servopivot = hwMap.get(Servo.class, "servopivot");
        servopivot = hwMap.get(CRServo.class, "servopivot");
        servoscoop = hwMap.get(Servo.class, "servoscoop");
        servonudge = hwMap.get(Servo.class, "servonudge");
    }
}