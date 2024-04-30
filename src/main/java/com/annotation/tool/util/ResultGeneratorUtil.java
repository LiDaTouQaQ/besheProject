package com.annotation.tool.util;

import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.enums.ResultEnum;
import org.springframework.util.StringUtils;

/**
 * 响应结果生成工具
 *
 */
public class ResultGeneratorUtil {
    private static final String RESULT_CODE_SUCCESS = "10000";
    private static final String RESULT_CODE_SERVER_ERROR = "00000";

    public static Result genSuccessResult() {
        return new Result(ResultEnum.SUCCESS,null);
    }

    public static Result genSuccessResult(String message) {
        Result result = new Result();
        result.setCode(RESULT_CODE_SUCCESS);
        result.setMsg(message);
        return result;
    }

    public static Result genSuccessResult(Object data) {
        return new Result(ResultEnum.SUCCESS,data);
    }

    public static Result genFailResult(ResultEnum resultEnum) {
        return new Result(resultEnum,null);
    }
    public static Result genFailResult(ResultEnum resultEnum,String msg) {
        Result result = new Result(resultEnum,null);
        result.setMsg(msg);
        return result;
    }
}
