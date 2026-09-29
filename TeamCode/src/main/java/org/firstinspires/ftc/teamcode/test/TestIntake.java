package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Test: intake")
public class TestIntake extends LinearOpMode {
    private HuskyLens huskyLens;

    @Override
    public void runOpMode() {
        DcMotor intake = hardwareMap.get(DcMotor.class, "intake");
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_trigger_pressed && should_intake()) {
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }
        }
    }

    private boolean should_intake() {
        HuskyLens.Block[] blocks = huskyLens.blocks();
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].id == 1) {
                return false;
            }
        }
        return true;
    }
}
