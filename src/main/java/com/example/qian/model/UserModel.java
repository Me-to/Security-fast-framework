package com.example.qian.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {
   //自增长id
   private String id;
   //唯一id
   private String uid;
   //用户名
   private String username;
   //用户密码
   private String password;
   //用户登录状态
   private int enable;
   //权限
   private int role;
}
