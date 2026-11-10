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

        // Note: If you don't set the follower's position at the beginning of teleop,
        // it will use the pinpoint to start with the same pose as at auto end
        drivetrain.setPose(8, 141.5-8, 0);

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

            telemetry.addData("Drivetrain", "x=%4.2f y=%4.2f, h=%5.2f", drivetrain.follower().pose().x(), drivetrain.follower().pose().y(), Math.toDegrees(drivetrain.follower().pose().heading()));
            telemetry.addData("Drivetrain - driving mode", gamepad1.right_trigger_pressed ? "Facing goal" : "Normal");
            telemetry.addData("Drivetrain - Target heading", Math.toDegrees(drivetrain.getTargetHeading()));
            telemetry.addData("Drivetrain - heading power", drivetrain.power);
            telemetry.addData("Shooter - Velocity", shooter.getVelocity());
            telemetry.addData("Shooter - Is up to speed", shooter.isUpToSpeed());
            telemetry.addData("Transfer - is running", gamepad1.right_trigger_pressed && shooter.isUpToSpeed() && drivetrain.isAlignedToHive());
            telemetry.update();
        }
    }
}
