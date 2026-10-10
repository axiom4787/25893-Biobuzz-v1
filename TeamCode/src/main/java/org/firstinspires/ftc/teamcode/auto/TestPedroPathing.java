package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class TestPedroPathing extends LinearOpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, Paths.startToEdge()),
                repeat(sequential(
                        follow(follower, Paths.corner1To2()),
                        follow(follower, Paths.corner2To3()),
                        follow(follower, Paths.corner3To4()),
                        follow(follower, Paths.corner4To1())
                ), 10),
                follow(follower, Paths.edgeToStart())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(Paths.start);
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

    public static class Paths {

        private static final PoseFactory poseFactory = PoseFactory.degrees();

        private static final Pose start = poseFactory.of(50, 50, 90);
        private static final Pose startToEdge = poseFactory.of(75, 25, 90);
        private static final Pose corner1To2 = poseFactory.of(75, 75, 180);
        private static final Pose corner2To3 = poseFactory.of(25, 75, -90);
        private static final Pose corner3To4 = poseFactory.of(25, 25, 0);
        private static final Pose corner4To1 = poseFactory.of(75, 25, 90);
        private static final Pose edgeToStartStart = poseFactory.of(75, 25, 0);
        private static final Pose edgeToStart = poseFactory.of(50, 50, 0);

        public static Path startToEdge() {
            return line(start, startToEdge).linear(start, startToEdge);
        }

        public static Path corner1To2() {
            return line(startToEdge, corner1To2).linear(startToEdge, corner1To2);
        }

        public static Path corner2To3() {
            return line(corner1To2, corner2To3).linear(corner1To2, corner2To3);
        }

        public static Path corner3To4() {
            return line(corner2To3, corner3To4).linear(corner2To3, corner3To4);
        }

        public static Path corner4To1() {
            return line(corner3To4, corner4To1).linear(corner3To4, corner4To1);
        }

        public static Path edgeToStart() {
            return line(edgeToStartStart, edgeToStart).linear(edgeToStartStart, edgeToStart);
        }
    }
}
