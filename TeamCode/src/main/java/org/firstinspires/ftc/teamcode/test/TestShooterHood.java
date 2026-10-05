package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Axon ServoAxon")
public class TestShooterHood extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor shooter = hardwareMap.get(DcMotor.class, "motor");
        Servo hood = hardwareMap.get(Servo.class, "servo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_trigger_pressed) {
                shooter.setPower(1);
            } else {
                shooter.setPower(0);
            }

            if (gamepad1.dpad_up) {
                hood.setPosition(hood.getPosition() + 0.05);
            } else if (gamepad1.dpad_down) {
                hood.setPosition(hood.getPosition() - 0.05);
            } else {
                hood.setPosition(hood.getPosition());
            }
        }
    }
}
