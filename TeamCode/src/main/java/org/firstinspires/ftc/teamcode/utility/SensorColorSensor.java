package org.firstinspires.ftc.teamcode.utility;

//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Utility(name = "Test: Color sensor")
public class SensorColorSensor extends LinearOpMode {
    @Override
    public void runOpMode() {
        RevColorSensorV3 colorSensor = hardwareMap.get(RevColorSensorV3.class, "color");
//        TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            

            telemetry.addData("1: blue", colorSensor.blue());
            telemetry.addData("3: green", colorSensor.green());
            telemetry.addData("2: red", colorSensor.red());
            telemetry.addData("1: normalized blue", colorSensor.getNormalizedColors().blue);
            telemetry.addData("3: normalized green", colorSensor.getNormalizedColors().green);
            telemetry.addData("2: normalized red", colorSensor.getNormalizedColors().red);
//            telemetry.addData("distance (inches)", colorSensor.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
    }
}
