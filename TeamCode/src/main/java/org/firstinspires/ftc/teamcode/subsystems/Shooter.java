package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private final DcMotorEx shooter;
    private int tps = 0;

    public Shooter(HardwareMap hardwareMap){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }


    /// Method to update the shooter. Must be called on every loop
    public void update(){
        if(shooter.getVelocity() > tps) {
            shooter.setPower(0);
        } else if (shooter.getVelocity() == tps) {
            shooter.setPower(tps / 2000.0);
        } else {
            shooter.setPower(1);
        }
    }


    /// Get the shooter's velocity in ticks per second
    public double getVelocity() {
        return shooter.getVelocity();
    }


    /// Set the shooter's target velocity
    public void setTargetVelocity(int tps){
        this.tps = tps;
    }


    /// Whether the shooter is up to speed (within 60 TPS)
    public boolean isUpToSpeed(){
        return shooter.getVelocity() >= tps - 60;
    }
}
