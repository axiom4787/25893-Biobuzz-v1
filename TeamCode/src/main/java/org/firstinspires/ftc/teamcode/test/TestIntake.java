package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Test: intake")
public class TestIntake extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor intake = hardwareMap.get(DcMotor.class, "intake");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_trigger_pressed) {
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }
        }
    }
}
