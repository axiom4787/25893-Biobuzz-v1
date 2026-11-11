package org.firstinspires.ftc.teamcode.subsystems;


import com.pedropathing.controllers.Controller;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.utils.Angle;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;


public class Drivetrain {
    private final Follower follower;

    public Drivetrain(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
    }


    /// Public method to directly access the follower
    public Follower follower() {
        return follower;
    }


    /// Drive the robot in field centric mode
    public void driveFC(double forward, double right, double turn) {
        DrivePowers powers = ManualDrive.fieldCentric(forward, right, turn, follower.pose().heading());

        follower.manual(powers);
    }


    public double power = 0;
    private final Controller headingPID = Controller.proportional(2);
    /// Drive the robot in field centric mode while continually facing the hive
    public void driveFacingGoalFC(double forward, double right) {
        DrivePowers powers = ManualDrive.fieldCentric(forward, right, 0, follower.pose().heading());

        double targetHeading = getTargetHeading();

        double headingError = Angle.normalizeSigned(targetHeading - follower.pose().heading());
        double power = headingPID.calculate(targetHeading, headingError, follower.twist().omega);
        this.power = power;
        powers = new DrivePowers(powers.forward(), powers.strafe(), power);

        follower.manual(powers);
    }


    /// Whether the robot's rotation is within 15 degrees of the hive
    public boolean isAlignedToHive() {
        double robotHeading = follower.pose().heading();
        double targetHeading = getTargetHeading();

        double error = Angle.normalizeSigned(robotHeading - targetHeading);
        double errorThreshold = Math.toRadians(15);

        return Math.abs(error) < errorThreshold;
    }


    /// Helper to get the Pose of the hive based on which half of the field the robot is on
    private Pose getHivePose() {
        if (follower.pose().y() > 70.75) {
            return new Pose(58.8976, 141.5 - 55.1654); // Left hive
        } else {
            return new Pose(58.8976, 55.1654); // Right hive
        }
    }


    /// Set the robot's pose
    public void setPose(double x, double y, double headingDegrees) {
        follower.setPose(new Pose(x, y, Math.toRadians(headingDegrees)));
    }


    /// Helper to get the robot's target heading for automatically aligning to the goal
    public double getTargetHeading() {
        double dx = getHivePose().x() - follower.pose().x();
        double dy = getHivePose().y() - follower.pose().y();

        // Add pi because the shooter is on the back of the robot, and 0 is the front of the robot
        return Angle.normalize(Math.atan2(dy, dx) + Math.PI);
    }


    /// Method to update the drivetrain. Must be called on every loop
    public void update() {
        follower.update();
    }
}
