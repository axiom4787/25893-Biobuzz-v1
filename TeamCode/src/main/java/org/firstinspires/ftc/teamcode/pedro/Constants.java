package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.algorithm.ForesightConfig;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(new PinpointLocalizer(h, localizerConfig), new Mecanum(h, drivetrainConfig), new Foresight(foresightConfig));
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.manualBrakeMode.set(false); //set true if we want manual break mode
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-5.569363691675382);
        c.yPodOffset.set(-3.4302803099624755);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.22677472061805998);
                Controller secondaryTranslationalForward = Controller.proportional(0.08378719973623824);
                Controller primaryTranslationalLateral = Controller.proportional(0.3629033476275962);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1340830899923568);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016455388084919382));
                c.brake.set(Controller.proportionalFeedforward(0.013987079872181474));

                c.headingFeedback.set(Controller.proportional(3.5100028408612025));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.042049371531103524, 0.006980327069153524));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09998660649429843, 0.025262297101421954));
                c.quadraticBrakeCoefficients.set(Matrix.diag(3.7531248250493075E-4, 0.002319508412547453));

                c.maxAchievableForwardVelocity.set(61.23211987646126);
                c.maxAchievableStrafeVelocity.set(52.50685176586476);
                c.naturalForwardDeceleration.set(28.909555899886826);
                c.naturalStrafeDeceleration.set(59.12140313945829);
            }
    );
}


