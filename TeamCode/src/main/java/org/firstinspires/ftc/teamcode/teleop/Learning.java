package org.firstinspires.ftc.teamcode.teleop;

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
public class Learning extends LinearOpMode {
//    double p = 20.0;
//    double i = 0.0;
//    double d = 0.0;
//    double f = 12.1;

    int setpoint = 2000;

//    int conf = 0; // p-0, i-1, d-2, f-3, setPoint-4
//
//
//    String[] confs = new String[]{"p", "i", "d", "f", "setPoint"};

    @Override
    public void runOpMode() {
        //bunch of random hardware mapping stuff
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        //shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, i, d, f));

        DcMotor middleIntake = hardwareMap.get(DcMotor.class, "middleIntake");
        CRServo leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        CRServo rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        CRServo transfer = hardwareMap.get(CRServo.class, "transfer");

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();

        ElapsedTime timer = new ElapsedTime();


        while(opModeIsActive()){

            //for intake  (recalibrate to work right (if work wrong))
            if(gamepad1.left_trigger_pressed){
                middleIntake.setPower(1);
                leftIntake.setPower(1);
                rightIntake.setPower(-1);
            }
            else{
                middleIntake.setPower(0);
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            }

            //for shooter
            if(gamepad1.right_trigger_pressed) {
                if(shooter.getVelocity() > setpoint){
                    shooter.setPower(0);
                    transfer.setPower(-1);
                } else if (shooter.getVelocity() == setpoint) {
                    shooter.setPower(setpoint/2000);
                } else {
                    shooter.setPower(1);
                }
            }
            else{
                shooter.setPower(0);
                transfer.setPower(0);
            }

            //for transferer
            /*
            if(gamepad1.a){
                transfer.setPower(-1);
            } else if(gamepad1.x){
                transfer.setPower(1);

            } else{
                transfer.setPower(0);
            }
            */
            //wheel calibration
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;
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


            //configuring PIDF
            if (gamepad1.dpadUpWasPressed())
            {
                setpoint += 100;
            }
            else if (gamepad1.dpadDownWasPressed()) {
                setpoint -= 100;
            }

            telemetry.addData("setpoint ", setpoint);
            telemetry.addData("vel ", shooter.getVelocity());
            telemetry.addData("yaw ", botYaw);
            telemetry.update();

        }
    }
}
