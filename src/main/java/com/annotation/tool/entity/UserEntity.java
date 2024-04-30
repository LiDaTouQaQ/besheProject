package com.annotation.tool.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserEntity
 * @Author Liyh
 * @Date 2024.04.05 09:48
 * @Description:
 **/
@Data
public class UserEntity{
    private Integer id;
    private String userName;
    private String password;
    private String identity;
    private String email;
    private String isDelete;
    private String token;

    private String projectId;
    private String power;
}
