package com.annotation.tool.controller.impl;

import com.annotation.tool.controller.ATEntityController;
import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.service.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName ATEntityControllerImpl
 * @Author Liyh
 * @Date 2024.04.08 17:14
 * @Description:
 **/
@RestController
@Slf4j
@RequestMapping("/entity")
public class ATEntityControllerImpl implements ATEntityController {
    @Autowired
    EntityService entityService;

    @Override
    @PostMapping("/createOne")
    public Result createATEntity(@RequestBody ATEntityEntity entity) {
        return entityService.createEntity(entity);
    }

    @Override
    @PostMapping("/createBatch")
    public Result createATEntityBatch(String content) {
        return null;
    }

    @Override
    @PostMapping("/update")
    public Result updateATEntity(@RequestBody ATEntityEntity entity) {
        return entityService.updateEntity(entity);
    }

    @Override
    @PostMapping("/getAll")
    public Result getAllATEntity(@RequestBody Map<String,Object> params) {
        String projectId = MapUtils.getString(params,"projectId");
        String page = MapUtils.getString(params,"page","1");
        String pageSize = MapUtils.getString(params,"pageSize","10000");
        return entityService.getAllEntityByProjectId(projectId,page,pageSize);
    }

    @Override
    @PostMapping("/getOne")
    public Result getEntityByLike(@RequestBody ATEntityEntity entity) {
        return entityService.getEntityByLikeName(entity);
    }


    // 想着不提供删除渠道了
    @Override
    public Result deleteEntity(ATEntityEntity entity) {
        return null;
    }

    @Override
    @PostMapping("/getCount")
    public Result countEntity(@RequestBody ATEntityEntity entity) {
        return entityService.countEntity(entity.getProjectId());
    }
}
