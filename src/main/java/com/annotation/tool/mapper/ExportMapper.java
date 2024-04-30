package com.annotation.tool.mapper;

import com.annotation.tool.entity.ExportRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ExportMapper
 * @Author Liyh
 * @Date 2024.04.22 09:30
 * @Description:
 **/
@Mapper
public interface ExportMapper {
    List<ExportRelationEntity> getExportDateByProjectId(@Param("projectId") String projectId);
    List<ExportRelationEntity> getExportDateByProjectContentId(@Param("list") List<String> projectContentIds,@Param("projectId") String projectId);
}
