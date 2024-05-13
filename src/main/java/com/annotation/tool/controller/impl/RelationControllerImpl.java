package com.annotation.tool.controller.impl;

import com.annotation.tool.controller.RelationController;
import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.service.RelationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName RelationControllerImpl
 * @Author Liyh
 * @Date 2024.04.08 17:16
 * @Description:
 **/
@RestController
@Slf4j
@RequestMapping("/relation")
public class RelationControllerImpl implements RelationController {
    @Autowired
    RelationService relationService;

    @Override
    @PostMapping(value = "/create")
    public Result createRelation(@RequestBody RelationEntity relationEntity) {
        return relationService.createRelation(relationEntity);
    }

    @Override
    @PostMapping(value = "/createBatch")
    public Result createRelationBatch(String content) {
        return null;
    }

    @Override
    @PostMapping(value = "/update")
    public Result updateRelation(@RequestBody RelationEntity relationEntity) {
        return null;
    }

    @Override
    @PostMapping(value = "/getAll")
    public Result getAllRelation(@RequestBody Map<String,Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        String page = MapUtils.getString(params,"page","1");
        String pageSize = MapUtils.getString(params,"pageSize","10000");
        return relationService.getAllRelationByProjectId(projectId,page,pageSize);
    }

    @Override
    @PostMapping("/getOne")
    public Result getRelationByLikeName(@RequestBody RelationEntity entity) {
        return relationService.getRelationLikeName(entity);
    }

    @Override
    @PostMapping("/getCount")
    public Result countRelation(@RequestBody RelationEntity entity) {
        return relationService.countRelation(entity.getProjectId());
    }
}
