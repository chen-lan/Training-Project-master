package com.aaa.entity;

import lombok.Data;

@Data
public class Role {
    private Integer roleId;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;

    private String delFlag;

}