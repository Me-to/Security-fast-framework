package com.example.qian.controller;

import com.example.qian.model.UserModel;
import com.example.qian.security.MyBCryptPasswordEncoder;
import com.example.qian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Scontroller {
@Autowired
    UserService service;
@Autowired
    MyBCryptPasswordEncoder myBCryptPasswordEncoder;

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("insert")
    public void test1(){
        UserModel userModel=new UserModel();
        userModel.setUid((UUID.randomUUID().toString()));
        userModel.setUsername("zhangqian");
        userModel.setPassword(myBCryptPasswordEncoder.encode("123"));
        userModel.setEnable(1);
        userModel.setRole(1);
        int i = service.insertUser(userModel);
        System.out.println("i---"+i);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String authenticationTest() {
        return "您拥有admin权限，可以查看";
    }
}
