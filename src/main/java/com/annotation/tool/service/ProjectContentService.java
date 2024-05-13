package com.annotation.tool.service;

import com.annotation.tool.entity.ContentEntityEntity;
import com.annotation.tool.entity.ContentRelationEntity;
import com.annotation.tool.entity.ProjectContentEntity;
import com.annotation.tool.entity.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ProjectContentService
 * @Author Liyh
 * @Date 2024.04.08 16:06
 * @Description:
 **/
public interface ProjectContentService {
    Result createBatchContent(List<ProjectContentEntity> projectContentEntities);

    Result updateContentInfo(ProjectContentEntity entity);

    Result createContent(ProjectContentEntity projectContentEntity);

    Result getAllContent(String projectId,String page,String pageSize);

    Result executeFileContent(BufferedReader reader, String projectId, int projectContentId) throws IOException;

    Result getDetailContentByProjectIdAndId(ProjectContentEntity entity);

    Result deleteContentByProjectIdAndContentId(ProjectContentEntity entity);

    Result insertContentEntity(ContentEntityEntity entity);

    int maxContentID(String projectId);

    Result getCount(String projectId);

    Result addER(ProjectContentEntity entity);

    Result deleteEntity(ContentEntityEntity entity);

    Result deleteRelation(ContentRelationEntity entity);

    Result countUserWork(String projectId);

}
