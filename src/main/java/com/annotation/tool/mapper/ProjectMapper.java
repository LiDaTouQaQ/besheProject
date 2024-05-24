package com.annotation.tool.mapper;

import com.annotation.tool.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ProjectMapper
 * @Author Liyh
 * @Date 2024.04.08 13:21
 * @Description:
 **/
@Mapper
public interface ProjectMapper {
    int createProject(@Param("entity") ProjectEntity projectEntity);
    int deleteProject(@Param("list") List<String> projectIds);
    List<ProjectEntity> getProjectByCreateByPage(String createBy);

    int countProject(@Param("createBy") String id);
    ProjectEntity getByProjectIdProject(@Param("projectId") String projectId);
}
