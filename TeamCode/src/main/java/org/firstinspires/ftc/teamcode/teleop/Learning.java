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
import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

@TeleOp
public class Learning extends LinearOpMode {

    int setpoint = 2000;

    boolean colorUpdated = true;

    int goalX = 70;
    int goalY = 70;


    PrismAnimations.Solid green = new PrismAnimations.Solid(Color.GREEN);
    PrismAnimations.Solid red = new PrismAnimations.Solid(Color.RED);
    PrismAnimations.Solid blue = new PrismAnimations.Solid(Color.BLUE);
    PrismAnimations.Solid white = new PrismAnimations.Solid(Color.WHITE);
    PrismAnimations.Blink strobe1 = new PrismAnimations.Blink();
    PrismAnimations.Blink strobe2 = new PrismAnimations.Blink();

//    int conf = 0; // p-0, i-1, d-2, f-3, setPoint-4
//
//
//    String[] confs = new String[]{"p", "i", "d", "f", "setPoint"};

    @Override
    public void runOpMode() {
        //bunch of random hardware mapping stuff
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        DcMotor middleIntake = hardwareMap.get(DcMotor.class, "middleIntake");
        CRServo leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        CRServo rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        CRServo transfer = hardwareMap.get(CRServo.class, "transfer");

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

        GoBildaPrismDriver prismDriver = hardwareMap.get(GoBildaPrismDriver.class, "prismDriver");

        green.setBrightness(50);
        green.setStartIndex(0);
        green.setStopIndex(12);

        blue.setBrightness(50);
        blue.setStartIndex(0);
        blue.setStopIndex(12);

        red.setBrightness(50);
        red.setStartIndex(0);
        red.setStopIndex(12);

        white.setBrightness(50);
        white.setStartIndex(0);
        white.setStopIndex(12);

        strobe1.setBrightness(50);
        strobe1.setStartIndex(0);
        strobe1.setStopIndex(5);
        strobe1.setPrimaryColor(Color.RED);
        strobe1.setSecondaryColor(Color.WHITE);
        strobe1.setPeriod(500, TimeUnit.MILLISECONDS);
        strobe1.setPrimaryColorPeriod(250, TimeUnit.MILLISECONDS);

        strobe2.setBrightness(50);
        strobe2.setStartIndex(6);
        strobe2.setStopIndex(11);
        strobe2.setPrimaryColor(Color.WHITE);
        strobe2.setSecondaryColor(Color.RED);
        strobe2.setPeriod(500, TimeUnit.MILLISECONDS);
        strobe2.setPrimaryColorPeriod(250, TimeUnit.MILLISECONDS);


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, red);

        waitForStart();

        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, green);

        ElapsedTime timer = new ElapsedTime();




        while(opModeIsActive()){



            //for intake  (recalibrate to work right (if work wrong))
            if(gamepad1.leftTriggerWasPressed()){
                prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, blue);
                colorUpdated = true;
            }



            if(gamepad1.left_trigger_pressed){
                middleIntake.setPower(1);
                leftIntake.setPower(1);
                rightIntake.setPower(-1);


            }
            else {
                middleIntake.setPower(0);
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            }
            //for shooter
            if(gamepad1.rightTriggerWasPressed()){
                prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, strobe1);
                prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_1, strobe2);
                colorUpdated = true;
            }
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


            //attempt at making autoalign for shooter


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
            if(!gamepad1.right_trigger_pressed && !gamepad1.left_trigger_pressed && colorUpdated){
                prismDriver.clearAllAnimations();
                prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, green);
                colorUpdated = false;
            }
        }
    }
}
