package org.firstinspires.ftc.teamcode.auto;


import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;


@Autonomous(name = "RedStart1", group = "Autonomous")
public class RedStart1 extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose redStart = poseFactory.of(58, 8, 90);
    private final Pose path1 = poseFactory.of(58, 8, 270);
    private final Pose path1Control1 = poseFactory.of(37.75, 22.75, 0);
    private final Pose path1Control2 = poseFactory.of(76.75, 22.75, 0);
    private final Pose point2Start = poseFactory.of(58, 8, 270);
    private final Pose point2 = poseFactory.of(46.9405, 130.2073, 90);
    private final Pose point2Control1 = poseFactory.of(76.75, 121.75, 0);
    private final Pose point2Control2 = poseFactory.of(47.0556, 115.9627, 0);
    private final Pose point3 = poseFactory.of(58, 109.5, 90);
    private final Pose point3Control1 = poseFactory.of(47, 116.1006, 0);
    private final Pose point4Start = poseFactory.of(58, 109.5, 90);
    private final Pose point4 = poseFactory.of(10, 47, 180);
    private final Pose point4Control1 = poseFactory.of(10.2881, 108.8659, 0);
    private final Pose point4Control2 = poseFactory.of(43.0762, 42.9604, 0);
    private final Pose point5Start = poseFactory.of(10, 47, 180);
    private final Pose point5 = poseFactory.of(58, 109.5, 90);
    private final Pose point5Control1 = poseFactory.of(42.9794, 42.9024, 0);
    private final Pose point5Control2 = poseFactory.of(10.8392, 108.7302, 0);


    private Transfer transfer;

    private Shooter shooter;

    private Intake intake;

    private int setpoint;

    // Autonomous routine
    public Command autoRoutine() {
        return parallel(
                infinite(() -> {
                    shooter.setTargetVelocity(setpoint);
                    shooter.update();
                }),

                sequential(
                        follow(follower, path1()),

                        instant(() -> transfer.in()),
                        waitMs(2500),
                        instant(() -> transfer.off()),

                        follow(follower, path2()),

                        instant(() -> intake.in()),
                        waitMs(3000),
                        instant(() -> intake.off()),

                        follow(follower, path3()),

                        instant(() -> transfer.in()),
                        waitMs(2500),
                        instant(() -> transfer.off()),

                        follow(follower, path4()),

                        instant(() -> intake.in()),
                        waitMs(3000),
                        instant(() -> intake.off()),

                        follow(follower, path5()),

                        instant(() -> transfer.in()),
                        waitMs(2500),
                        instant(() -> transfer.off())
                )
        );
    }

    @Override
    public void runOpMode() {
        setpoint = 2100;
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(redStart);
        follower.update();


        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

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
        return Paths.curve(redStart, path1Control1, path1Control2, path1).linear(redStart, path1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).constant(point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4Control2, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.curve(point5Start, point5Control1, point5Control2, point5).linear(point5Start, point5);
    }
}
