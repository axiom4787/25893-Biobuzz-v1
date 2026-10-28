package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {

    public CRServo transfer;
    public Transfer(HardwareMap hardwareMap){
        transfer = hardwareMap.get(CRServo.class, "transfer");
    }

    public void in(){
        transfer.setPower(-1);
    }

    public void off(){
        transfer.setPower(0);
    }
}
