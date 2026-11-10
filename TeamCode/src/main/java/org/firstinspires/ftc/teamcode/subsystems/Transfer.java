package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    private final CRServo transfer;

    public Transfer(HardwareMap hardwareMap){
        transfer = hardwareMap.get(CRServo.class, "transfer");
    }


    /// Run the transfer in
    public void in(){
        transfer.setPower(-1);
    }


    /// Turn off the transfer
    public void off(){
        transfer.setPower(0);
    }
}
