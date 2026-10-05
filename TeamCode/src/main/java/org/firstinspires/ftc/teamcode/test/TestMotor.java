package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestMotor extends LinearOpMode {
    DcMotor test;

    @Override
    public void runOpMode() throws InterruptedException {
        test = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                test.setPower(1);
            } else if (gamepad1.b) {
                test.setPower(-1);
            } else if (gamepad1.x) {
                test.setPower(0.5);
            } else if (gamepad1.y) {
                test.setPower(-0.5);
            } else {
                test.setPower(0);
            }
        }
    }
}
