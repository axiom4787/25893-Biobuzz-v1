package org.firstinspires.ftc.teamcode.utility;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Utility(name = "Test: Color sensor")
public class SensorColorSensor extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor shooter = hardwareMap.get(DcMotor.class, "motor");
        RevColorSensorV3 colorSensor = hardwareMap.get(RevColorSensorV3.class, "color");
        TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            panels.addData("red", colorSensor.red());
            panels.addData("green", colorSensor.green());
            panels.addData("blue", colorSensor.blue());
            panels.addData("normalized colors", colorSensor.getNormalizedColors());
            panels.addData("distance (inches)", colorSensor.getDistance(DistanceUnit.INCH));
            panels.update(telemetry);
        }
    }
}
