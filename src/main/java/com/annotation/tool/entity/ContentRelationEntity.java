package com.annotation.tool.entity;

import lombok.Data;

/**
 * @ClassName ContentRelationEntity
 * @Author Liyh
 * @Date 2024.04.10 15:07
 * @Description:
 **/
@Data
public class ContentRelationEntity {
    private Integer id;
    private String projectId;
    private String projectContentId;
    private String relationName;
    private Integer form;
    private Integer to;
    private Integer relationId;
}
