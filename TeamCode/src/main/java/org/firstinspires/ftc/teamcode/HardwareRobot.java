package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareRobot
{
    // Public OpMode members.
    public DcMotor frontleft = null;
    public DcMotor backleft = null;
    public DcMotor frontright = null;
    public DcMotor backright = null;
    public DcMotor rightlift = null;
    public DcMotor leftlift = null;
    public Servo servoleft = null;
    public Servo servoright = null;
    public NormalizedColorSensor CSright = null;
    public NormalizedColorSensor CScenter = null;
    public NormalizedColorSensor CSleft = null;

    /* local OpMode members. */
    HardwareMap hwMap =  null;
    static final double COUNTS_PER_MOTOR_REV = 2240;
    static final double COUNTS_PER_MOTOR_NEVEREST = 280;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH_REV = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);
    public static final double COUNTS_PER_INCH_NEVEREST = (COUNTS_PER_MOTOR_NEVEREST) / (WHEEL_DIAMETER_INCHES * 3.1415);
    public static final double DRIVE_SPEED = 0.6;



    public void ZeroPower(){
        frontleft.setPower(0);
        backleft.setPower(0);
        frontright.setPower(0);
        backright.setPower(0);
        leftlift.setPower(0);
        rightlift.setPower(0);
    }

    /* Constructor */
    public HardwareRobot(){

    }

    /* Initialize standard Hardware interfaces */
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
        servoleft = hwMap.get(Servo.class, "servoleft");
        servoright = hwMap.get(Servo.class, "servoright");
        CSright = hwMap.get(NormalizedColorSensor.class, "CSright");
        CScenter = hwMap.get(NormalizedColorSensor.class, "CScenter");
        CSleft = hwMap.get(NormalizedColorSensor.class, "CSleft");
    }
}