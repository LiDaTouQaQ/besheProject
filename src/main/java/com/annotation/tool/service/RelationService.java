package com.annotation.tool.service;

import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;

import java.util.List;

/**
 * @ClassName RelationService
 * @Author Liyh
 * @Date 2024.04.08 16:31
 * @Description:
 **/
public interface RelationService {
    Result createBatchRelation(List<RelationEntity> entities);

    Result getAllRelationByProjectId(String projectId,String page,String pageSize);

    Result createRelation(RelationEntity relationEntity);

    Result getRelationLikeName(RelationEntity entity);

    Result countRelation(String projectId);
}
