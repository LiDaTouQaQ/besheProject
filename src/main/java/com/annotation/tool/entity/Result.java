package com.annotation.tool.entity;

import com.annotation.tool.entity.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Author Liyh
 * @Date 2024.04.05 10:38
 * @Description:
 **/
@Data
public class Result implements Serializable {
    private String msg;
    private String code;
    private Object data;

    public Result(ResultEnum resultEnum, Object data){
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
        this.data=data;
    }

    public Result(){}

}
