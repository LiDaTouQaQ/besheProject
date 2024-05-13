package com.annotation.tool.service.impl;

import com.annotation.tool.entity.ATEntityEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.mapper.ATEntityMapper;
import com.annotation.tool.service.EntityService;
import com.annotation.tool.service.RelationService;
import com.annotation.tool.util.ResultGeneratorUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EntityServiceImpl
 * @Author Liyh
 * @Date 2024.04.08 16:32
 * @Description:
 **/
@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    ATEntityMapper atEntityMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createBatchEntity(List<ATEntityEntity> entities) {
        try{
            atEntityMapper.createATEntityBatch(entities);
            return ResultGeneratorUtil.genSuccessResult("导入成功");
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"导入失败");
        }
    }

    @Override
    public Result getAllEntityByProjectId(String projectId,String page,String pageSize) {
        try{
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(pageSize));
            List<ATEntityEntity> atEntityEntities = atEntityMapper.getAllATEntityByProjectId(projectId);
            return ResultGeneratorUtil.genSuccessResult(atEntityEntities);
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"导入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createEntity(ATEntityEntity entity) {
        ATEntityEntity oldEntity = atEntityMapper.getEntityByEntityName(entity.getProjectId(), entity.getEntityName());
        if(oldEntity != null){
            return ResultGeneratorUtil.genSuccessResult(oldEntity);
        }
        try{
            atEntityMapper.createATEntity(entity);
            return ResultGeneratorUtil.genSuccessResult(entity);
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"导入失败");
        }
    }

    @Override
    public Result getEntityByLikeName(ATEntityEntity entity) {
        if(entity.getProjectId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"参数错误");
        }
        List<ATEntityEntity> entities = atEntityMapper.getEntityByLikeEntityName(entity.getProjectId(), entity.getEntityName());
        return ResultGeneratorUtil.genSuccessResult(entities);
    }

    @Override
    public Result updateEntity(ATEntityEntity entity) {
        if(entity.getId() == null || entity.getProjectId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"缺少参数");
        }
        atEntityMapper.updateEntity(entity);
        return ResultGeneratorUtil.genSuccessResult("修改成功");
    }

    @Override
    public Result countEntity(String projectId) {
        if(StringUtils.isEmpty(projectId)){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"缺少参数");
        }
        Integer num = atEntityMapper.countEntity(projectId);
        Map<String,Object> result = new HashMap<>();
        result.put("count",num);
        return ResultGeneratorUtil.genSuccessResult(result);
    }
}
