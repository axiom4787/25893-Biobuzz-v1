package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Utility(name = "Servo Axon ServoAxon")
public class TestShooterHood extends LinearOpMode {
    @Override
    public void runOpMode() {
        double servo_position = 0f;
        DcMotor shooter = hardwareMap.get(DcMotor.class, "motor");
        Servo hood = hardwareMap.get(Servo.class, "servo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_trigger_pressed) {
                shooter.setPower(1f);
            } else {
                shooter.setPower(0f);
            }

            hood.setPosition(servo_position);
            if (gamepad1.dpadUpWasPressed()) {
                if (servo_position < 1.0) {
                    servo_position += 0.05;
                } else {
                    servo_position = 1f;
                }
            } else if (gamepad1.dpadDownWasPressed()) {
                if (servo_position > -1.0) {
                    servo_position -= 0.05;
                } else {
                    servo_position = -1f;
                }
            }
            telemetry.addData("servo_position", servo_position);
            telemetry.update();
        }
    }
}
