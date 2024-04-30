package com.annotation.tool.mapper;

import com.annotation.tool.entity.ContentEntityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ContentEntityMapper
 * @Author Liyh
 * @Date 2024.04.10 15:10
 * @Description:
 **/
@Mapper
public interface ContentEntityMapper {
    int insertEntity(@Param("entity") ContentEntityEntity entity);
    int insertEntityBatch(@Param("list") List<ContentEntityEntity> entityList);
    int deleteEntity(@Param("entity") ContentEntityEntity entity);
    List<ContentEntityEntity> selectAllEntity(@Param("projectId") String projectId,@Param("projectContentId") String projectContentId);
    int deleteByProjectIdAndProjectContentId(@Param("projectId") String projectId,@Param("projectContentId") String projectContentId);
    int getMaxToRelationIdByProjectIdAndProjectContentId(@Param("projectId") String projectId,@Param("projectContentId") String projectContentId);

}
