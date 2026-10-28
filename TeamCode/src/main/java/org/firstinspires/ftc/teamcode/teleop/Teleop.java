package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.PrismDriver;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

@TeleOp
public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode(){
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        PrismDriver prismDriver = new PrismDriver(hardwareMap);

        prismDriver.setRed();
        waitForStart();
        prismDriver.setGreen();
        while(opModeIsActive()){
            //intake
            if(gamepad1.left_trigger_pressed){
                intake.in();
            } else {
                intake.off();
            }
            //shooter
            if(gamepad1.right_trigger_pressed){
                shooter.setVelocity(2000);
                if(shooter.upToSpeed()){
                    transfer.in();
                } else {
                    transfer.off();
                }
            } else {
                shooter.setVelocity(0);
            }
            shooter.update();
        }


    }
}
