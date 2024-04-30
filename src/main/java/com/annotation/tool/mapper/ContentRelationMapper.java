package com.annotation.tool.mapper;

import com.annotation.tool.entity.ContentEntityEntity;
import com.annotation.tool.entity.ContentRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ContentRelationMapper
 * @Author Liyh
 * @Date 2024.04.10 15:24
 * @Description:
 **/
@Mapper
public interface ContentRelationMapper {
    int insertRelation(@Param("entity")ContentRelationEntity entity);
    int deleteEntity(@Param("entity")ContentRelationEntity entity);
    List<ContentRelationEntity> selectAllEntity(@Param("projectId") String projectId,@Param("projectContentId") String projectContentId);
    int deleteByProjectIdAndProjectContentId(@Param("projectId") String projectId,@Param("projectContentId") String projectContentId);
}
