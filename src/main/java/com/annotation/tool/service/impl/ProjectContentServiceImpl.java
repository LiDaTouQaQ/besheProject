package com.annotation.tool.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.annotation.tool.entity.*;
import com.annotation.tool.mapper.*;
import com.annotation.tool.util.RandomColorUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.MapUtils;
import cn.hutool.json.JSONUtil;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.service.ProjectContentService;
import com.annotation.tool.util.ResultGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName ProjectContentServiceImpl
 * @Author Liyh
 * @Date 2024.04.08 16:06
 * @Description:
 **/
@Service
public class ProjectContentServiceImpl implements ProjectContentService {

    @Autowired
    ProjectContentMapper projectContentMapper;
    @Autowired
    ContentEntityMapper contentEntityMapper;
    @Autowired
    ContentRelationMapper contentRelationMapper;
    @Autowired
    ATEntityMapper atEntityMapper;
    @Autowired
    RelationMapper relationMapper;

    @Autowired
    UserMapper userMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createBatchContent(List<ProjectContentEntity> projectContentEntities) {
        try {
            projectContentMapper.insertBatchProjectContent(projectContentEntities);
            return ResultGeneratorUtil.genSuccessResult("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "导入出错");
        }
    }

    @Override
    public Result updateContentInfo(ProjectContentEntity entity) {
        projectContentMapper.updateProjectContentById(entity);
        if(entity.getTestContent() != null){
            contentEntityMapper.deleteByProjectIdAndProjectContentId(entity.getProjectId(),entity.getContentId());
            contentRelationMapper.deleteByProjectIdAndProjectContentId(entity.getProjectId(),entity.getContentId());
        }
        return ResultGeneratorUtil.genSuccessResult("更新成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createContent(ProjectContentEntity projectContentEntity) {
        try {
            projectContentMapper.insertProjectContent(projectContentEntity);
            return ResultGeneratorUtil.genSuccessResult("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "插入出错");
        }
    }

    @Override
    public Result getAllContent(String projectId ,String page,String pageSize) {
        try {
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(pageSize));
            List<ProjectContentEntity> projectContentEntities = projectContentMapper.selectAllProjectContentByPage(projectId);
            return ResultGeneratorUtil.genSuccessResult(projectContentEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "获取失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result executeFileContent(BufferedReader reader, String projectId, int projectContentId) throws IOException {
        String line;
        List<ProjectContentEntity> entities = new ArrayList<>();
        int j = projectContentId;
        List<ContentEntityEntity> contentEntityEntities = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            int i = 0;
            Map<String, Object> lineMap = JSONUtil.toBean(line, Map.class);
            ProjectContentEntity entity = new ProjectContentEntity();
            String textContent = MapUtils.getString(lineMap, "text");
            entity.setTestContent(textContent);
            entity.setProjectId(projectId);
            entity.setContentId(String.valueOf(j));
            int relationId = 0;
            List<Map<String, Object>> spoList = (List) lineMap.get("spo_list");
            for (Map<String, Object> item : spoList) {
                String subject = MapUtils.getString(item, "subject", null);
                String subjectType = MapUtils.getString(item, "subject_type", null);
                ContentEntityEntity entity1 = new ContentEntityEntity();
                // 获取实体1
                if (subject != null) {
                    entity1.setProjectId(projectId);
                    entity1.setProjectContentId(String.valueOf(j));
                    entity1.setEntityName(subjectType);
                    entity1.setStart(textContent.indexOf(subject));
                    entity1.setEnd(textContent.indexOf(subject) + subject.length());
                    entity1.setEntityContent(subject);
                    entity1.setToRelationId(String.valueOf(i));
                    ATEntityEntity projectEntity = atEntityMapper.getEntityByEntityName(projectId,subjectType);
                    if(projectEntity != null){
                        entity1.setEntityId(Integer.valueOf(projectEntity.getId()));
                        atEntityMapper.addEntityNum(projectId,projectEntity.getId());
                    }else{
                        projectEntity = new ATEntityEntity();
                        projectEntity.setProjectId(projectId);
                        projectEntity.setEntityName(subjectType);
                        projectEntity.setSpanColor(RandomColorUtil.rgbToHex());
                        projectEntity.setNum(0);
                        atEntityMapper.createATEntity(projectEntity);
                        projectEntity = atEntityMapper.getEntityByEntityName(projectId,subjectType);
                        entity1.setEntityId(Integer.valueOf(projectEntity.getId()));
                    }
                    i++;
                    contentEntityEntities.add(entity1);
                }
                Map objectMap = MapUtils.getMap(item, "object");
                String object = MapUtils.getString(objectMap, "@value", null);
                Map objectTypeMap = MapUtils.getMap(item, "object_type");
                String objectType = MapUtils.getString(objectTypeMap, "@value", null);
                ContentEntityEntity entity2 = new ContentEntityEntity();
                // 获取实体2
                if (object != null) {
                    entity2.setProjectId(projectId);
                    entity2.setProjectContentId(String.valueOf(j));
                    entity2.setEntityName(objectType);
                    entity2.setStart(textContent.indexOf(object));
                    entity2.setEnd(textContent.indexOf(object) + object.length());
                    entity2.setEntityContent(object);
                    entity2.setToRelationId(String.valueOf(i));
                    ATEntityEntity projectEntity = atEntityMapper.getEntityByEntityName(projectId,subjectType);
                    if(projectEntity != null){
                        entity2.setEntityId(Integer.valueOf(projectEntity.getId()));
                        atEntityMapper.addEntityNum(projectId,projectEntity.getId());
                    }else{
                        projectEntity = new ATEntityEntity();
                        projectEntity.setProjectId(projectId);
                        projectEntity.setEntityName(objectType);
                        projectEntity.setSpanColor(RandomColorUtil.rgbToHex());
                        projectEntity.setNum(0);
                        atEntityMapper.createATEntity(projectEntity);
                        projectEntity = atEntityMapper.getEntityByEntityName(projectId,subjectType);
                        entity2.setEntityId(Integer.valueOf(projectEntity.getId()));
                    }
                    i++;
                    contentEntityEntities.add(entity2);
                }
                if(entity1.getToRelationId() != null && entity2.getToRelationId() != null){
                    String relationName = MapUtils.getString(item,"predicate", null);
                    if(relationName != null){
                        ContentRelationEntity relation = new ContentRelationEntity();
                        relation.setProjectId(projectId);
                        relation.setProjectContentId(String.valueOf(j));
                        relation.setForm(Integer.valueOf(entity1.getToRelationId()));
                        relation.setTo(Integer.valueOf(entity2.getToRelationId()));
                        RelationEntity relationEntity = relationMapper.getRelationByRelationName(projectId,relationName);
                        if(relationEntity == null){
                            relationEntity = new RelationEntity();
                            relationEntity.setProjectId(projectId);
                            relationEntity.setRelationName(relationName);
                            relationEntity.setSpanColor(RandomColorUtil.rgbToHex());
                            relationEntity.setNum(0);
                            relationMapper.createRelation(relationEntity);
                            relationEntity = relationMapper.getRelationByRelationName(projectId,relationName);
                        }
                        relation.setRelationName(relationName);
                        relation.setRelationId(Integer.valueOf(relationEntity.getId()));
                        relationMapper.addRelationNum(projectId,relationEntity.getId());
                        relationId++;
                        contentRelationMapper.insertRelation(relation);
                    }
                }
            }
            j++;
            entities.add(entity);
        }
        contentEntityMapper.insertEntityBatch(contentEntityEntities);
        projectContentMapper.insertBatchProjectContent(entities);
        return ResultGeneratorUtil.genSuccessResult("导入成功");
    }

    @Override
    public Result getDetailContentByProjectIdAndId(ProjectContentEntity entity) {
        if (entity == null || entity.getProjectId() == null || entity.getContentId() == null) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "参数不全");
        }
        entity = projectContentMapper.getDetailContentByProjectIdAndContentId(entity.getProjectId(), entity.getContentId());
        List<ContentEntityEntity> entityEntityList = contentEntityMapper.selectAllEntity(entity.getProjectId(), entity.getContentId());
        List<ContentRelationEntity> relationEntityList = contentRelationMapper.selectAllEntity(entity.getProjectId(), entity.getContentId());
        if (CollectionUtil.isNotEmpty(entityEntityList)) {
            entity.setEntityEntityList(entityEntityList);
        }else{
            entity.setEntityEntityList(new ArrayList<>());
        }
        if (CollectionUtil.isNotEmpty(relationEntityList)) {
            entity.setRelationEntityList(relationEntityList);
        }else{
            entity.setRelationEntityList(new ArrayList<>());
        }
        return ResultGeneratorUtil.genSuccessResult(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteContentByProjectIdAndContentId(ProjectContentEntity entity) {
        if (entity == null || entity.getProjectId() == null || entity.getContentId() == null) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "参数不全");
        }
        try {
            int i = projectContentMapper.deleteByContentIdAndProjectIdInt(entity.getProjectId(), entity.getContentId());
            contentEntityMapper.deleteByProjectIdAndProjectContentId(entity.getProjectId(), entity.getContentId());
            contentRelationMapper.deleteByProjectIdAndProjectContentId(entity.getProjectId(), entity.getContentId());
            if (i == 1) {
                return ResultGeneratorUtil.genSuccessResult("删除成功");
            } else {
                return ResultGeneratorUtil.genFailResult(ResultEnum.UNKNOWN, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertContentEntity(ContentEntityEntity entity) {
        if(entity.getProjectContentId() == null || entity.getProjectId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "参数不全");
        }
        if(entity.getToRelationId() == null){
            int maxId = 0;
            try {
                maxId = contentEntityMapper.getMaxToRelationIdByProjectIdAndProjectContentId(entity.getProjectId(), entity.getProjectContentId());
            }catch (Exception e){
                maxId = 0;
            }
            entity.setToRelationId(String.valueOf(maxId+1));
        }
        contentEntityMapper.insertEntity(entity);
        atEntityMapper.addEntityNum(entity.getProjectId(), String.valueOf(entity.getEntityId()));
        return ResultGeneratorUtil.genSuccessResult("添加成功");
    }

    private void insertEntity(ContentEntityEntity entity){
        if(entity.getToRelationId() == null){
            int maxId = 0;
            try {
                maxId = contentEntityMapper.getMaxToRelationIdByProjectIdAndProjectContentId(entity.getProjectId(), entity.getProjectContentId());
            }catch (Exception e){
                maxId = 0;
            }
            entity.setToRelationId(String.valueOf(maxId+1));
        }
        contentEntityMapper.insertEntity(entity);
        atEntityMapper.addEntityNum(entity.getProjectId(), String.valueOf(entity.getEntityId()));
    }

    @Override
    public int maxContentID(String projectId) {
        String max = projectContentMapper.maxProjectContentIdByProjectId(projectId);
        if(max == null || max.equals("null")){
            return 0;
        }
        return Integer.parseInt(max);
    }

    @Override
    public Result getCount(String projectId) {
        Map<String,Object> count = new HashMap<>();
        count.put("count",projectContentMapper.countByProjectIdInt(projectId));
        return ResultGeneratorUtil.genSuccessResult(count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addER(ProjectContentEntity entity) {
        ContentEntityEntity entityOneEntity = entity.getEntityEntityList().get(0);
        ContentEntityEntity entityTwoEntity = entity.getEntityEntityList().get(1);
        ContentRelationEntity relationEntity = entity.getRelationEntityList().get(0);
        relationEntity.setForm(Integer.valueOf(entityOneEntity.getToRelationId()));
        relationEntity.setTo(Integer.valueOf(entityTwoEntity.getToRelationId()));
        relationEntity.setProjectContentId(entity.getContentId());
        relationEntity.setRelationId(relationEntity.getId());
        // int i = contentEntityMapper.insertEntity(entityOneEntity);
        // int k = contentEntityMapper.insertEntity(entityTwoEntity);
        int j = contentRelationMapper.insertRelation(relationEntity);
        relationMapper.addRelationNum(relationEntity.getProjectId(), String.valueOf(relationEntity.getRelationId()));
        if(j == 1){
            return ResultGeneratorUtil.genSuccessResult();
        }else{
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR);
        }
    }

    @Override
    public Result deleteEntity(ContentEntityEntity entity) {
        if(entity.getId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR);
        }
        contentEntityMapper.deleteEntity(entity);
        return ResultGeneratorUtil.genSuccessResult();
    }

    @Override
    public Result deleteRelation(ContentRelationEntity entity) {
        if(entity.getId() == null){
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR);
        }
        contentRelationMapper.deleteEntity(entity);
        return ResultGeneratorUtil.genSuccessResult();
    }

    @Override
    public Result countUserWork(String projectId) {
        List<UserEntity> userEntities = userMapper.getProjectUserByProjectId(projectId);
        int sum = projectContentMapper.countByProjectIdInt(projectId);
        List<Map<String,Object>> result = new ArrayList<>();
        for(UserEntity user : userEntities){
            Map<String,Object> map = new HashMap<>();
            int num = projectContentMapper.countUserWorkByProjectIdAndUserId(projectId, String.valueOf(user.getId()));
            Double percentage = (double) (num/sum*100);
            map.put("userName",user.getUserName());
            map.put("userId",user.getId());
            map.put("completeNum",num);
            map.put("percentage",percentage);
            map.put("total",sum);
            result.add(map);
        }
        return ResultGeneratorUtil.genSuccessResult(result);
    }

    @Override
    public Result checkPredResult(Map<String, Object> params) {
        String projectId = MapUtils.getString(params,"projectId","");
        String projectContentId = MapUtils.getString(params,"contentId","");
        if(StringUtils.isEmpty(projectId) || StringUtils.isEmpty(projectContentId)){
            return ResultGeneratorUtil.genFailResult(ResultEnum.UNKNOWN);
        }
        List<Map<String,Object>> predList = (List<Map<String, Object>>) params.get("pred_list");
        if(CollectionUtils.isEmpty(predList)){
            return ResultGeneratorUtil.genSuccessResult("预测结果为空");
        }
        // 处理内容 区分出实体和关系
        for(Map<String,Object> item : predList){
            ContentEntityEntity entityOne = new ContentEntityEntity();
            ContentEntityEntity entityTwo = new ContentEntityEntity();
            ContentRelationEntity relationEntity = new ContentRelationEntity();
            entityOne.setProjectId(projectId);
            entityTwo.setProjectId(projectId);
            entityOne.setProjectContentId(projectContentId);
            entityTwo.setProjectContentId(projectContentId);
            entityOne.setEntityId(101);
            entityOne.setEntityName("预测实体1");
            entityTwo.setEntityId(102);
            entityTwo.setEntityName("预测实体2");
            entityOne.setEntityContent(MapUtils.getString(item,"entityOne",""));
            entityOne.setStart(MapUtils.getInteger(item,"oneStart"));
            entityOne.setEnd(MapUtils.getInteger(item,"oneEnd"));
            entityTwo.setEntityContent(MapUtils.getString(item,"entityTwo",""));
            entityTwo.setStart(MapUtils.getInteger(item,"twoStart"));
            entityTwo.setEnd(MapUtils.getInteger(item,"twoEnd"));
            insertEntity(entityOne);
            insertEntity(entityTwo);
            relationEntity.setProjectId(projectId);
            relationEntity.setProjectContentId(projectContentId);
            relationEntity.setForm(Integer.valueOf(entityOne.getToRelationId()));
            relationEntity.setTo(Integer.valueOf(entityTwo.getToRelationId()));
            relationEntity.setRelationName(MapUtils.getString(item,"relation",""));
            RelationEntity relation = relationMapper.getRelationByRelationName(projectId,MapUtils.getString(item,"relation",""));
            if(relation == null){
                relation = new RelationEntity();
                relation.setProjectId(projectId);
                relation.setRelationName(MapUtils.getString(item,"relation",""));
                relation.setSpanColor(RandomColorUtil.rgbToHex());
                relation.setNum(0);
                relationMapper.createRelation(relation);
                relation = relationMapper.getRelationByRelationName(projectId,MapUtils.getString(item,"relation",""));
            }
            relationEntity.setRelationId(Integer.valueOf(relation.getId()));
            contentRelationMapper.insertRelation(relationEntity);
            relationMapper.addRelationNum(relationEntity.getProjectId(), String.valueOf(relationEntity.getRelationId()));
        }
        return ResultGeneratorUtil.genSuccessResult("自动标注成功");
    }
}
