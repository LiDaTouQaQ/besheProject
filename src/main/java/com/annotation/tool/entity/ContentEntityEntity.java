package com.annotation.tool.entity;

import lombok.Data;

/**
 * @ClassName ContantEntityEntity
 * @Author Liyh
 * @Date 2024.04.10 15:03
 * @Description:
 **/
@Data
public class ContentEntityEntity {
    private Integer id;
    private String projectId;
    private String projectContentId;
    private String entityName;
    private Integer start;
    private Integer end;
    private String entityContent;
    private Integer entityId;
    private String toRelationId;

    private String color;
}
