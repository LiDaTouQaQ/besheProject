package com.annotation.tool.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName ProjectEntity
 * @Author Liyh
 * @Date 2024.04.08 13:17
 * @Description:
 **/
@Data
public class ProjectEntity {
    public Integer id;
    public String projectId;
    /**
     * 项目名称
     */
    public String projectName;
    public String remark;
    public String tag;
    /**
     * 任务数量
     */
    public Long workNum;
    /**
     * 已完成任务数量
     */
    public Long completeNum;
    /**
     * 创建时间
     */
    public Date createDate;
    /**
     * 创建人
     */
    public String createBy;
    /**
     * 是否允许添加标签
     */
    public String isAllowAdd;
    /**
     * 任务是否乱序
     */
    public String isOrder;
    /**
     * 是否有关系字段
     */
    public String isRelation;
    /**
     * 是否已经删除
     */
    public String isDelete;

    public String autoPort;
}
