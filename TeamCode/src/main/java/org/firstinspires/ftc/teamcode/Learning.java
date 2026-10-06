package org.firstinspires.ftc.teamcode;

import android.service.autofill.RegexValidator;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class Learning extends LinearOpMode {
    @Override
    public void runOpMode() {
        //bunch of random hardware mapping stuff
        DcMotor shooter = hardwareMap.get(DcMotor.class, "shooter");
        DcMotor intake = hardwareMap.get(DcMotor.class, "intake");
        CRServo transfer = hardwareMap.get(CRServo.class, "transfer");

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();
        while(opModeIsActive()){

            //for intake
            if(gamepad1.left_trigger_pressed){
                intake.setPower(1);
            }
            else{
                intake.setPower(0);
            }

            //for shooter
            if(gamepad1.right_trigger_pressed){
                shooter.setPower(1);
            }
            else{
                shooter.setPower(0);
            }

            //for transferer
            if(gamepad1.a){
                transfer.setPower(-1);
            }
            else{
                transfer.setPower(0);
            }

            //wheel calibration
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double botYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double rotX = x * Math.cos(-botYaw) - y * Math.sin(-botYaw);
            double rotY = x * Math.sin(-botYaw) + y * Math.cos(-botYaw);
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX +rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;
            if(gamepad1.options){
                imu.resetYaw();
            }

            //wheel speed assignment
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

        }
    }
}
