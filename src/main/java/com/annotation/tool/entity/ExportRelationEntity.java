package com.annotation.tool.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName ExportRelationEntity
 * @Author Liyh
 * @Date 2024.04.22 09:27
 * @Description:
 **/
@Data
public class ExportRelationEntity {
    @ExcelIgnore()
    private String contentText;
    @ExcelIgnore
    private String entityIdOne;
    @ExcelIgnore()
    private String entityOne;
    @ExcelIgnore()
    private String entityNameOne;
    @ExcelIgnore()
    private String startOne;
    @ExcelIgnore()
    private String endOne;
    @ExcelIgnore
    private String entityIdTwo;
    @ExcelIgnore()
    private String entityTwo;
    @ExcelIgnore()
    private String entityNameTwo;
    @ExcelIgnore()
    private String startTwo;
    @ExcelIgnore()
    private String endTwo;
    @ExcelIgnore()
    private String relationName;

    @ExcelProperty()
    private String content;
    @ExcelProperty()
    private String entityAndRelation;
    @ExcelProperty()
    private String entity;
}
