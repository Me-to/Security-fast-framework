package com.example.qian.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleModel implements Serializable {
    //权限id
    private int id;
    //权限名称
    private String name;
}
