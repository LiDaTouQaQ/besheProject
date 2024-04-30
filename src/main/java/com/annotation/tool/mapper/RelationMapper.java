package com.annotation.tool.mapper;

import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.RelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName RelationMapper
 * @Author Liyh
 * @Date 2024.04.08 16:21
 * @Description:
 **/
public interface RelationMapper {

    int createRelation(@Param("entity") RelationEntity entity);
    int createRelationBatch(List<RelationEntity> entities);
    List<RelationEntity> getAllRelationByProjectId(@Param("projectId") String projectId,@Param("start") String start,@Param("end") String end);
    List<RelationEntity> getRelationByLikeName(@Param("projectId") String projectId,@Param("name") String name);
    RelationEntity getRelationByRelationName(@Param("projectId") String projectId,@Param("relationName") String relationName);
    int countRelation(@Param("projectId") String projectId);
}
