package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    public DcMotorEx shooter;
    public int tps;
    public Shooter(HardwareMap hardwareMap){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }
    public void update(){
        if(shooter.getVelocity() > tps){
            shooter.setPower(0);
        } else if (shooter.getVelocity() == tps) {
            shooter.setPower(tps/2000.0);
        } else {
            shooter.setPower(1);
        }

    }
    public void setVelocity(int tps){
        this.tps = tps;
    }

    public boolean upToSpeed(){
        return shooter.getVelocity() >= tps - 200;

    }
}
