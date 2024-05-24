package com.annotation.tool.controller.impl;


import cn.hutool.json.JSONUtil;
import com.annotation.tool.controller.ProjectController;
import com.annotation.tool.entity.ProjectEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProjectController
 * @Author Liyh
 * @Date 2024.04.08 15:17
 * @Description:
 **/
@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectControllerImpl implements ProjectController {

    @Autowired
    ProjectService projectService;

    @Override
    @PostMapping("/create")
    public Result createProject(@RequestBody ProjectEntity projectEntity) {
        JSONUtil.toJsonStr(projectEntity);
        return projectService.createProject(projectEntity);
    }

    @Override
    @PostMapping("/get")
    public Result selectProject(@RequestBody Map<String,Object> params) {
        String id = MapUtils.getString(params,"createBy");
        Integer page = MapUtils.getInteger(params,"page");
        Integer pageSize = MapUtils.getInteger(params,"pageSize");
        return projectService.selectProject(id,page,pageSize);
    }

    @Override
    @PostMapping("/getCount")
    public Result countProject(@RequestBody Map<String, Object> params) {
        String id = MapUtils.getString(params,"createBy");
        return projectService.countProject(id);
    }

    @Override
    @PostMapping("/delete")
    public Result deleteProject(@RequestBody Map<String, Object> params) {
        List<String> projectId = (List<String>) params.get("projectId");
        return projectService.deleteProject(projectId);
    }

    @Override
    @PostMapping("/getOne")
    public Result getOneProject(@RequestBody Map<String, Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        return projectService.selectOneProject(projectId);
    }
}
