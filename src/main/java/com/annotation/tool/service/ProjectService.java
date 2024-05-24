package com.annotation.tool.service;

import com.annotation.tool.entity.ProjectEntity;
import com.annotation.tool.entity.Result;

import java.util.List;

/**
 * @ClassName ProjectService
 * @Author Liyh
 * @Date 2024.04.08 15:07
 * @Description:
 **/
public interface ProjectService {
    Result createProject(ProjectEntity projectEntity);
    Result selectProject(String id,Integer page,Integer pageSize);
    Result deleteProject(List<String> projectIds);
    Result selectOneProject(String projectId);

    Result countProject(String id);
}
