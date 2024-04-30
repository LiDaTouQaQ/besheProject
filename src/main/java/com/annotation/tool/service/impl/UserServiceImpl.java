package com.annotation.tool.service.impl;

import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.UserEntity;
import com.annotation.tool.entity.enums.ResultEnum;
import com.annotation.tool.mapper.UserMapper;
import com.annotation.tool.service.UserService;
import com.annotation.tool.util.JwtUtil;
import com.annotation.tool.util.ResultGeneratorUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Author Liyh
 * @Date 2024.04.05 10:33
 * @Description:
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtil jwtUtil;

    /**
     * @param userEntity 登录用户信息
     * @return 反参
     */
    @Override
    public Result login(UserEntity userEntity) {
        // 存在token的情况
        if(userEntity.getToken() != null){
            String userToken = userEntity.getToken();
            Claims claims = jwtUtil.parseJwt(userToken);
            String id = claims.getId();
            if(!StringUtils.isEmpty(id)){
                userEntity.setId(Integer.valueOf(id));
                UserEntity user = userMapper.selectUserById(userEntity);
                if(user != null ){
                    return new Result(ResultEnum.SUCCESS, user);
                }
            }
        }
        if (userEntity.getEmail() == null && userEntity.getPassword() == null) {
            return new Result(ResultEnum.USERNAME_OR_PASSWORD_NOT_EXIST, null);
        }
        UserEntity user = userMapper.selectUserByEmailAndPassword(userEntity);
        if (user == null) {
            return new Result(ResultEnum.USER_NOT_EXIST, null);
        }
        String token = jwtUtil.createJwt(user.getId().toString(), user.getUserName(), new HashMap<>());
        user.setToken(token);
        return new Result(ResultEnum.SUCCESS, user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(UserEntity userEntity) {
        // 检查信息
        if (userEntity.getUserName() == null && userEntity.getPassword() == null && userEntity.getEmail() == null) {
            return new Result(ResultEnum.USER_INFO_MISSING, null);
        }
        // 检查是否有重复的Email和UserName
        if (userMapper.checkSameEmailAndUserName(userEntity) > 0) {
            return new Result(ResultEnum.USER_EMAIL_REPEAT, null);
        }
        // 插入信息
        try {
            int num = userMapper.insertUser(userEntity);
            if (num > 0) {
                return ResultGeneratorUtil.genSuccessResult("注册成功");
            } else {
                return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "注册失败");
            }
        } catch (Exception e) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.UNKNOWN, "注册失败");
        }
    }

    @Override
    public Result updateUserInfo(UserEntity userEntity) {
        // 检查是否有重复的Email和UserName
        if (userMapper.checkSameEmailAndUserName(userEntity) > 0) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "存在重复Email或用户名");
        }
        // 更新
        try {
            int num = userMapper.updateUserToPasswordOREmail(userEntity);
            if (num > 0) {
                return ResultGeneratorUtil.genSuccessResult("更新成功");
            } else {
                return ResultGeneratorUtil.genFailResult(ResultEnum.UNKNOWN, "更新失败");
            }
        } catch (Exception e) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.UNKNOWN, "更新失败");
        }
    }

    @Override
    public Result deleteUserInfo(UserEntity userEntity) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addUserProject(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "用户不存在");
        }
        UserEntity user = userMapper.selectUserById(userEntity);
        if (user == null) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "用户不存在");
        }
        if (userEntity.getProjectId() == null || userEntity.getPower() == null) {
            return ResultGeneratorUtil.genFailResult(ResultEnum.ERROR, "参数不全");
        }
        userMapper.addProjectUser(userEntity);
        return ResultGeneratorUtil.genSuccessResult();
    }

    @Override
    public Result getUserProject(String projectId) {
        List<UserEntity> users = userMapper.getProjectUserByProjectId(projectId);
        for (UserEntity entity : users) {
            if (entity.getPower().equals("1")) {
                entity.setPower("创建人");
            } else if (entity.getPower().equals("2")) {
                entity.setPower("审核人");
            } else if (entity.getPower().equals("3")) {
                entity.setPower("标注员");
            }
        }
        return ResultGeneratorUtil.genSuccessResult(users);
    }

    @Override
    public Result deleteUserProject(List<String> ids, String projectId) {
        userMapper.deleteUserProjectByIds(ids,projectId);
        return ResultGeneratorUtil.genSuccessResult();
    }

    @Override
    public Result addUserProjectForNew(UserEntity userEntity) {
        userMapper.addUserForNew(userEntity);
        userMapper.addProjectUser(userEntity);
        return ResultGeneratorUtil.genSuccessResult();
    }
}
