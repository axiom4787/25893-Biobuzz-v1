package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;

@Autonomous
public class PreloadAndGarden extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.819, 8.3845, -90);
    private final Pose path1 = poseFactory.of(8, 10, -90);
    private final Pose path1Control1 = poseFactory.of(8, 40.6318, 0);
    private final Pose point2 = poseFactory.of(59.3127, 8.2114, -90);
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> shooter.setTargetVelocity(1600)),
                waitUntil(() -> shooter.isUpToSpeed()),

                instant(transfer::in), // shoot preload
                waitMs(4000),
                instant(transfer::off),

                instant(intake::in), // go to garden and intake from there
                follow(follower, path1()),
                waitMs(3000),
                instant(intake::off),
                follow(follower, path2()),

                instant(transfer::in), // shoot balls obtained from garden
                waitMs(4000),
                instant(transfer::off)
        );
    }

    Shooter shooter;
    Transfer transfer;
    Intake intake;


    @Override
    public void runOpMode() {
        shooter = new Shooter(hardwareMap);
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            shooter.update();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.curve(start, path1Control1, path1).constant(path1).with(Constants.foresightConfig.maxPathSpeed.at(0.5));
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }
}
