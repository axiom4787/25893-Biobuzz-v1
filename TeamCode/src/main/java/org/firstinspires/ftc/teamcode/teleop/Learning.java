package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.utils.Angle;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.PrismDriver;


@TeleOp
public class Learning extends LinearOpMode {

    int setpoint = 2000;
    boolean current = true;
    boolean changePID = true;

    double p = 1;
    double d = 0;

    boolean colorUpdated = true;

    int goalX = 70;
    int goalY = 70;


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

        PrismDriver prismDriver = new PrismDriver(hardwareMap);

        Follower follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(85, 8, Math.PI/2));

        Controller controller = Controller.pid(p, 0, d);

        prismDriver.setRed();

        waitForStart();

        prismDriver.setGreen();

        ElapsedTime timer = new ElapsedTime();




        while(opModeIsActive()){
            follower.update();


            //for intake  (recalibrate to work right (if work wrong))
            if(gamepad1.leftTriggerWasPressed()){
                prismDriver.setBlue();
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
                prismDriver.setStrobe();
                colorUpdated = true;
            }
            if(gamepad1.right_trigger_pressed) {
                Pose goalPose = new Pose(58, 84);
                double dx = follower.pose().x() - goalPose.x();
                double dy = follower.pose().y() - goalPose.y();
                double goalAngle = Math.atan2(dy, dx);
                double robotAngle = follower.pose().heading();
                double error = Angle.normalizeSigned(goalAngle - robotAngle);
                double out = controller.calculate(goalAngle, error);
                follower.manual(ManualDrive.fieldCentric(-gamepad1.left_stick_y, -gamepad1.left_stick_x, out, follower.pose().heading()));
                if(shooter.getVelocity() > setpoint){
                    shooter.setPower(0);
                    transfer.setPower(-1);
                } else if (shooter.getVelocity() == setpoint) {
                    shooter.setPower(setpoint/2000.0);
                } else {
                    shooter.setPower(1);
                }
            }
            else{
                follower.manual(ManualDrive.fieldCentric(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, follower.pose().heading()));
                shooter.setPower(0);
                transfer.setPower(0);
            }


            //attempt at making autoalign for shooter

//            if (gamepad1.dpadRightWasPressed()){
//                if (current){
//                    current = false;
//                }
//                else {
//                    current = true;
//                }
//            }
//
//            //configuring PID
//            if (current) {
//                if (gamepad1.dpadUpWasPressed()) {
//                    p += 0.01;
//                    changePID = false;
//                } else if (gamepad1.dpadDownWasPressed()) {
//                    p -= 0.01;
//                    changePID = false;
//                }
//            }
//            else{
//                if (gamepad1.dpadUpWasPressed()) {
//                    d += 0.001;
//                    changePID = false;
//                } else if (gamepad1.dpadDownWasPressed()) {
//                    d -= 0.001;
//                    changePID = false;
//                }
//            }
//            if (!changePID){
//                controller = Controller.pid(p, 0, d);
//            }
//
//            telemetry.addData("d ", d);
//            telemetry.addData("p ", p);
//            telemetry.addData("current (p=true)", current);
            telemetry.addData("robot pos ", follower.pose());
            telemetry.update();



            if(!gamepad1.right_trigger_pressed && !gamepad1.left_trigger_pressed && colorUpdated){
                prismDriver.setGreen();
                colorUpdated = false;
            }
        }
    }
}
