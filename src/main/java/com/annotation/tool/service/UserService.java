package com.annotation.tool.service;

import com.annotation.tool.entity.Result;
import com.annotation.tool.entity.UserEntity;

import java.util.List;

/**
 * @ClassName UserService
 * @Author Liyh
 * @Date 2024.04.05 10:33
 * @Description:
 **/

public interface UserService {

    Result login(UserEntity userEntity);

    Result register(UserEntity userEntity);

    Result updateUserInfo(UserEntity userEntity);

    Result deleteUserInfo(UserEntity userEntity);

    Result addUserProject(UserEntity userEntity);

    Result addUserProjectForNew(UserEntity userEntity);

    Result getUserProject(String projectId);

    Result deleteUserProject(List<String> ids,String projectId);
}
