package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;

import java.util.concurrent.TimeUnit;

public class PrismDriver {
    public GoBildaPrismDriver prismDriver;
    public PrismDriver(HardwareMap hardwareMap){
        prismDriver = hardwareMap.get(GoBildaPrismDriver.class, "prismDriver");
    }

    public void setGreen(){
        prismDriver.clearAllAnimations();
        PrismAnimations.Solid green = new PrismAnimations.Solid(Color.GREEN);
        green.setBrightness(50);
        green.setStartIndex(0);
        green.setStopIndex(12);
        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, green);

    }

    public void setRed(){
        prismDriver.clearAllAnimations();
        PrismAnimations.Solid red = new PrismAnimations.Solid(Color.RED);
        red.setBrightness(50);
        red.setStartIndex(0);
        red.setStopIndex(12);
        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, red);
    }

    public void setBlue(){
        prismDriver.clearAllAnimations();
        PrismAnimations.Solid blue = new PrismAnimations.Solid(Color.BLUE);
        blue.setBrightness(50);
        blue.setStartIndex(0);
        blue.setStopIndex(12);
        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, blue);
    }

    public void setStrobe(){
        prismDriver.clearAllAnimations();
        PrismAnimations.Blink strobe1 = new PrismAnimations.Blink();
        strobe1.setBrightness(50);
        strobe1.setStartIndex(0);
        strobe1.setStopIndex(5);
        strobe1.setPrimaryColor(Color.RED);
        strobe1.setSecondaryColor(Color.WHITE);
        strobe1.setPeriod(500, TimeUnit.MILLISECONDS);
        strobe1.setPrimaryColorPeriod(250, TimeUnit.MILLISECONDS);
        PrismAnimations.Blink strobe2 = new PrismAnimations.Blink();
        strobe2.setBrightness(50);
        strobe2.setStartIndex(6);
        strobe2.setStopIndex(11);
        strobe2.setPrimaryColor(Color.WHITE);
        strobe2.setSecondaryColor(Color.RED);
        strobe2.setPeriod(500, TimeUnit.MILLISECONDS);
        strobe2.setPrimaryColorPeriod(250, TimeUnit.MILLISECONDS);
        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, strobe1);
        prismDriver.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_1, strobe2);
    }
}
