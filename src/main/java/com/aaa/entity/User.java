package com.aaa.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private Integer deptid;

    private String loginName;

    private String userName;

    private String email;

    private String phonenumber;

    private String sex;

    private String password;

    private String salt;

    private String status;

    private String delFlag;

}