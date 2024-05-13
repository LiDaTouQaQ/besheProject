package com.annotation.tool.controller.impl;

import cn.hutool.json.JSONUtil;
import com.annotation.tool.controller.ProjectContentController;
import com.annotation.tool.entity.ContentEntityEntity;
import com.annotation.tool.entity.ContentRelationEntity;
import com.annotation.tool.entity.ProjectContentEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.mapper.ContentEntityMapper;
import com.annotation.tool.service.DocumentExportService;
import com.annotation.tool.service.ProjectContentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProjectContentControllerImpl
 * @Author Liyh
 * @Date 2024.04.08 17:15
 * @Description:
 **/
@RestController
@Slf4j
@RequestMapping("/projectContent")
public class ProjectContentControllerImpl implements ProjectContentController {
    @Autowired
    ProjectContentService projectContentService;
    @Autowired
    DocumentExportService documentExportService;

    @Override
    @PostMapping(value = "/create")
    public Result createContent(@RequestBody ProjectContentEntity projectContentEntity) {
        return projectContentService.createContent(projectContentEntity);
    }

    @Override
    @PostMapping(value = "/createBatch")
    public Result createContentBatch(String content) {
        return null;
    }

    @Override
    @PostMapping(value = "/update")
    public Result updateContent(@RequestBody ProjectContentEntity projectContentEntity) {
        return projectContentService.updateContentInfo(projectContentEntity);
    }

    @Override
    @PostMapping(value = "/getAll")
    public Result getAllContent(@RequestBody Map<String,Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        String page = MapUtils.getString(params,"page","0");
        String pageSize = MapUtils.getString(params,"pageSize","10");
        return projectContentService.getAllContent(projectId,page,pageSize);
    }

    @Override
    @PostMapping(value = "/getDetail")
    public Result getContentDetail(@RequestBody ProjectContentEntity entity) {
        log.info(JSONUtil.toJsonStr(entity));
        return projectContentService.getDetailContentByProjectIdAndId(entity);
    }

    @Override
    @PostMapping(value = "/deleteOne")
    public Result deleteContent(@RequestBody ProjectContentEntity entity) {
        log.info(JSONUtil.toJsonStr(entity));
        return projectContentService.deleteContentByProjectIdAndContentId(entity);
    }

    @Override
    @PostMapping(value = "/insertEntity")
    public Result insertContentEntity(@RequestBody ContentEntityEntity entity) {
        log.info(JSONUtil.toJsonStr(entity));
        return projectContentService.insertContentEntity(entity);
    }

    @Override
    @PostMapping(value = "/exportAllRelation")
    public Result exportRelationExcel(@RequestBody ContentEntityEntity entity) {
        return documentExportService.exportRelationAndEntity(entity.getProjectId());
    }

    @Override
    @PostMapping(value = "/exportRelationByContentId")
    public void exportRelationExcel(HttpServletResponse response, @RequestBody Map<String, Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        List<String> ids = (List<String>) params.get("contentIds");
        documentExportService.exportRelationAndEntity(response,projectId,ids);
    }

    @Override
    @PostMapping(value = "/exportRelationByContentIdNeo4j")
    public Result exportRelationNeo4j(@RequestBody Map<String, Object> params) throws Exception {
        String projectId = MapUtils.getString(params,"projectId");
        List<String> ids = (List<String>) params.get("contentIds");
        return documentExportService.exportRelationNeo4j(projectId,ids);
    }

    @Override
    @PostMapping(value = "/exportRelationByContentIdJSON")
    public void exportRelationJson(HttpServletResponse response, @RequestBody Map<String, Object> params) throws IOException {
        String projectId = MapUtils.getString(params,"projectId");
        List<String> ids = (List<String>) params.get("contentIds");
        documentExportService.exportRelationJson(response,projectId,ids);
    }

    @Override
    @PostMapping(value = "/getCount")
    public Result getCount(@RequestBody Map<String, Object> params) {
        return projectContentService.getCount(MapUtils.getString(params,"projectId"));
    }

    @Override
    @PostMapping(value = "/createER")
    public Result createEntityAndRelation(@RequestBody ProjectContentEntity entity) {
        return projectContentService.addER(entity);
    }

    @Override
    @PostMapping(value = "/deleteEntity")
    public Result deleteEntity(@RequestBody ContentEntityEntity entity) {
        return projectContentService.deleteEntity(entity);
    }

    @Override
    @PostMapping(value = "/deleteRelation")
    public Result deleteRelation(@RequestBody ContentRelationEntity entity) {
        return projectContentService.deleteRelation(entity);
    }

    @Override
    @PostMapping(value = "/userWork")
    public Result countUserWork(@RequestBody Map<String, Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        return projectContentService.countUserWork(projectId);
    }
}
