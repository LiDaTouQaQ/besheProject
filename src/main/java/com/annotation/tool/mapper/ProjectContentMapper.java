package com.annotation.tool.mapper;

import com.annotation.tool.entity.ExportRelationEntity;
import com.annotation.tool.entity.ProjectContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ProjectContentMapper
 * @Author Liyh
 * @Date 2024.04.08 15:33
 * @Description:
 **/
@Mapper
public interface ProjectContentMapper {
    int insertProjectContent(@Param("entity") ProjectContentEntity projectContentEntity);

    int insertBatchProjectContent(@Param("list")List<ProjectContentEntity> projectContentEntities);

    int updateProjectContentById(@Param("entity") ProjectContentEntity projectContentEntity);

    List<ProjectContentEntity> selectAllProjectContentByPage(String projectId);

    ProjectContentEntity getDetailContentByProjectIdAndContentId(@Param("projectId") String projectId,@Param("contentId") String contentId);

    int deleteByContentIdAndProjectIdInt(@Param("projectId") String projectId,@Param("contentId") String contentId);

    String maxProjectContentIdByProjectId(@Param("projectId") String projectId);

    int countByProjectIdInt(@Param("projectId") String projectId);
}
