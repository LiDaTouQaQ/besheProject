package com.annotation.tool.entity;

import lombok.Data;

/**
 * @ClassName RelationEntity
 * @Author Liyh
 * @Date 2024.04.08 16:19
 * @Description:
 **/
@Data
public class RelationEntity {
    private String id;
    private String projectId;
    private String relationName;
    private Integer num;
    private String spanColor;
}
