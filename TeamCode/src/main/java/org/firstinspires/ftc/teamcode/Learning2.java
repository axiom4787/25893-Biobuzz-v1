package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class Learning2 extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        DcMotor backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setDirection((DcMotorSimple.Direction.REVERSE));
        CRServo leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        DcMotor middleIntake = hardwareMap.get(DcMotor.class, "middleIntake");
        CRServo rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        CRServo transfer = hardwareMap.get(CRServo.class, "transfer");

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_trigger_pressed) {
                middleIntake.setPower(1);
                leftIntake.setPower(1);
                rightIntake.setPower(-1);
            }
            else {
                middleIntake.setPower(0);
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            }

            if (gamepad1.right_trigger_pressed) {
                shooter.setPower(1);
            }
            else {
                shooter.setPower(0);
            }

            if (gamepad1.x) {
                transfer.setPower(1);
            }
            else {
                transfer.setPower(0);
            }
            //wheel calibration
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad1.options) {
                imu.resetYaw();
            }


        }

    }
}