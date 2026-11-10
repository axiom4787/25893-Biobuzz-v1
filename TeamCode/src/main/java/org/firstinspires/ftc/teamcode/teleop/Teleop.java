package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
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
        Drivetrain drivetrain = new Drivetrain(hardwareMap);

        prismDriver.setRed();

        waitForStart();

        prismDriver.setGreen();

        // Always run the shooter. This value should be tuned
        shooter.setTargetVelocity(1600);

        while(opModeIsActive()){
            // Intake
            if(gamepad1.left_trigger_pressed) {
                intake.in();
            } else {
                intake.off();
            }

            // Shooter and Transfer
            if(gamepad1.right_trigger_pressed && shooter.isUpToSpeed() && drivetrain.isAlignedToHive()) {
                transfer.in();
            } else {
                transfer.off();
            }

            // Driving
            if (gamepad1.right_trigger_pressed) {
                drivetrain.driveFacingGoalFC(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            } else {
                drivetrain.driveFC(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
            }

            // Update subsystems
            drivetrain.update();
            shooter.update();
        }
    }
}
