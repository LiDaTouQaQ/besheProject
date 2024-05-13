package com.annotation.tool.controller;

import com.annotation.tool.entity.ContentEntityEntity;
import com.annotation.tool.entity.ContentRelationEntity;
import com.annotation.tool.entity.ProjectContentEntity;
import com.annotation.tool.entity.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName ProjectContentController
 * @Author Liyh
 * @Date 2024.04.08 17:12
 * @Description:
 **/
public interface ProjectContentController {
    Result createContent(ProjectContentEntity projectContentEntity);

    Result createContentBatch(String content);

    Result updateContent(ProjectContentEntity projectContentEntity);

    Result getAllContent(Map<String,Object> params);

    Result getContentDetail(ProjectContentEntity entity);

    Result deleteContent(ProjectContentEntity entity);

    Result insertContentEntity(ContentEntityEntity entity);

    Result exportRelationExcel(ContentEntityEntity entity);

    void exportRelationExcel(HttpServletResponse response, Map<String,Object> params);

    Result exportRelationNeo4j(Map<String,Object> params) throws Exception;

    void exportRelationJson(HttpServletResponse response, Map<String,Object> params) throws IOException;

    Result getCount(Map<String,Object> params);

    Result createEntityAndRelation(ProjectContentEntity entity);

    Result deleteEntity(ContentEntityEntity entity);

    Result deleteRelation(ContentRelationEntity entity);

    Result countUserWork(Map<String,Object> params);
}
