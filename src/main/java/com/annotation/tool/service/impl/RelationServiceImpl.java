package com.annotation.tool.service.impl;

import com.annotation.tool.entity.RelationEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.mapper.RelationMapper;
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
 * @ClassName RelationServiceImpl
 * @Author Liyh
 * @Date 2024.04.08 16:32
 * @Description:
 **/
@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationMapper relationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createBatchRelation(List<RelationEntity> entities) {
        try{
            relationMapper.createRelationBatch(entities);
            return ResultGeneratorUtil.genSuccessResult();
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"导入失败");
        }
    }

    @Override
    public Result getAllRelationByProjectId(String projectId,String page,String pageSize) {
        try{
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(pageSize));
            List<RelationEntity> relationEntities = relationMapper.getAllRelationByProjectId(projectId,null,null);
            return ResultGeneratorUtil.genSuccessResult(relationEntities);
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"获取失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createRelation(RelationEntity relationEntity) {
        try{
            relationMapper.createRelation(relationEntity);
            return ResultGeneratorUtil.genSuccessResult();
        }catch (Exception e){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"插入失败");
        }
    }

    @Override
    public Result getRelationLikeName(RelationEntity entity) {
        if(entity.getProjectId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"参数错误");
        }
        List<RelationEntity> entityList = relationMapper.getRelationByLikeName(entity.getProjectId(),entity.getRelationName());
        return ResultGeneratorUtil.genSuccessResult(entityList);
    }

    @Override
    public Result countRelation(String projectId) {
        if(StringUtils.isEmpty(projectId)){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"参数错误");
        }
        Integer num = relationMapper.countRelation(projectId);
        Map<String,Object> result = new HashMap<>();
        result.put("count",num);
        return ResultGeneratorUtil.genSuccessResult(result);
    }
}
