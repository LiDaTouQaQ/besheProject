package com.annotation.tool.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ProjectContantEntity
 * @Author Liyh
 * @Date 2024.04.08 15:23
 * @Description:
 **/
@Data
public class ProjectContentEntity {
    private String id;
    private String projectId;
    private String contentId;
    private String projectName;
    private String testContent;
    private String isComplete;
    private String completeBy;

    private List<ContentEntityEntity> entityEntityList;
    private List<ContentRelationEntity> relationEntityList;
}
