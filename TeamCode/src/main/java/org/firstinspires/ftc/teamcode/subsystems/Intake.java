package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final CRServo leftIntake;
    private final CRServo rightIntake;
    private final DcMotor middleIntake;

    public Intake(HardwareMap hardwareMap){
        leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        middleIntake = hardwareMap.get(DcMotor.class, "middleIntake");
    }


    /// Run the intake in
    public void in(){
        leftIntake.setPower(1);
        rightIntake.setPower(-1);
        middleIntake.setPower(1);
    }


    /// Turn the intake off
    public void off(){
        leftIntake.setPower(0);
        rightIntake.setPower(0);
        middleIntake.setPower(0);
    }
}
