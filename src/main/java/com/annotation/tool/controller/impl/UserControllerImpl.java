package com.annotation.tool.controller.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.annotation.tool.controller.UserController;
import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.UserEntity;
import com.annotation.tool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserControllerImpl
 * @Author Liyh
 * @Date 2024.04.05 10:57
 * @Description:
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    @Autowired
    UserService userService;


    @PostMapping(value = "/login")
    @Override
    public Result login(@RequestBody UserEntity user) {
        log.info(JSONUtil.toJsonStr(user));
        return userService.login(user);
    }

    @Override
    @PostMapping(value = "/register")
    public Result register(@RequestBody UserEntity user) {
        return userService.register(user);
    }

    @Override
    @PostMapping(value = "/getProjectUser")
    public Result getProjectUser(@RequestBody String projectId) {
        Map<String,Object> params = JSONUtil.toBean(projectId,Map.class);
        return userService.getUserProject(MapUtils.getString(params,"projectId"));
    }

    @Override
    @PostMapping(value = "/addProjectUser")
    public Result addProjectUser(@RequestBody UserEntity user) {
        return userService.addUserProject(user);
    }

    @Override
    @PostMapping(value = "/deleteProjectUser")
    public Result deleteProjectUser(Map<String, Object> params) {
        List<String> ids = (List<String>) params.get("ids");
        String projectId = MapUtils.getString(params,"projectId");
        return userService.deleteUserProject(ids,projectId);
    }

    @Override
    @PostMapping(value = "/addUserForNew")
    public Result addProjectUserForNew(@RequestBody UserEntity user) {
        return userService.addUserProjectForNew(user);
    }
}
