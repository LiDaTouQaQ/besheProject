package com.annotation.tool.controller;

import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;

import java.util.Map;

/**
 * @ClassName ATEntityController
 * @Author Liyh
 * @Date 2024.04.08 17:12
 * @Description:
 **/
public interface ATEntityController {
    Result createATEntity(ATEntityEntity entity);

    Result createATEntityBatch(String content);

    Result updateATEntity(ATEntityEntity entity);

    Result getAllATEntity(Map<String,Object> params);

    Result getEntityByLike(ATEntityEntity entity);

    Result deleteEntity(ATEntityEntity entity);

    Result countEntity(ATEntityEntity entity);

}
