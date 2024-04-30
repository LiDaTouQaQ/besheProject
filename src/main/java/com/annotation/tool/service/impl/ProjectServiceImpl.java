package com.annotation.tool.service.impl;

import com.annotation.tool.entity.ProjectEntity;
import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.mapper.ProjectMapper;
import com.annotation.tool.service.ProjectService;
import com.annotation.tool.util.ResultGeneratorUtil;
import com.annotation.tool.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName ProjectServiceImpl
 * @Author Liyh
 * @Date 2024.04.08 15:07
 * @Description:
 **/
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createProject(ProjectEntity projectEntity) {
        if(projectEntity.getProjectName() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"项目名称不能为空");
        }
        projectEntity.setProjectId(UUIDUtil.uuid());
        projectEntity.setCreateDate(new Date());
        try{
            projectMapper.createProject(projectEntity);
            return ResultGeneratorUtil.genSuccessResult(projectEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"项目创建失败");
        }
    }

    @Override
    public Result selectProject(String id,Integer page,Integer pageSize) {
        try{
            PageHelper.startPage(page,pageSize);
            List<ProjectEntity> projectEntityList = projectMapper.getProjectByCreateByPage(id);
            return ResultGeneratorUtil.genSuccessResult(projectEntityList);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR,"获取项目列表失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteProject(List<String> projectIds) {
        projectMapper.deleteProject(projectIds);
        return ResultGeneratorUtil.genSuccessResult("删除成功");
    }

    @Override
    public Result countProject(String id) {
        int total = projectMapper.countProject(id);
        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        return ResultGeneratorUtil.genSuccessResult(result);
    }
}
