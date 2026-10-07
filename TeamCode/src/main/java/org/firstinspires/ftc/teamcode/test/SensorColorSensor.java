package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name = "Sensor ColorSensor: Sensor ColorSensor")
public class SensorColorSensor extends LinearOpMode {
    double maxRedOutput = 0;
    double maxGreenOutput = 0;
    @Override
    public void runOpMode() {
        DcMotor shooter = hardwareMap.get(DcMotor.class, "motor");
        ColorSensor sensorColorSensor = hardwareMap.get(ColorSensor.class, "color");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_trigger_pressed) {
                shooter.setPower(1);
            } else {
                shooter.setPower(0);
            }
            maxRedOutput = Math.max(maxRedOutput, sensorColorSensor.red());
            maxGreenOutput = Math.max(maxGreenOutput, sensorColorSensor.green());
            telemetry.addLine("Max values");
            telemetry.addData("Red: ", maxRedOutput);
            telemetry.addData("Green: ", maxGreenOutput);
            telemetry.update();
        }
    }
}
