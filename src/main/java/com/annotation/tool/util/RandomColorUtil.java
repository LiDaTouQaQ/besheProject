package com.annotation.tool.util;

import java.util.Random;

/**
 * @ClassName RandomColorUtil
 * @Author Liyh
 * @Date 2024.04.11 15:54
 * @Description:
 **/
public class RandomColorUtil {
    public static String generateRandomColor(){
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        String color = String.format("rgb(%d,%d,%d)",red,green,blue);
        return color;
    }

    public static String rgbToHex(){
        String rgbColor = generateRandomColor();
        String[] rgbValues = rgbColor.substring(rgbColor.indexOf('(')+1,rgbColor.indexOf(')')).split(",");
        StringBuilder hexColor = new StringBuilder("#");
        for(String value : rgbValues){
            int intValue = Integer.parseInt(value.trim());
            String hexValue = Integer.toHexString(intValue);
            hexColor.append(hexValue.length()==1?"0"+hexValue:hexValue);
        }
        return hexColor.toString();
    }
}
