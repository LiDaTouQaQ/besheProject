package com.annotation.tool.service;

import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.Result;

import java.util.List;

/**
 * @ClassName EntityService
 * @Author Liyh
 * @Date 2024.04.08 16:32
 * @Description:
 **/
public interface EntityService {
    Result createBatchEntity(List<ATEntityEntity> entities);

    Result getAllEntityByProjectId(String projectId,String page,String pageSize);

    Result createEntity(ATEntityEntity entity);

    Result getEntityByLikeName(ATEntityEntity entity);

    Result updateEntity(ATEntityEntity entity);

    Result countEntity(String projectId);

}
