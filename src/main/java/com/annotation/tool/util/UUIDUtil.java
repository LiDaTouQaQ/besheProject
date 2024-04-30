package com.annotation.tool.util;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Author Liyh
 * @Date 2024.04.08 15:11
 * @Description:
 **/
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
