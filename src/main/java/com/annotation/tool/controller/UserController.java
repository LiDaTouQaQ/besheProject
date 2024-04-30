package com.annotation.tool.controller;

import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ClassName UserController
 * @Author Liyh
 * @Date 2024.04.05 10:56
 * @Description:
 **/
public interface UserController {
    Result login( UserEntity user);

    Result loginByToken(UserEntity user);

    Result register(UserEntity user);

    Result getProjectUser(String projectId);

    Result addProjectUser(UserEntity user);

    Result deleteProjectUser(Map<String,Object> params);

    Result addProjectUserForNew(UserEntity user);
}
