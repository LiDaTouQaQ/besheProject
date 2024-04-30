package com.annotation.tool.mapper;

import com.annotation.tool.entity.ATEntityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ATEntityMapper
 * @Author Liyh
 * @Date 2024.04.08 16:20
 * @Description:
 **/
public interface ATEntityMapper {
    int createATEntity(@Param("entity") ATEntityEntity entity);

    int createATEntityBatch(@Param("list") List<ATEntityEntity> entities);

    List<ATEntityEntity> getAllATEntityByProjectId(@Param("projectId") String projectId, @Param("start") String start, @Param("end") String end);

    List<ATEntityEntity> getEntityByLikeEntityName(@Param("projectId") String projectId, @Param("name") String entityName);

    ATEntityEntity getEntityByEntityName(@Param("projectId") String projectId, @Param("entityName") String entityName);

    int updateEntity(@Param("entity") ATEntityEntity entity);

    int countEntity(@Param("projectId") String projectId);
}
