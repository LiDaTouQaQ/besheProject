package com.annotation.tool.controller;

import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;

import java.util.Map;

/**
 * @ClassName RelationController
 * @Author Liyh
 * @Date 2024.04.08 17:12
 * @Description:
 **/
public interface RelationController {
    Result createRelation(RelationEntity relationEntity);

    Result createRelationBatch(String content);

    Result updateRelation(RelationEntity relationEntity);

    Result getAllRelation(Map<String,Object> params);

    Result getRelationByLikeName(RelationEntity entity);

    Result countRelation(RelationEntity entity);
}
