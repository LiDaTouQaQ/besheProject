package com.annotation.tool.controller;

import com.annotation.tool.entity.ProjectEntity;
import com.annotation.tool.entity.Result;

import java.util.Map;

/**
 * @ClassName ProjectController
 * @Author Liyh
 * @Date 2024.04.08 15:16
 * @Description:
 **/
public interface ProjectController {
    Result createProject(ProjectEntity projectEntity);

    Result selectProject(Map<String,Object> params);

    Result countProject(Map<String,Object> params);

    Result deleteProject(Map<String,Object> params);

}
