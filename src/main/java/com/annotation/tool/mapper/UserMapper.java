package com.annotation.tool.mapper;

import com.annotation.tool.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Author Liyh
 * @Date 2024.04.05 09:49
 * @Description:
 **/
@Mapper
public interface UserMapper {
    UserEntity selectUserByUserNameAndPassword(@Param("userEntity") UserEntity userEntity);
    UserEntity selectUserByEmailAndPassword(@Param("userEntity") UserEntity userEntity);

    int insertUser(@Param("entity") UserEntity userEntity);

    List<UserEntity> selectUserListByProjectId(String projectId);

    int updateUserToPasswordOREmail(@Param("entity") UserEntity userEntity);

    int deleteUserByIsDelete(@Param("entity") UserEntity userEntity);

    int checkSameEmailAndUserName(@Param("entity") UserEntity userEntity);

    int addProjectUser(@Param("entity") UserEntity userEntity);

    List<UserEntity> getProjectUserByProjectId(@Param("projectId") String projectId);

    UserEntity selectUserById(@Param("entity") UserEntity userEntity);

    int deleteUserProjectByIds(@Param("list") List<String> ids,@Param("projectId") String projectId);

    int addUserForNew(@Param("entity") UserEntity userEntity);

}
